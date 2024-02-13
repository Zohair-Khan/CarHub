package carshop.carshop;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class RemoveListingController implements Initializable {

    @FXML
    private Label Revenue;

    @FXML
    private Button Sign_Out;
    @FXML
    private Label User_Label;
    @FXML
    private HBox Ad_List;
    @FXML
    private GridPane ListingContainer;
    @FXML
    private ImageView Home_Img;

    @FXML
    private GridPane carContainer;

    @FXML
    private HBox Profile_Page;

    @FXML
    private HBox Remove_List;

    @FXML
    private HBox User_Page;
    private List<Listing> listings;
    private List<User> user;
    private Listing selectedListing;


    public void revenue(){
        double sum = 0;
        for (Listing list : ListingDatabase.getSellerListings()){
            if (list.getBuyer() != 0) {
                sum = sum + Double.parseDouble(list.getPrice());
            }
        }
        Revenue.setText(String.valueOf(sum));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listings = new ArrayList<>(listings());
        user = new ArrayList<>(user());
        displayListings();
        updateLoginLabel();
        revenue();
    }
    public void initialize(){
        updateLoginLabel();
    }
    public List<User> user(){
        List<User> person = new ArrayList<>();
        for (User list : UserDatabase.getUsers()){
            list.getPhone();
            person.add(list);
        }
        return person;
    }
    public List<Listing> listings(){
        List<Listing> ls = new ArrayList<>();
        for (Listing list : ListingDatabase.getSellerListings()){
            list.getMake();
            list.getImage();
            list.getDriveTerrain();
            ls.add(list);
        }
        return ls;
    }
    private void displayListings() {
        int column = 0;
        int row = 1;
        try{
            for (Listing car : listings){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("Vehicle.fxml"));
                VBox carBox = fxmlLoader.load();
                //Label delete = fxmlLoader.load();
                VehicleController carController = fxmlLoader.getController();
                // Check for null before setting data
                if (carController != null) {
                    carController.setData2(car);
                }
                // Set up click event for the listing
                carBox.setOnMouseClicked(event -> handleListingClick(car));

                if(column ==6){
                    column =0;
                    ++row;
                }
                ListingContainer.add(carBox, column++, row);
                GridPane.setMargin(carBox, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Listing ls = new Listing();
    private void handleListingClick(Listing listing) {
        User user = UserSession.getInstance().getLoggedInUser();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("VehicleProfile.fxml"));
            Parent root = loader.load();

            // Access the controller and set the data for the VehicleProfile
            VehicleProfileController profileController = loader.getController();
            profileController.setData2(listing);
            ls = listing;
            profileController.settSelectedListing2(ls);


            // Create a new stage for the VehicleProfile window
            Stage stage = (Stage) ListingContainer.getScene().getWindow();
            stage.setScene(new Scene(root, 1200, 800));
            stage.setTitle("Vehicle Profile");
//            stage.setMaximized(true);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void On_SignOut_Clicked(ActionEvent event) {
        UserSession.getInstance().logout();
        updateLoginLabel();
        openHomePage();
    }
    private void openHomePage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) Sign_Out.getScene().getWindow();
            Scene scene = new Scene(root, 1200, 800);
            stage.setScene(scene);
            stage.setTitle("Home Page");
            setFixedStageSize(stage);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openLoginPage() {
        // Open the "Login.fxml" file
        try {
            // Assuming "Login.fxml" is in the same package as LoginController
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) User_Page.getScene().getWindow();
            stage.setScene(new Scene(root, 1200, 800));
            stage.setTitle("Login");
            setFixedStageSize(stage);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openUserPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UserPage.fxml"));
            Parent root = loader.load();
            UserPageController userController = loader.getController();
            userController.setLoggedInUser(UserSession.getInstance().getLoggedInUser());
            Stage stage = (Stage) User_Page.getScene().getWindow();
            stage.setScene(new Scene(root, 1200, 800));
            stage.setTitle("User Page");
            setFixedStageSize(stage);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void Add_Clicked(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AddListing.fxml")));
            Stage stage = (Stage) Ad_List.getScene().getWindow();
            stage.setTitle("Add Listing Page");
            stage.setScene(new Scene(root, 1200, 800));
            stage.setTitle("User Page");
            setFixedStageSize(stage);
            stage.show();;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void Home_Img_Clicked(MouseEvent event) {
        openHomePage();
    }

    @FXML
    void Profile_Clicked(MouseEvent event) {
        openUserPage();
    }

    private void setFixedStageSize(Stage stage) {
        stage.setMinWidth(1200);
        stage.setMinHeight(800);
        stage.setMaxWidth(1200);
        stage.setMaxHeight(800);
    }

    @FXML
    void Remove_Clicked(MouseEvent event) {


    }
    private void updateLoginLabel() {
        if (UserSession.getInstance().isLoggedIn()) {
            User_Label.setText("Welcome, " + UserSession.getInstance().getLoggedInUser().getName() + "!");
        } else {
            User_Label.setText("Login");
        }
    }
    @FXML
    void User_Page_Clicked(MouseEvent event) {
        if (UserSession.getInstance().isLoggedIn()) {
            // Redirect to UserPage.fxml
            openUserPage();
        } else {
            // Redirect to Login.fxml
            openLoginPage();
        }
    }

}
