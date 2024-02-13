package carshop.carshop;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {

    @FXML
    private WebView webView;
    @FXML
    private Button Sign_Out;
    @FXML
    private Label Login_Label;
    @FXML
    private MenuButton Category_Menu_Button;
    @FXML
    private MenuButton Model;
    @FXML
    private MenuButton Make;
    @FXML
    private MenuButton Drive_Terrain;
    @FXML
    private MenuButton Interior_Color;
    @FXML
    private MenuButton Exterior_Color;
    @FXML
    private ImageView Home_IMG_Button;
    @FXML
    private MenuButton Location_Menu_Button;
    @FXML
    private TextField Maximum_Text_Field;
    @FXML
    private TextField Minimum_Text_Field;
    @FXML
    private MenuButton Sort_Menu_Button;
    @FXML
    private GridPane carContainer;
    @FXML
    private MenuButton Browse_Menu_Button;
    @FXML
    private HBox Login;
    @FXML
    private Label titleLablel;
    @FXML
    private ImageView back1_Button;
    @FXML
    private Navigation navManager;
    private List<Listing> listings;
    private List<Listing> listings2;
    private List<Listing> carListing;
    private List<User> user;
    @FXML
    private Button hi;
    private Listing selectedListing;
    private SelectedListing listing2;
    private VehicleProfileController vehicleProfileController;
    private Dialog_Interface dialogInterface;
    private String selectedMake;
    private String selectedModel;
    private String selectedDriveTerrain;
    private String selectedExteriorColor;
    private String selectedInteriorColor;
    private TextField Minimum_Year_Field;
    private TextField Maximum_Year_Field;
    private TextField Minimum_Mileage_Field;
    private TextField Maximum_Mileage_Field;



    @FXML
    public void on_hi(){
        carContainer.getChildren().clear();
        displayListings2();
    }

    public void initialize(){
        updateLoginLabel();
    }

    public void setDialogInterface(Dialog_Interface dialogInterface) {
        this.dialogInterface = dialogInterface;
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
        for (Listing list : ListingDatabase.getListings()){

            list.getMake();
            list.getImage();
            list.getDriveTerrain();
            ls.add(list);
        }
        return ls;
    }
    public List<Listing> listings2(){
        List<Listing> ls = new ArrayList<>();

        for (Listing list : ListingDatabase.getFilteredListings(0, 1000000, 0,10000000,0,100000000)){
            list.getMake();
            list.getImage();
            list.getDriveTerrain();
            ls.add(list);
        }
        return ls;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        vehicleProfileController = new VehicleProfileController();
        listings2 = new ArrayList<>(listings2());
        listings = new ArrayList<>(listings());
        user = new ArrayList<>(user());
        displayListings();
        updateLoginLabel();
    }


    private void displayListings() {
        int column = 0;
        int row = 1;
        try{
            for (Listing car : listings) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("Vehicle.fxml"));
                VBox carBox = fxmlLoader.load();
                VehicleController carController = fxmlLoader.getController();
                //listing2.setSelectedListing(selectedListing);
                carBox.setUserData(car);
                // Check for null before setting data
                if (carController != null) {
                    carController.setData(car);
                }
                // Set up click event for the listing
                carBox.setOnMouseClicked(event -> handleListingClick(car));
                if (column == 6) {
                    column = 0;
                    ++row;
                }
                carContainer.add(carBox, column++, row);
                GridPane.setMargin(carBox, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void displayListings2() {
        int column = 0;
        int row = 1;
        try{
            for (Listing car : listings2) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("Vehicle.fxml"));
                VBox carBox = fxmlLoader.load();
                VehicleController carController = fxmlLoader.getController();
                //listing2.setSelectedListing(selectedListing);
                carBox.setUserData(car);
                // Check for null before setting data
                if (carController != null) {
                    carController.setData(car);
                }
                // Set up click event for the listing
                carBox.setOnMouseClicked(event -> handleListingClick(car));
                if (column == 6) {
                    column = 0;
                    ++row;
                }
                carContainer.add(carBox, column++, row);
                GridPane.setMargin(carBox, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    Listing ls = new Listing();


    private void handleListingClick(Listing listing) {
        //WishList wishLs = new WishList();
        setDialogInterface(new Dialog_Box());
        User user = UserSession.getInstance().getLoggedInUser();

        if(listing.getBuyer() == 0) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("VehicleProfile.fxml"));
                Parent root = loader.load();
                // Access the controller and set the data for the VehicleProfile
                VehicleProfileController profileController = loader.getController();
                profileController.setData(listing);
                ls = listing;
                profileController.settSelectedListing(ls);
                profileController.setSelectedListing3(ls);

                // Create a new stage for the VehicleProfile window
                Stage stage = (Stage) carContainer.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Vehicle Profile");
                stage.setMaximized(true);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            dialogInterface.sold();
        }
    }

    @FXML
    public void onBack1Clicked(ActionEvent event){
        System.out.println("back 1 clicked");
        if (navManager != null){
            Scene previousScene = navManager.popScene();
            if (previousScene != null){
                Stage stage = (Stage) back1_Button.getScene().getWindow();
                stage.setScene(previousScene);
            }
        }
    }

    @FXML
    protected void onHelloButtonClick(MouseEvent event) throws IOException {

        openHomePage();

    }



    @FXML
    void On_Category_Menu_Button_Clicked(ActionEvent event) {

    }
    @FXML
    void On_Location_Menu_Button_Clicked(ActionEvent event) {
    }

    @FXML
    void On_Login_Clicked(MouseEvent event) throws IOException {
        if (UserSession.getInstance().isLoggedIn()) {
            // Redirect to UserPage.fxml
            openUserPage();

        } else {
            // Redirect to Login.fxml
            openLoginPage();
        }

    }
    @FXML
    void On_SignOut_Clicked(ActionEvent event) {
        UserSession.getInstance().logout();
        updateLoginLabel();
        setDialogInterface(new Dialog_Box());
        dialogInterface.signOutSuccessful();
    }

//    @FXML
//    void On_Maximum_Text_Field_Clicked(ActionEvent event) {
//
//    }
    @FXML
    void On_Menu_Button_Clicked(ActionEvent event) {

    }
//    @FXML
//    void On_Minimum_Text_Field_Clicked(ActionEvent event) {
//
//    }
    @FXML
    void On_Search_TextField_Clicked(ActionEvent event) {

    }
    @FXML
    void On_Sort_Menu_Button_Clicked(ActionEvent event) {

    }

    @FXML
    public void onMakeOptionSelected(ActionEvent event) {
        MenuItem selectedMakeItem = (MenuItem) event.getSource();
        selectedMake = selectedMakeItem.getText();
        Make.setText(selectedMake);
        selectedModel = null;
        populateModelMenu(selectedMake);

    }
    @FXML
    void On_Model_Clicked(ActionEvent event) {
        MenuItem selectedModelItem = (MenuItem) event.getSource();
        selectedModel = selectedModelItem.getText();
        Model.setText(selectedModel);
    }

    @FXML
    void On_Drive_Terrain_Clicked(ActionEvent event) {
        MenuItem selectedDriveItem = (MenuItem) event.getSource();
        selectedDriveTerrain = selectedDriveItem.getText();
        Drive_Terrain.setText(selectedDriveTerrain);
    }
    @FXML
    void On_Exterior_Color_Clicked(ActionEvent event) {
        MenuItem selectedExteriorItem = (MenuItem) event.getSource();
        selectedExteriorColor = selectedExteriorItem.getText();
        Exterior_Color.setText(selectedExteriorColor);
    }
    @FXML
    void On_Interior_Color_Clicked(ActionEvent event) {
        MenuItem selectedInteriorItem = (MenuItem) event.getSource();
        selectedInteriorColor = selectedInteriorItem.getText();
        Interior_Color.setText(selectedInteriorColor);
    }

    @FXML
    void onBack1Clicked(MouseEvent event) {

    }

    private void updateLoginLabel() {
        if (UserSession.getInstance().isLoggedIn()) {
            Login_Label.setText("Welcome, " + UserSession.getInstance().getLoggedInUser().getName() + "!");
        } else {
            Login_Label.setText("Login");
        }
    }

    private void openUserPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UserPage.fxml"));
            Parent root = loader.load();
            // Pass the username to the UserController
            UserPageController userPageController = loader.getController();
            userPageController.setLoggedInUser(UserSession.getInstance().getLoggedInUser());
            Stage stage = (Stage) Login.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("User Page");
            stage.setMaximized(true);
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
            Stage stage = (Stage) Login.getScene().getWindow();
            stage.setMaximized(true);
            stage.setScene(new Scene(root));
            stage.setTitle("Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void openHomePage(){
        try {
            WebView webView = new WebView();
            Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) Home_IMG_Button.getScene().getWindow();
            stage.setMaximized(true);
            stage.setTitle("Home Page");
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void populateModelMenu(String selectedMake) {
        // Clear existing items
        Model.getItems().clear();

        // Add model options based on the selected Make
        // You can replace this with your logic to fetch models based on the selected make
        switch (selectedMake) {
            case "Acura":
                Model.getItems().addAll(
                        addModelItem("ILX"),
                        addModelItem("MDX"),
                        addModelItem("RDX"),
                        addModelItem("RLX"),
                        addModelItem("RLX Sport Hybrid"),
                        addModelItem("TL"),
                        addModelItem("TLX"),
                        addModelItem("TSX")
                        // Add more models as needed
                );
                break;
            case "Alfa Romeo":
                Model.getItems().addAll(
                        addModelItem("4C"),
                        addModelItem("4C Spider"),
                        addModelItem("Giulia"),
                        addModelItem("Stelvio")
                        // Add more models as needed
                );
                break;
            case "Audi":
                Model.getItems().addAll(
                        addModelItem("A3"),
                        addModelItem("A3 Sportback e-tron"),
                        addModelItem("A4"),
                        addModelItem("A4 allroad"),
                        addModelItem("A5"),
                        addModelItem("A6"),
                        addModelItem("A6 allroad"),
                        addModelItem("A7"),
                        addModelItem("A8"),
                        addModelItem("allroad"),
                        addModelItem("e-tron"),
                        addModelItem("e-tron Sportback"),
                        addModelItem("Q3"),
                        addModelItem("Q4 e-tron"),
                        addModelItem("Q5"),
                        addModelItem("Q5 Sportback"),
                        addModelItem("Q7"),
                        addModelItem("Q8"),
                        addModelItem("RS 3"),
                        addModelItem("RS 5"),
                        addModelItem("RS 7"),
                        addModelItem("S3"),
                        addModelItem("S4"),
                        addModelItem("S5"),
                        addModelItem("S6"),
                        addModelItem("S7"),
                        addModelItem("S8"),
                        addModelItem("SQ5"),
                        addModelItem("SQ5 Sportback"),
                        addModelItem("TT")
                        // Add more models as needed
                );
                break;
            case "BMW":
                Model.getItems().addAll(
                        addModelItem("1 Series"),
                        addModelItem("2 Series"),
                        addModelItem("3 Series"),
                        addModelItem("4 Series"),
                        addModelItem("5 Series"),
                        addModelItem("6 Series"),
                        addModelItem("7 Series"),
                        addModelItem("8 Series"),
                        addModelItem("i3"),
                        addModelItem("i4"),
                        addModelItem("iX"),
                        addModelItem("M2"),
                        addModelItem("M3"),
                        addModelItem("M4"),
                        addModelItem("M5"),
                        addModelItem("M6"),
                        addModelItem("M8"),
                        addModelItem("X1"),
                        addModelItem("X2"),
                        addModelItem("X3"),
                        addModelItem("X3 M"),
                        addModelItem("X4"),
                        addModelItem("X5"),
                        addModelItem("X5 M"),
                        addModelItem("X6"),
                        addModelItem("X6 M"),
                        addModelItem("X7"),
                        addModelItem("Z4")
                        // Add more models as needed
                );
                break;
            case "Buick":
                Model.getItems().addAll(
                        addModelItem("Casada"),
                        addModelItem("Enclave"),
                        addModelItem("Encore"),
                        addModelItem("Encore GX"),
                        addModelItem("Envision"),
                        addModelItem("LaCrosse"),
                        addModelItem("Lucerne"),
                        addModelItem("Regal"),
                        addModelItem("Regal Sportback"),
                        addModelItem("Regal TourX"),
                        addModelItem("Verano")
                        // Add more models as needed
                );
                break;
            case "Cadillac":
                Model.getItems().addAll(
                        addModelItem("ATS"),
                        addModelItem("ATS-V"),
                        addModelItem("CT4"),
                        addModelItem("CT5"),
                        addModelItem("CT6"),
                        addModelItem("CTS"),
                        addModelItem("CTS-V"),
                        addModelItem("DTS"),
                        addModelItem("ELR"),
                        addModelItem("Escalade"),
                        addModelItem("Escalade ESV"),
                        addModelItem("LYRIQ"),
                        addModelItem("SRX"),
                        addModelItem("XLR"),
                        addModelItem("XT4"),
                        addModelItem("XT5"),
                        addModelItem("XT6"),
                        addModelItem("XTS")
                        // Add more models as needed
                );
                break;
            case "Chevrolet":
                Model.getItems().addAll(
                        addModelItem("Avalanche"),
                        addModelItem("Blazer"),
                        addModelItem("Bolt EUV"),
                        addModelItem("Bolt EV"),
                        addModelItem("Camaro"),
                        addModelItem("Captiva Sport"),
                        addModelItem("Cobalt"),
                        addModelItem("Colorado Crew Cab"),
                        addModelItem("Colorado Extended Cab"),
                        addModelItem("Corvette"),
                        addModelItem("Cruz"),
                        addModelItem("Cruz Limited"),
                        addModelItem("Equinox"),
                        addModelItem("Express 2500 Passenger"),
                        addModelItem("Express 3500 Cargo"),
                        addModelItem("Impala"),
                        addModelItem("Impala Limited"),
                        addModelItem("Malibu"),
                        addModelItem("Malibu Limited"),
                        addModelItem("Silverado 1500 Crew Cab"),
                        addModelItem("Silverado 1500 Double Cab"),
                        addModelItem("Silverado 1500 Extended Cab"),
                        addModelItem("Silverado 1500 Limited Crew Cab"),
                        addModelItem("Silverado 1500 Limited Double Cab"),
                        addModelItem("Silverado 1500 Regular Cab"),
                        addModelItem("Silverado 2500 HD Crew Cab"),
                        addModelItem("Silverado 2500 HD Double Cab"),
                        addModelItem("Silverado 3500 HD Crew Cab"),
                        addModelItem("Silverado 3500 HD Crew Cab & Chassis"),
                        addModelItem("Silverado 3500 HD Regular Cab"),
                        addModelItem("Sonic"),
                        addModelItem("Spark"),
                        addModelItem("Spark EV"),
                        addModelItem("SS"),
                        addModelItem("Suburban"),
                        addModelItem("Suburban 1500"),
                        addModelItem("Tahoe"),
                        addModelItem("TrailBlazer"),
                        addModelItem("Traverse"),
                        addModelItem("Trax"),
                        addModelItem("Volt")
                        // Add more models as needed
                );
                break;
            case "Chrysler":
                Model.getItems().addAll(
                        addModelItem("200"),
                        addModelItem("300"),
                        addModelItem("Pacifica"),
                        addModelItem("Pacifica Hybrid"),
                        addModelItem("PT Cruiser"),
                        addModelItem("Sebring"),
                        addModelItem("Town & Country"),
                        addModelItem("Voyager")
                        // Add more models as needed
                );
                break;
            case "Dodge":
                Model.getItems().addAll(
                        addModelItem("Avenger"),
                        addModelItem("Challenger"),
                        addModelItem("Charger"),
                        addModelItem("Dart"),
                        addModelItem("Durango"),
                        addModelItem("Grand Caravan Passenger"),
                        addModelItem("Hornet"),
                        addModelItem("Journey"),
                        addModelItem("Ram 1500 Crew Cab")
                        // Add more models as needed
                );
                break;
            case "FIAT":
                Model.getItems().addAll(
                        addModelItem("124 Spider"),
                        addModelItem("500"),
                        addModelItem("500 Abarth"),
                        addModelItem("500c"),
                        addModelItem("500c Abarth"),
                        addModelItem("500L"),
                        addModelItem("500X")
                        // Add more models as needed
                );
                break;
            case "Ford":
                Model.getItems().addAll(
                        addModelItem("Bronco"),
                        addModelItem("Bronco Sport"),
                        addModelItem("C-MAX Energi"),
                        addModelItem("C-MAX Hybrid"),
                        addModelItem("E350 Super Duty Passenger"),
                        addModelItem("EcoSport"),
                        addModelItem("Edge"),
                        addModelItem("Escape"),
                        addModelItem("Escape Plug-in Hybrid"),
                        addModelItem("Expedition"),
                        addModelItem("Expedition MAX"),
                        addModelItem("Explorer"),
                        addModelItem("F150 Lightning"),
                        addModelItem("F150 Regular Cab"),
                        addModelItem("F150 Super Cab"),
                        addModelItem("F150 SuperCrew Cab"),
                        addModelItem("F250 Super Duty Crew Cab"),
                        addModelItem("F250 Super Duty Regular Cab"),
                        addModelItem("F250 Super Duty Super Cab"),
                        addModelItem("Fiesta"),
                        addModelItem("Flex"),
                        addModelItem("Focus"),
                        addModelItem("Focus ST"),
                        addModelItem("Fusion"),
                        addModelItem("Fusion Energi"),
                        addModelItem("Fusion Plug-in Hybrid"),
                        addModelItem("Maverick"),
                        addModelItem("Mustang"),
                        addModelItem("Mustang MACH-E"),
                        addModelItem("Ranger Super Cab"),
                        addModelItem("Ranger SuperCab"),
                        addModelItem("Ranger SuperCrew"),
                        addModelItem("Taurus"),
                        addModelItem("Transit 150 Cargo Van"),
                        addModelItem("Transit 150 Van"),
                        addModelItem("Transit 150 Wagon"),
                        addModelItem("Transit 250 Cargo Van"),
                        addModelItem("Transit 250 Crew Van"),
                        addModelItem("Transit 150 Passenger Van"),
                        addModelItem("Transit Connect Cargo"),
                        addModelItem("Transit Connect Passenger"),
                        addModelItem("Transit Connect Passenger Wagon")
                        // Add more models as needed
                );
                break;
            case "Genesis":
                Model.getItems().addAll(
                        addModelItem("G70"),
                        addModelItem("G80"),
                        addModelItem("G90"),
                        addModelItem("GV60"),
                        addModelItem("GV70"),
                        addModelItem("GV80")
                        // Add more models as needed
                );
                break;
            case "GMC":
                Model.getItems().addAll(
                        addModelItem("Acadia"),
                        addModelItem("Acadia Limited"),
                        addModelItem("Canyon Crew Cab"),
                        addModelItem("Canyon Extended Cab"),
                        addModelItem("Savana 3500 Crew Cab"),
                        addModelItem("Sierra 1500 Crew Cab"),
                        addModelItem("Sierra 1500 Double Cab"),
                        addModelItem("Sierra 1500 Extended Cab"),
                        addModelItem("Sierra 1500 Limited Crew Cab"),
                        addModelItem("Sierra 1500 Limited Double Cab"),
                        addModelItem("Sierra 1500 Regular Cab"),
                        addModelItem("Sierra 2500 HD Crew Cab"),
                        addModelItem("Sierra 2500 HD Double Cab"),
                        addModelItem("Sierra 2500 HD Extended Cab"),
                        addModelItem("Sierra 2500 HD Regular Cab"),
                        addModelItem("Sierra 3500 HD Crew Cab"),
                        addModelItem("Sierra 3500 HD Double Cab"),
                        addModelItem("Sierra 3500 HD Regular Cab"),
                        addModelItem("Terrain"),
                        addModelItem("Yukon"),
                        addModelItem("Yukon XL"),
                        addModelItem("Yukon XL 1500")
                        // Add more models as needed
                );
                break;
            case "Honda":
                Model.getItems().addAll(
                        addModelItem("Accord"),
                        addModelItem("Accord Crosstour"),
                        addModelItem("Accord Hybrid"),
                        addModelItem("Civic"),
                        addModelItem("Civic Type R"),
                        addModelItem("Clarity Plug-in Hybrid"),
                        addModelItem("CR-V"),
                        addModelItem("CR-V Hybrid"),
                        addModelItem("CR-Z"),
                        addModelItem("Crosstour"),
                        addModelItem("Element"),
                        addModelItem("Fit"),
                        addModelItem("HR-V"),
                        addModelItem("Insight"),
                        addModelItem("Odyssey"),
                        addModelItem("Passport"),
                        addModelItem("Pilot"),
                        addModelItem("Ridgeline")
                        // Add more models as needed
                );
                break;
            case "Hyundai":
                Model.getItems().addAll(
                        addModelItem("Accent"),
                        addModelItem("Azera"),
                        addModelItem("Elantra"),
                        addModelItem("Elantra GT"),
                        addModelItem("Elantra N"),
                        addModelItem("Equus"),
                        addModelItem("Genesis"),
                        addModelItem("Genesis Coupe"),
                        addModelItem("IONIQ 5"),
                        addModelItem("Ioniq Electric"),
                        addModelItem("Ioniq Hybrid"),
                        addModelItem("Ionic Plug-in Hybrid"),
                        addModelItem("Kona"),
                        addModelItem("Kona Electric"),
                        addModelItem("Kona N"),
                        addModelItem("Palisade"),
                        addModelItem("Santa Cruz"),
                        addModelItem("Santa Fe"),
                        addModelItem("Santa Fe Hybrid"),
                        addModelItem("Santa Fe Plug-in Hybrid"),
                        addModelItem("Santa Fe Sport"),
                        addModelItem("Santa Fe XL"),
                        addModelItem("Sonata"),
                        addModelItem("Sonata Hybrid"),
                        addModelItem("Tuscan"),
                        addModelItem("Tuscan Hybrid"),
                        addModelItem("Veloster"),
                        addModelItem("Venue"),
                        addModelItem("Veracruz")
                        // Add more models as needed
                );
                break;
            case "INFINITI":
                Model.getItems().addAll(
                        addModelItem("EX"),
                        addModelItem("FX"),
                        addModelItem("G"),
                        addModelItem("JX"),
                        addModelItem("M"),
                        addModelItem("Q40"),
                        addModelItem("Q50"),
                        addModelItem("Q60"),
                        addModelItem("Q70"),
                        addModelItem("QX"),
                        addModelItem("QX30"),
                        addModelItem("QX50"),
                        addModelItem("QX55"),
                        addModelItem("QX60"),
                        addModelItem("QX70"),
                        addModelItem("QX80")
                        // Add more models as needed
                );
                break;
            case "Jaguar":
                Model.getItems().addAll(
                        addModelItem("E-PACE"),
                        addModelItem("F-PACE"),
                        addModelItem("F-TYPE"),
                        addModelItem("I-TYPE"),
                        addModelItem("XE"),
                        addModelItem("XF"),
                        addModelItem("XJ"),
                        addModelItem("XK")
                        // Add more models as needed
                );
                break;
            case "Jeep":
                Model.getItems().addAll(
                        addModelItem("Cherokee"),
                        addModelItem("Compass"),
                        addModelItem("Gladiator"),
                        addModelItem("Grand Cherokee"),
                        addModelItem("Grand Cherokee L"),
                        addModelItem("Grand Wagoneer"),
                        addModelItem("Liberty"),
                        addModelItem("Patriot"),
                        addModelItem("Renegade"),
                        addModelItem("Wagoneer"),
                        addModelItem("Wrangler"),
                        addModelItem("Wrangler 4xe"),
                        addModelItem("Wrangler Unlimited"),
                        addModelItem("Wrangler Unlimited 4xe")
                        // Add more models as needed
                );
                break;
            case "Kia":
                Model.getItems().addAll(
                        addModelItem("Borrego"),
                        addModelItem("Cadenza"),
                        addModelItem("Carnival"),
                        addModelItem("EV6"),
                        addModelItem("Forte"),
                        addModelItem("Forte Koup"),
                        addModelItem("Forte5"),
                        addModelItem("K5"),
                        addModelItem("K900"),
                        addModelItem("Niro"),
                        addModelItem("Niro EV"),
                        addModelItem("Niro Plug-in Hybrid"),
                        addModelItem("Optima"),
                        addModelItem("Optima Hybrid"),
                        addModelItem("Optima Hybrid Plug-in Hybrid"),
                        addModelItem("Rio"),
                        addModelItem("Sedona"),
                        addModelItem("Seltos"),
                        addModelItem("Sorento"),
                        addModelItem("Sorento Hybrid"),
                        addModelItem("Sorento Plug-in Hybrid"),
                        addModelItem("Soul"),
                        addModelItem("Soul EV"),
                        addModelItem("Sportage"),
                        addModelItem("Sportage Hybrid"),
                        addModelItem("Sportage Plug-in Hybrid"),
                        addModelItem("Stinger"),
                        addModelItem("Telluride")
                        // Add more models as needed
                );
                break;
            case "Land Rover":
                Model.getItems().addAll(
                        addModelItem("Defender 110"),
                        addModelItem("Discovery"),
                        addModelItem("Discovery Sport"),
                        addModelItem("LR2"),
                        addModelItem("LR4"),
                        addModelItem("Range Rover"),
                        addModelItem("Range Rover Evoque"),
                        addModelItem("Range Rover Sport"),
                        addModelItem("Range Rover Velar")
                        // Add more models as needed
                );
                break;
            case "Lexus":
                Model.getItems().addAll(
                        addModelItem("CT"),
                        addModelItem("ES"),
                        addModelItem("GS"),
                        addModelItem("GX"),
                        addModelItem("HS"),
                        addModelItem("IS"),
                        addModelItem("LC"),
                        addModelItem("LS"),
                        addModelItem("LX"),
                        addModelItem("NX"),
                        addModelItem("RC"),
                        addModelItem("RX"),
                        addModelItem("SC"),
                        addModelItem("UX")
                        // Add more models as needed
                );
                break;
            case "Lincoln":
                Model.getItems().addAll(
                        addModelItem("Aviator"),
                        addModelItem("Continental"),
                        addModelItem("Corsair"),
                        addModelItem("MKC"),
                        addModelItem("MKS"),
                        addModelItem("MKT"),
                        addModelItem("MKX"),
                        addModelItem("MKZ"),
                        addModelItem("Nautilus"),
                        addModelItem("Navigator"),
                        addModelItem("Navigator L"),
                        addModelItem("Town Car")
                        // Add more models as needed
                );
                break;
            case "Maserati":
                Model.getItems().addAll(
                        addModelItem("Ghibli"),
                        addModelItem("GranTurismo"),
                        addModelItem("Levante"),
                        addModelItem("Quattroporte")
                        // Add more models as needed
                );
                break;
            case "Mazda":
                Model.getItems().addAll(
                        addModelItem("CX-3"),
                        addModelItem("CX-30"),
                        addModelItem("CX-5"),
                        addModelItem("CX-50"),
                        addModelItem("CX-7"),
                        addModelItem("CX-9"),
                        addModelItem("MAZDA2"),
                        addModelItem("MAZDA3"),
                        addModelItem("MAZDA5"),
                        addModelItem("MAZDA6"),
                        addModelItem("MX-30"),
                        addModelItem("MX-5 Miata"),
                        addModelItem("MX-5 Miata RF")
                        // Add more models as needed
                );
                break;
            case "Mercedes-Benz":
                Model.getItems().addAll(
                        addModelItem("A-Class"),
                        addModelItem("B-Class"),
                        addModelItem("C-Class"),
                        addModelItem("CL-Class"),
                        addModelItem("CLA"),
                        addModelItem("CLA-Class"),
                        addModelItem("CLK-Class"),
                        addModelItem("CLS-Class"),
                        addModelItem("E-Class"),
                        addModelItem("G-Class"),
                        addModelItem("GL-Class"),
                        addModelItem("Gla"),
                        addModelItem("GlA-Class"),
                        addModelItem("GLB"),
                        addModelItem("GLC Coup"),
                        addModelItem("GLE"),
                        addModelItem("GLE Coup"),
                        addModelItem("GLK-Class"),
                        addModelItem("GLS"),
                        addModelItem("M-Class"),
                        addModelItem("Mercedes-AMG A-Class"),
                        addModelItem("Mercedes-AMG C-CLass"),
                        addModelItem("Mercedes-AMG CLA"),
                        addModelItem("Mercedes-AMG CLS"),
                        addModelItem("Mercedes-AMG E-Class"),
                        addModelItem("Mercedes-AMG GLA"),
                        addModelItem("Mercedes-AMG GLC"),
                        addModelItem("Mercedes-AMG GLC Coup"),
                        addModelItem("Mercedes-AMG GLE Coup"),
                        addModelItem("Mercedes-AMG GT"),
                        addModelItem("Mercedes-AMG S-Class"),
                        addModelItem("Mercedes-AMG SL"),
                        addModelItem("Mercedes-AMG SLC"),
                        addModelItem("Mercedes-EQ EQB"),
                        addModelItem("Mercedes-EQ EQE"),
                        addModelItem("Metris Passenger"),
                        addModelItem("S-Class"),
                        addModelItem("SL"),
                        addModelItem("SL-Class"),
                        addModelItem("SLC"),
                        addModelItem("SLK"),
                        addModelItem("SLK-CLass")
                        // Add more models as needed
                );
                break;
            case "MINI":
                Model.getItems().addAll(
                        addModelItem("CLubman"),
                        addModelItem("Convertible"),
                        addModelItem("Countryman"),
                        addModelItem("Coupe"),
                        addModelItem("Hardtop"),
                        addModelItem("Hardtop 2 Door"),
                        addModelItem("Hardtop 4 Door"),
                        addModelItem("Paceman"),
                        addModelItem("Roadster")
                        // Add more models as needed
                );
                break;
            case "Mitsubishi":
                Model.getItems().addAll(
                        addModelItem("Eclipse"),
                        addModelItem("Eclipse Cross"),
                        addModelItem("i=MiEV"),
                        addModelItem("Lancer"),
                        addModelItem("Lancer Evolution"),
                        addModelItem("Mirage"),
                        addModelItem("Mirage G4"),
                        addModelItem("Outlander"),
                        addModelItem("Outlander PHEV"),
                        addModelItem("Outlander Sport")
                        // Add more models as needed
                );
                break;
            case "Nissan":
                Model.getItems().addAll(
                        addModelItem("370Z"),
                        addModelItem("ALtima"),
                        addModelItem("Ariya"),
                        addModelItem("Armada"),
                        addModelItem("Frontier Crew Cab"),
                        addModelItem("Frontier King Cab"),
                        addModelItem("GT-R"),
                        addModelItem("JUKE"),
                        addModelItem("Kicks"),
                        addModelItem("LEAF"),
                        addModelItem("Maxima"),
                        addModelItem("Murano"),
                        addModelItem("NV1500 Cargo"),
                        addModelItem("Pathfinder"),
                        addModelItem("Quest"),
                        addModelItem("Rogue"),
                        addModelItem("Rouge Select"),
                        addModelItem("Rouge Sport"),
                        addModelItem("Sentra"),
                        addModelItem("Titan Crew Cab"),
                        addModelItem("Titan King Cab"),
                        addModelItem("TITAN Single Cab"),
                        addModelItem("TITAN XD Crew Cab"),
                        addModelItem("TITAN XD Single Cab"),
                        addModelItem("Versa"),
                        addModelItem("Versa Note"),
                        addModelItem("Xterra")
                        // Add more models as needed
                );
                break;
            case "Polester":
                Model.getItems().addAll(
                        addModelItem("2")
                        // Add more models as needed
                );
                break;
            case "Porsche":
                Model.getItems().addAll(
                        addModelItem("718 Boxter"),
                        addModelItem("718 Cayman"),
                        addModelItem("911"),
                        addModelItem("Boxter"),
                        addModelItem("Cayenne"),
                        addModelItem("Cayman"),
                        addModelItem("Macan"),
                        addModelItem("Panamera"),
                        addModelItem("Taycan")
                        // Add more models as needed
                );
                break;
            case "Ram":
                Model.getItems().addAll(
                        addModelItem("1500 Classic Crew Cab"),
                        addModelItem("1500 Classic Quad Cab"),
                        addModelItem("1500 Classic Regular Cab"),
                        addModelItem("1500 Crew Cab"),
                        addModelItem("1500 Quad Cab"),
                        addModelItem("1500 Regular Cab"),
                        addModelItem("2500 Crew Cab"),
                        addModelItem("3500 Crew Cab"),
                        addModelItem("ProMaster Cargo Van")
                        // Add more models as needed
                );
                break;
            case "Rivian":
                Model.getItems().addAll(
                        addModelItem("R1T")
                        // Add more models as needed
                );
                break;
            case "Scion":
                Model.getItems().addAll(
                        addModelItem("FR-S"),
                        addModelItem("iA"),
                        addModelItem("iM"),
                        addModelItem("iQ"),
                        addModelItem("tC"),
                        addModelItem("xB"),
                        addModelItem("xD")
                        // Add more models as needed
                );
                break;
            case "smart":
                Model.getItems().addAll(
                        addModelItem("fortwo"),
                        addModelItem("fortwo electric drive"),
                        addModelItem("fortwo electric drive cabino")
                        // Add more models as needed
                );
                break;
            case "Subaru":
                Model.getItems().addAll(
                        addModelItem("Ascent"),
                        addModelItem("BRZ"),
                        addModelItem("Crosstrek"),
                        addModelItem("Forester"),
                        addModelItem("Impreza"),
                        addModelItem("Legacy"),
                        addModelItem("Outback"),
                        addModelItem("Solterra"),
                        addModelItem("WRX"),
                        addModelItem("XV Crosstrek")
                        // Add more models as needed
                );
                break;
            case "Tesla":
                Model.getItems().addAll(
                        addModelItem("Model 3"),
                        addModelItem("Model S"),
                        addModelItem("Model x"),
                        addModelItem("Model Y")
                        // Add more models as needed
                );
                break;
            case "Toyota":
                Model.getItems().addAll(
                        addModelItem("4Runner"),
                        addModelItem("86"),
                        addModelItem("Avalon"),
                        addModelItem("Avalon Hybrid"),
                        addModelItem("bZ4X"),
                        addModelItem("C-HR"),
                        addModelItem("Camry"),
                        addModelItem("Camry Hybrid"),
                        addModelItem("Corolla"),
                        addModelItem("Corolla Cross"),
                        addModelItem("Corolla Hatchback"),
                        addModelItem("Corolla Hybrid"),
                        addModelItem("Corolla iM"),
                        addModelItem("Crown"),
                        addModelItem("FJ Cruiser"),
                        addModelItem("GR Supra"),
                        addModelItem("GR 86"),
                        addModelItem("Highlander"),
                        addModelItem("Highlander Hybrid"),
                        addModelItem("Matrix"),
                        addModelItem("Prius"),
                        addModelItem("Prius c"),
                        addModelItem("Prius Plug-in Hybrid"),
                        addModelItem("Prius Prime"),
                        addModelItem("Prius v"),
                        addModelItem("RAV4"),
                        addModelItem("RAV4 Hybrid"),
                        addModelItem("RAV4 Prime"),
                        addModelItem("Sequoia"),
                        addModelItem("Sienna"),
                        addModelItem("Tacoma Access Cab"),
                        addModelItem("Tacoma Double Cab"),
                        addModelItem("Tacoma Regular Cab"),
                        addModelItem("Tundra CrewMax"),
                        addModelItem("Tundra Double Cab"),
                        addModelItem("Tundra Hybrid CrewMax"),
                        addModelItem("Venza"),
                        addModelItem("Yaris"),
                        addModelItem("Yaris Hatchback"),
                        addModelItem("Yaris iA")
                        // Add more models as needed
                );
                break;
            case "Volkswagon":
                Model.getItems().addAll(
                        addModelItem("Arteon"),
                        addModelItem("Atlas"),
                        addModelItem("Atlas Cross Sport"),
                        addModelItem("Beetle"),
                        addModelItem("CC"),
                        addModelItem("e-Golf"),
                        addModelItem("Eos"),
                        addModelItem("Golf"),
                        addModelItem("Golf Alltrack"),
                        addModelItem("Golf GTI"),
                        addModelItem("Golf R"),
                        addModelItem("Golf SportWagon"),
                        addModelItem("GTI"),
                        addModelItem("ID.4"),
                        addModelItem("Jetta"),
                        addModelItem("Jetta GLI"),
                        addModelItem("Jetta SportWagon"),
                        addModelItem("New Beetle"),
                        addModelItem("Passat"),
                        addModelItem("Taos"),
                        addModelItem("Tiguan"),
                        addModelItem("Tiguan Limited"),
                        addModelItem("Touareg")
                        // Add more models as needed
                );
                break;
            case "Volvo":
                Model.getItems().addAll(
                        addModelItem("C30"),
                        addModelItem("C40 Recharge"),
                        addModelItem("C70"),
                        addModelItem("S60"),
                        addModelItem("S80"),
                        addModelItem("S90"),
                        addModelItem("V50"),
                        addModelItem("V60"),
                        addModelItem("V90"),
                        addModelItem("XC40"),
                        addModelItem("XC40 Recharge"),
                        addModelItem("XC60"),
                        addModelItem("XC70"),
                        addModelItem("XC90")
                        // Add more models as needed
                );
                break;

            // Add cases for other makes...
            default:
                // Handle unknown make
                Model.getItems().addAll(
                        addModelItem("Default Model 1"),
                        addModelItem("Default Model 2")
                        // Add more default models as needed
                );
                break;
        }
        //System.out.println("Populated Models: " + Model.getItems());
    }
    @FXML
    private MenuItem addModelItem(String modelName) {
        MenuItem modelItem = new MenuItem(modelName);
        modelItem.setOnAction(event -> {
            // Handle the action when a model is selected
            handleModelSelection(modelName);
        });
        return modelItem;
    }
    @FXML
    private void handleModelSelection(String modelName) {

        Model.setText(modelName);
    }


}