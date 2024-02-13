package carshop.carshop;

import javafx.scene.image.Image;

public class Listing extends Car{
    private int id;
    private String make;
    private String model;
    private String year;
    private String price;
    private String mileage;
    private String driveTerrain;
    private String exteriorColor;
    private String interiorColor;
    private String image = "resources/carshop/greycar.png";
    private int seller;
    private int buyer;
    private String sellerPhone;


    // Add getters and setters as needed

    public Listing() {

        super("", "");
        this.year = "";
        this.price = "";
        this.mileage = "";
        this.driveTerrain = "";
        this.exteriorColor = "";
        this.interiorColor = "";
        this.image = "";
        this.seller = 0;
        this.buyer = 0;
        this.sellerPhone = "";
    }
    public Listing(String make, String model, String year, String price, String mileage,
                   String driveTerrain, String exteriorColor, String interiorColor, String image, int seller, int buyer, String sellerPhone) {
        super(make, model);
        this.year = year;
        this.price = price;
        this.mileage = mileage;
        this.driveTerrain = driveTerrain;
        this.exteriorColor = exteriorColor;
        this.interiorColor = interiorColor;
        this.image = image;
        this.seller = seller;
        this.buyer = buyer;
        this.sellerPhone = sellerPhone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getMake() {
        return make;
    }
    @Override
    public void setMake(String make) {
        this.make = make;
    }
    @Override
    public String getModel() {
        return model;
    }
    @Override
    public void setModel(String model) {
        this.model = model;
    }
    public String getYear() {
        return year;
    }
    public void setYear(String year) {
        this.year = year;
    }
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public String getMileage() {
        return mileage;
    }
    public void setMileage(String mileage) {
        this.mileage = mileage;
    }
    public String getDriveTerrain() {
        return driveTerrain;
    }
    public void setDriveTerrain(String driveTerrain) {
        this.driveTerrain = driveTerrain;
    }
    public String getExteriorColor() {
        return exteriorColor;
    }

    public void setExteriorColor(String exteriorColor) {
        this.exteriorColor = exteriorColor;
    }

    public String getInteriorColor() {
        return interiorColor;
    }

    public void setInteriorColor(String interiorColor) {
        this.interiorColor = interiorColor;
    }

    public String getImage() { return image;    }

    public void setImage(String image) {
        this.image = image;
    }
    public int getSeller() {
        return seller;
    }
    public void setSeller(int seller) {
        this.seller = seller;
    }
    public int getBuyer() {
        return buyer;
    }
    public void setBuyer(int buyer) {
        this.buyer = buyer;
    }
    public String getSellerPhone() {
        return sellerPhone;
    }
    public void setSellerPhone(String sellerPhone) {
        this.sellerPhone = sellerPhone;
    }
}
