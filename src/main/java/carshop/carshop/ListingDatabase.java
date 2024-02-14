package carshop.carshop;

import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ListingDatabase implements DatabaseCredentials{
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("MySQL JDBC Driver not found", e);
        }
    }

    public static void createTable() {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS listings (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "make TEXT," +
                    "model TEXT," +
                    "year TEXT," +
                    "price TEXT," +
                    "mileage TEXT," +
                    "driveTerrain TEXT," +
                    "exteriorColor TEXT," +
                    "interiorColor TEXT," +
                    "image TEXT," +
                    "seller INTEGER," +
                    "buyer INTEGER," +
                    "sellerPhone TEXT)";
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void dropTable() {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {
            String sql = "DROP TABLE IF EXISTS listings";
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Image getImage(int listingindex) {
        Image image = new Image("https://www.iconsdb.com/icons/preview/gray/car-26-xxl.png");
        try {
            Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            PreparedStatement statement = connection.prepareStatement("SELECT imgfile FROM listings WHERE id = ?");
            statement.setInt(1, listingindex);

            try (ResultSet resultSet = statement.executeQuery()) {
                while(resultSet.next()) {
                    Blob imgblob = resultSet.getBlob("imgfile");
                    if(imgblob != null){
                        InputStream inputStream = imgblob.getBinaryStream();
                        image = new Image(inputStream);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return image;
    }

    public static void addListing(Listing listing, User user) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO listings (make, model, year, price, mileage, driveTerrain, exteriorColor, " +
                             "interiorColor, image, imgfile, seller, buyer, sellerPhone) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
            statement.setString(1, listing.getMake());
            statement.setString(2, listing.getModel());
            statement.setString(3, listing.getYear());
            statement.setString(4, listing.getPrice());
            statement.setString(5, listing.getMileage());
            statement.setString(6, listing.getDriveTerrain());
            statement.setString(7, listing.getExteriorColor());
            statement.setString(8, listing.getInteriorColor());
            // Get the filename from the image path
            String imageName = Paths.get(listing.getImage()).getFileName().toString();
            statement.setString(9, imageName);

            File img = new File(listing.getImage());
            FileInputStream fis = new FileInputStream(img);
            statement.setBinaryStream(10, fis, (int) img.length());

            statement.setInt(11, user.getId());
            statement.setInt(12, listing.getBuyer());
            statement.setString(13, user.getPhone());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    private static void updateDeleteListing(Listing listing, User user) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM listings WHERE id = ?")) {
            statement.setInt(1, listing.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void deleteListing(Listing listing, User user) {
        //user = UserSession.getInstance().getLoggedInUser();
        // Check if the buyer is not the same as the seller
        if (listing.getSeller() == user.getId()) {
            //listing.setBuyer(user.getId());
            // Update the database using the appropriate method
            updateDeleteListing(listing, user);
        } else {
            // Handle the case where a user tries to buy their own listing
            System.out.println("Delete you own vehicle");
        }
    }

    public static List<Listing> getListings() {
        List<Listing> listings = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM listings")) {
            while (resultSet.next()) {
                Listing listing = new Listing();
                listing.setId(resultSet.getInt("id"));
                listing.setMake(resultSet.getString("make"));
                listing.setModel(resultSet.getString("model"));
                listing.setYear(resultSet.getString("year"));
                listing.setPrice(resultSet.getString("price"));
                listing.setMileage(resultSet.getString("mileage"));
                listing.setDriveTerrain(resultSet.getString("driveTerrain"));
                listing.setExteriorColor(resultSet.getString("exteriorColor"));
                listing.setInteriorColor(resultSet.getString("interiorColor"));
                listing.setImage(resultSet.getString("image"));
                listing.setSeller(resultSet.getInt("seller"));
                listing.setBuyer(resultSet.getInt("buyer"));
                listing.setSellerPhone(resultSet.getString("sellerPhone"));
                listings.add(listing);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listings;
    }

    public static List<Listing> getFilteredListings(int priceLowerBound,int priceUpperBound,int yearLowerBound,int yearUpperBound,int mileageLowerBound,int mileageUpperBound) {
        List<Listing> listings = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);

            String query = "SELECT * FROM listings where price >= "+Integer.toString(priceLowerBound)+" and price <= "+Integer.toString(priceUpperBound);
            query = query + " and year >= "+Integer.toString(yearLowerBound)+" and year <= "+Integer.toString(yearUpperBound);
            query = query + " and mileage >= "+Integer.toString(mileageLowerBound)+" and mileage <= "+Integer.toString(mileageUpperBound);
//            if(!fmake.equals("ALL")){ query = query + " and make = " + fmake;}
//            if(!fmodel.equals("ALL")){ query = query + " and model = " + fmodel;}
//            if(!fdriveTerrain.equals("ALL")){ query = query + " and driveTerrain = " + fdriveTerrain;}
//            if(!fexColor.equals("ALL")){ query = query + " and exteriorColor = " + fexColor;}
//            if(!finColor.equals("ALL")){ query = query + " and interiorColor = " + finColor;}

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Listing listing = new Listing();
                listing.setId(resultSet.getInt("id"));
                listing.setMake(resultSet.getString("make"));
                listing.setModel(resultSet.getString("model"));
                listing.setYear(resultSet.getString("year"));
                listing.setPrice(resultSet.getString("price"));
                listing.setMileage(resultSet.getString("mileage"));
                listing.setDriveTerrain(resultSet.getString("driveTerrain"));
                listing.setExteriorColor(resultSet.getString("exteriorColor"));
                listing.setInteriorColor(resultSet.getString("interiorColor"));
                listing.setImage(resultSet.getString("image"));
                listing.setSeller(resultSet.getInt("seller"));
                listing.setBuyer(resultSet.getInt("buyer"));
                listing.setSellerPhone(resultSet.getString("sellerPhone"));
                listings.add(listing);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listings;
    }

//    public static List<Listing> getFilteredListings(String fmake,String fmodel,String fdriveTerrain,String fexColor,String finColor,int priceLowerBound,int priceUpperBound,int yearLowerBound,int yearUpperBound,int mileageLowerBound,int mileageUpperBound) {
//        List<Listing> listings = new ArrayList<>();
//        try {
//            Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
//
//            String query = "SELECT * FROM listings where";
//            query = query + " price >= "+Integer.toString(priceLowerBound)+" and price <= "+Integer.toString(priceUpperBound);
//            query = query + " and year >= "+Integer.toString(yearLowerBound)+" and year <= "+Integer.toString(yearUpperBound);
//            query = query + " where mileage >= "+Integer.toString(mileageLowerBound)+" and mileage <= "+Integer.toString(mileageUpperBound);
//            if(!fmake.equals("ALL")){ query = query + " and make = " + fmake;}
//            if(!fmodel.equals("ALL")){ query = query + " and model = " + fmodel;}
//            if(!fdriveTerrain.equals("ALL")){ query = query + " and driveTerrain = " + fdriveTerrain;}
//            if(!fexColor.equals("ALL")){ query = query + " and exteriorColor = " + fexColor;}
//            if(!finColor.equals("ALL")){ query = query + " and interiorColor = " + finColor;}
//
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery(query);
//            while (resultSet.next()) {
//                Listing listing = new Listing();
//                listing.setId(resultSet.getInt("id"));
//                listing.setMake(resultSet.getString("make"));
//                listing.setModel(resultSet.getString("model"));
//                listing.setYear(resultSet.getString("year"));
//                listing.setPrice(resultSet.getString("price"));
//                listing.setMileage(resultSet.getString("mileage"));
//                listing.setDriveTerrain(resultSet.getString("driveTerrain"));
//                listing.setExteriorColor(resultSet.getString("exteriorColor"));
//                listing.setInteriorColor(resultSet.getString("interiorColor"));
//                listing.setImage(resultSet.getString("image"));
//                listing.setSeller(resultSet.getInt("seller"));
//                listing.setBuyer(resultSet.getInt("buyer"));
//                listing.setSellerPhone(resultSet.getString("sellerPhone"));
//                listings.add(listing);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return listings;
//    }



    public static List<Listing> getSellerListings() {
        User user = UserSession.getInstance().getLoggedInUser();
        int sellerId = user.getId(); // Assuming getId() returns the user's ID

        List<Listing> listings = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM listings WHERE seller = ?");
            statement.setInt(1, sellerId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Listing listing = new Listing();
                    listing.setId(resultSet.getInt("id"));
                    listing.setMake(resultSet.getString("make"));
                    listing.setModel(resultSet.getString("model"));
                    listing.setYear(resultSet.getString("year"));
                    listing.setPrice(resultSet.getString("price"));
                    listing.setMileage(resultSet.getString("mileage"));
                    listing.setDriveTerrain(resultSet.getString("driveTerrain"));
                    listing.setExteriorColor(resultSet.getString("exteriorColor"));
                    listing.setInteriorColor(resultSet.getString("interiorColor"));
                    listing.setImage(resultSet.getString("image"));
                    listing.setSeller(resultSet.getInt("seller"));
                    listing.setBuyer(resultSet.getInt("buyer"));
                    listing.setSellerPhone(resultSet.getString("sellerPhone"));
                    listings.add(listing);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listings;
    }

    public static void buyListing(Listing listing, User user) {
        //user = UserSession.getInstance().getLoggedInUser();
        // Check if the buyer is not the same as the seller
        if (listing.getSeller() != user.getId()) {
            //listing.setBuyer(user.getId());
            // Update the database using the appropriate method
            updateBuyerForListing(listing, user);
        } else {
            // Handle the case where a user tries to buy their own listing
            System.out.println("You cannot buy your own listing.");
        }
    }
    private static void updateBuyerForListing(Listing listing, User user) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE listings SET buyer = ? WHERE id = ?")) {
            statement.setInt(1, user.getId());
            statement.setInt(2, listing.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addWishlistItem(int user, int list) throws SQLException {
        // Associate the user with the listing
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
             PreparedStatement insertUserListingStmt = connection.prepareStatement(
                     "INSERT INTO wishlist (userID, listingID) VALUES (?, ?)")) {
            insertUserListingStmt.setInt(1, user);
            insertUserListingStmt.setInt(2, list);
            insertUserListingStmt.executeUpdate();
        }
    }


//    private static int getOrCreateListingId(int list) throws SQLException {
//        // Check if the listing exists
//        try (Connection connection = DriverManager.getConnection(JDBC_URL);
//            PreparedStatement checkListingStmt = connection.prepareStatement(
//                "SELECT id FROM listings WHERE id = ?")) {
//            checkListingStmt.setInt(1, list);
//            ResultSet resultSet = checkListingStmt.executeQuery();
//
//            if (resultSet.next()) {
//                // If the listing exists, return its ID
//                return resultSet.getInt("id");
//            } else {
//                // If the listing doesn't exist, insert a new listing and return its ID
//                try (PreparedStatement insertListingStmt = connection.prepareStatement(
//                        "INSERT INTO listings (id) VALUES (?)", Statement.RETURN_GENERATED_KEYS)) {
//                    insertListingStmt.setInt(1, list);
//                    insertListingStmt.executeUpdate();
//
//                    // Get the auto-generated listing ID
//                    ResultSet generatedKeys = insertListingStmt.getGeneratedKeys();
//                    if (generatedKeys.next()) {
//                        return generatedKeys.getInt(1);
//                    } else {
//                        throw new SQLException("Failed to get listing ID.");
//                    }
//                }
//            }
//        }
//    }


//    public static ResultSet getUserWishlist(int userId) throws SQLException {
//        // Retrieve a user's wish list
//        try (Connection connection = DriverManager.getConnection(JDBC_URL);
//            PreparedStatement stmt = connection.prepareStatement(
//                "SELECT id FROM listings " +
//                        "JOIN wishlist ON listings.id = wishlist.listingID " +
//                        "WHERE wishlist.userID = ?")) {
//            stmt.setInt(1, userId);
//            return stmt.executeQuery();
//        }
//    }

}