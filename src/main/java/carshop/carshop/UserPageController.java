package carshop.carshop;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.ResourceBundle;
public class UserPageController{


    @FXML
    private Button Sign_Out;
    @FXML
    private Label User_Label;
    @FXML
    private Label photo_Label;
    @FXML
    private ImageView Home_Img;
    @FXML
    private HBox Add_List;
    @FXML
    private ImageView Back;
    @FXML
    private HBox Delete_Account;
    @FXML
    private TextField Email;
    @FXML
    private TextField Name;
    @FXML
    private TextField Password;
    @FXML
    private MenuButton Menu_Button;
    @FXML
    private TextField Phone;
    @FXML
    private HBox Profile;
    @FXML
    private ImageView Profile_Img;
    @FXML
    private HBox Remove_List;
    @FXML
    private Button Update_Profile_Img;
    @FXML
    private Button Update_User_Info;
    @FXML
    private HBox User;
    private File selectedImage;
    private User loggedInUser;
    private Dialog_Interface dialogInterface;




    public void initialize(){
        updateLoginLabel();
        updatePhotoLabel();
    }
    public void setDialogInterface(Dialog_Interface dialogInterface) {
        this.dialogInterface = dialogInterface;
    }


    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
        updateLoginLabel();
    }
    @FXML
    public void Add_List_Clicked(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AddListing.fxml")));
            Stage stage = (Stage) Add_List.getScene().getWindow();
            Scene scene = new Scene(root, 1200, 800);
            stage.setScene(scene);
            stage.setTitle("Add Listing Page");

            setFixedStageSize(stage);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void Home_Img_Clicked(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("HomePage.fxml")));
            Stage stage = (Stage) Home_Img.getScene().getWindow();
            Scene scene = new Scene(root, 1200, 800);
            stage.setScene(scene);
            stage.setTitle("Home Page");
            setFixedStageSize(stage);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void On_Update_Profile_Img_Clicked(ActionEvent event) {
        // Open a file chooser dialog
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        selectedImage = fileChooser.showOpenDialog(new Stage());
        if (selectedImage != null) {
            // Display the selected image path
            photo_Label.setText(selectedImage.getAbsolutePath());
            // Load the selected image into the ImageView
            Image image = new Image(selectedImage.toURI().toString());
            Profile_Img.setImage(image);
            // Save the selected image to your project (optional)
            saveImageToProject(selectedImage);
        }
    }
    @FXML
    void On_Update_User_Info_Clicked(ActionEvent event) {
        // Retrieve user input
        String name = Name.getText();
        String email = Email.getText();
        String phone = Phone.getText();
        String password = Password.getText();
        String image = photo_Label.getText();
        // Create a new user
        User newUser = UserSession.getInstance().getLoggedInUser();;
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPhone(phone);
        newUser.setPassword(password);
        if (image != null) {
            newUser.setImage(image);
        } else {
            newUser.setImage("profile.png");
            //System.out.println("Image input stream is null. Please upload an image.");
        }
        // Add the user to the data storage
        UserDatabase.updateUserTable(newUser);
        openHomePage();
    }
    private void saveImageToProject(File imageFile) {
        // Define the destination path in your project
        File destination = new File("src/main/resources/carshop/carshop" + imageFile.getName());
        try {
            // Copy the image file to the project
            Files.copy(imageFile.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
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
    void On_Back_Clicked(MouseEvent event) {
    }
    @FXML
    void On_Delete_Account_Clicked(MouseEvent event) {
    }
    @FXML
    void On_Email_Typed(ActionEvent event) {
    }



    @FXML
    void On_Menu_Button_Clicked(ActionEvent event) {
    }
    @FXML
    void On_Phone_Typed(ActionEvent event) {
    }
    @FXML
    void On_Profile_Clicked(MouseEvent event) {
        openUserPage();
    }
    @FXML
    void On_Profile_Img_Clicked(MouseEvent event) {

    }
    @FXML
    void On_Remove_Clicked(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("RemoveListing.fxml")));
            Scene scene = new Scene(root);
            Stage stage = (Stage) Remove_List.getScene().getWindow();
            stage.setTitle("Vew/Remove Listing Page");
            stage.setMaximized(true);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void On_User_Clicked(MouseEvent event) {
        if (UserSession.getInstance().isLoggedIn()) {
            // Redirect to UserPage.fxml (optional, you can add more logic)
            openUserPage();
        } else {
            // Redirect to Login.fxml
            openLoginPage();
        }
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
    private void openUserPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UserPage.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) User.getScene().getWindow();
            Scene scene = new Scene(root, 1200, 800);
            stage.setScene(scene);
            stage.setTitle("User Page");
            setFixedStageSize(stage);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openLoginPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) User.getScene().getWindow();
            Scene scene = new Scene(root, 1200, 800);
            stage.setScene(scene);
            stage.setTitle("Login");
            setFixedStageSize(stage);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setFixedStageSize(Stage stage) {
        stage.setMinWidth(1200);
        stage.setMinHeight(800);
        stage.setMaxWidth(1200);
        stage.setMaxHeight(800);
    }


//    public void setUserName(String userName) {
//        User_Label.setText("Welcome, " + userName + "!");
//    }

    private void updateLoginLabel() {
        if (UserSession.getInstance().isLoggedIn()) {
            User_Label.setText("Welcome, " + UserSession.getInstance().getLoggedInUser().getName() + "!");
        } else {
            User_Label.setText("Login");
        }
    }
    private void updatePhotoLabel() {
        if (UserSession.getInstance().isLoggedIn()) {
            Profile_Img.getImage();
        } else {
            User_Label.setText("Login");
        }
    }



}
