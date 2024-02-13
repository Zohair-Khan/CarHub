package carshop.carshop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class VehicleProfileController implements Initializable {


    @FXML
    private Button Delete;
    @FXML
    private Button Sign_Out;
    @FXML
    private ImageView Home_Img;
    @FXML
    private HBox Login;
    @FXML
    private MenuButton Browse;
    @FXML
    private Label Contact;
    @FXML
    private HBox Contact_Button;
    @FXML
    private Label Login_Label;
    @FXML
    private Label Drive1;
    @FXML
    private Label Drive2;
    @FXML
    private Label Drive3;
    @FXML
    private Label ExtColor1;
    @FXML
    private Label IntColor1;
    @FXML
    private Label Make1;
    @FXML
    private Label Make2;
    @FXML
    private Label Mileage1;
    @FXML
    private Label Mileage2;
    @FXML
    private Label Model1;
    @FXML
    private Label Model2;
    @FXML
    private Label Phone1;
    @FXML
    private Label Price1;
    @FXML
    private Label Price2;
    @FXML
    private Label Year1;
    @FXML
    private Label Year2;
    @FXML
    private Label Year3;
    @FXML
    private ImageView Vehicle_Image;
    private HomePageController controller;
    private Listing selectedListing;
    private Listing selectedListing2;
    private Listing selectedListing3;
    private Dialog_Interface dialogInterface;





    public VehicleProfileController() {
    }
    public void setDialogInterface(Dialog_Interface dialogInterface) {
        this.dialogInterface = dialogInterface;
    }
    @FXML
    void On_Browse_Clicked(ActionEvent event) {
    }


    @FXML
    void On_Contact_Clicked(MouseEvent event) {
        User user = UserSession.getInstance().getLoggedInUser();
        Listing ls = getSelectedListing();
        setDialogInterface(new Dialog_Box());

        // Check if the listing is not null, and that it has not buyer
        if (ls != null  && ls.getBuyer() == 0 && ls.getSeller() != user.getId()) {
            ListingDatabase.buyListing(ls, user);
            openHomePage();
            System.out.println("Bought the Car!");
            //showPurchaseSuccessfulDialog();
            dialogInterface.purchaseSuccessful();
        } else {
            System.out.println("Already sold");
            dialogInterface.purchaseFailed();
        }
    }

    @FXML
    void On_Delete_Clicked(ActionEvent event) {
        User user = UserSession.getInstance().getLoggedInUser();
        Listing ls = getSelectedListing2();
        setDialogInterface(new Dialog_Box());
        // Check if the listing is not null, and that it has not buyer
        if (ls != null  && ls.getBuyer() == 0 && ls.getSeller() == user.getId()) {

            ListingDatabase.deleteListing(ls, user);
            openHomePage();
            System.out.println("deleted");
            dialogInterface.deleteSuccessful();
        } else {
            System.out.println("Delete your own car!");
            dialogInterface.deleteFailed();
        }
        //openUserPage();
    }

    @Override
    public void initialize(URL location, ResourceBundle recources){
        updateLoginLabel();
    }
    public void settSelectedListing(Listing list){
        this.selectedListing = list;
    }
    public Listing getSelectedListing(){
        return this.selectedListing;
    }
    public void settSelectedListing2(Listing list){
        this.selectedListing2 = list;
    }
    public Listing getSelectedListing2(){
        return this.selectedListing2;
    }
    public Listing getSelectedListing3() {
        return this.selectedListing3;
    }
    public void setSelectedListing3(Listing list){
        this.selectedListing3 = list;
    }


    private void updateLoginLabel() {
        if (UserSession.getInstance().isLoggedIn()) {
            Login_Label.setText("Welcome, " + UserSession.getInstance().getLoggedInUser().getName() + "!");
        } else {
            Login_Label.setText("Login");
        }
    }
    private void openLoginPage() {
        // Open the "Login.fxml" file
        try {
            // Assuming "Login.fxml" is in the same package as LoginController
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) Login.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Login");
            stage.setMaximized(true);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void openHomePage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
            Parent root = loader.load();

            // Pass the username to the UserController
            HomePageController homeController = loader.getController();
            homeController.initialize();

            Stage stage = (Stage) Sign_Out.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Home Page");
            stage.setMaximized(true);
            stage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void openUserPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UserPage.fxml"));
            Parent root = loader.load();

            // Pass the username to the UserController
            UserPageController userController = loader.getController();
            userController.setLoggedInUser(UserSession.getInstance().getLoggedInUser());

            Stage stage = (Stage) Login.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("User Page");
            stage.setMaximized(true);
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
        setDialogInterface(new Dialog_Box());
        dialogInterface.signOutSuccessful();
    }
    @FXML
    void On_Home_Img_Clicked(MouseEvent event) {
        openHomePage();
    }

    public void setData(Listing car){
        if (car != null) {
            //Image image = new Image(getClass().getResourceAsStream(car.getImage()));
            //URL url = new URL("https://www.iconsdb.com/icons/preview/gray/car-26-xxl.png");
//            Image image = new Image("https://www.iconsdb.com/icons/preview/gray/car-26-xxl.png");
            Image image = ListingDatabase.getImage(car.getId());
            Vehicle_Image.setImage(image);
            Drive1.setText(car.getDriveTerrain());
            Drive2.setText(car.getDriveTerrain());
            Drive3.setText(car.getDriveTerrain());
            ExtColor1.setText(car.getExteriorColor());
            IntColor1.setText(car.getInteriorColor());
            Make1.setText(car.getMake());
            Make2.setText(car.getMake());
            Mileage1.setText(car.getMileage());
            Mileage2.setText(car.getMileage());
            Model1.setText(car.getModel());
            Model2.setText(car.getModel());
            Phone1.setText(car.getSellerPhone());
            Price1.setText("$"+car.getPrice());
            Price2.setText("$"+car.getPrice());
            Year1.setText(car.getYear());
            Year2.setText(car.getYear());
            Year3.setText(car.getYear());
            Delete.setText("");
            Delete.setStyle("-fx-background-color: transparent");

        }

    }
    public void setData2(Listing car){
        if (car != null) {
            // Image image = new Image(getClass().getResourceAsStream(car.getImage()));
            //Image image = new Image("https://www.iconsdb.com/icons/preview/gray/car-26-xxl.png");
            Image image = ListingDatabase.getImage(car.getId());


            Vehicle_Image.setImage(image);
            Drive1.setText(car.getDriveTerrain());
            Drive2.setText(car.getDriveTerrain());
            Drive3.setText(car.getDriveTerrain());
            ExtColor1.setText(car.getExteriorColor());
            IntColor1.setText(car.getInteriorColor());
            Make1.setText(car.getMake());
            Make2.setText(car.getMake());
            Mileage1.setText(car.getMileage());
            Mileage2.setText(car.getMileage());
            Model1.setText(car.getModel());
            Model2.setText(car.getModel());
            Phone1.setText(car.getSellerPhone());
            Price1.setText("$"+car.getPrice());
            Price2.setText("$"+car.getPrice());
            Year1.setText(car.getYear());
            Year2.setText(car.getYear());
            Year3.setText(car.getYear());
        }
    }

    @FXML
    void On_Login_Clicked(MouseEvent event) {
        if (UserSession.getInstance().isLoggedIn()) {
            // Redirect to UserPage.fxml
            openUserPage();
        } else {
            // Redirect to Login.fxml
            openLoginPage();
        }
    }
}

