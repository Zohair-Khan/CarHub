package carshop.carshop;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebView;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Label User_Label;
    @FXML
    private MenuButton Browse;
    @FXML
    private TextField Email1;
    @FXML
    private TextField Email2;
    @FXML
    private HBox Login;
    @FXML
    private TextField Name;
    @FXML
    private PasswordField Password1;
    @FXML
    private PasswordField Password2;
    @FXML
    private TextField Phone;
    @FXML
    private Button SignIn;
    @FXML
    private Button SignUp;
    @FXML
    private ImageView Home_Img;
    @FXML
    private ImageView back2_Button;
    @FXML
    private Navigation navManager;
    private Dialog_Interface dialogInterface;



    public void setNavManager(Navigation navManager) {
        this.navManager = navManager;
    }

    public void initialize(URL location, ResourceBundle resources){
        // Observe loginStatusChanged property and update UI accordingly
        UserSession.getInstance().loginStatusChangedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                // Login status changed, update UI
                updateLoginLabel();
            }
        });
    }
    public void initialize(){
        updateLoginLabel();
    }

    public void setDialogInterface(Dialog_Interface dialogInterface) {
        this.dialogInterface = dialogInterface;
    }
    private void updateLoginLabel() {
        if (UserSession.getInstance().isLoggedIn()) {
            User_Label.setText("Welcome, " + UserSession.getInstance().getLoggedInUser().getName() + "!");
        } else {
            User_Label.setText("Login");
        }
    }

    @FXML
    public void onBack2Clicked(MouseEvent event) {

        System.out.println("back 2 clicked");
        if (navManager != null) {
            Scene previousScene = navManager.popScene();
            if (previousScene != null) {
                Stage stage = (Stage) back2_Button.getScene().getWindow();
                stage.setScene(previousScene);
            }
        }
    }

    @FXML
    public void Home_Img_Clicked(MouseEvent event) {
//        try {
//            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("HomePage.fxml")));
//            Scene scene = new Scene(root);
//            Stage stage = (Stage) Home_Img.getScene().getWindow();
//            stage.setFullScreen(true);
//            stage.setTitle("Home Page");
//            stage.setScene(scene);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        openHomePage();

    }

    @FXML
    void On_Login_Clicked(MouseEvent event) {

        if (UserData.isLoggedIn()) {
            // Redirect to UserPage.fxml
            openUserPage();
        } else {
            // Redirect to Login.fxml
            openLoginPage();

        }
    }

    @FXML
    void On_Browse_Clicked(ActionEvent event) {

    }

    @FXML
    void On_Email1_Clicked(ActionEvent event) {

    }

    @FXML
    void On_Email2_Clicked(ActionEvent event) {

    }

    @FXML
    void On_Name_Clicked(ActionEvent event) {

    }

    @FXML
    void On_Password1_Clicked(ActionEvent event) {

    }

    @FXML
    void On_Password2_Clicked(ActionEvent event) {

    }

    @FXML
    void On_Phone_Clicked(ActionEvent event) {

    }

    @FXML
    void On_SignIn_Clicked(ActionEvent event) {
        setDialogInterface(new Dialog_Box());
        // Get the user input
        String email = Email1.getText();
        String password = Password1.getText();

        // Check if the user exists in the data storage
        User loggedInUser = UserDatabase.getUsers().stream()
                .filter(user -> user.getEmail().equals(email) && user.getPassword().equals(password))
                .findFirst()
                .orElse(null);
        if (loggedInUser != null) {
            // Login successful
            System.out.println("Login successful!");
            dialogInterface.LoginSuccessful();
            UserData.setLoggedIn(true);
            // Set the loggedIn user in UserSession
            UserSession.getInstance().setLoggedInUser(loggedInUser);

            // Update the login label text (you can do this in a shared controller or wherever appropriate)
            updateLoginLabel();

            // Open the user page (you can do this in a shared controller or wherever appropriate)
            openHomePage();
        } else {
            // Display an error message for invalid login
            System.out.println("Invalid email or password.");
            dialogInterface.LoginFailed();
        }
    }

    @FXML
    void On_SignUp_Clicked(ActionEvent event) {
        setDialogInterface(new Dialog_Box());
        // Retrieve user input
        String name = Name.getText();
        String email = Email2.getText();
        String phone = Phone.getText();
        String password = Password2.getText();

        // Create a new user
        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPhone(phone);
        newUser.setPassword(password);
        System.out.println(newUser.getName());
        System.out.println(newUser.getEmail());
        System.out.println(newUser.getPhone());
        System.out.println(newUser.getPassword());

        // Add the user to the data storage
        boolean registerSuccessful = UserDatabase.addUser(newUser);

        if (registerSuccessful) {
            UserSession.getInstance().setLoggedInUser(newUser);
            updateLoginLabel();
            openHomePage();
            dialogInterface.SignUpSuccessful();
        }
        else {
            System.out.println("User-Name and/or Phone is Taken. Please Enter a valid username or phone ");
            dialogInterface.SignUpFailed();
        }


    }
    private void openHomePage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
            Parent root = loader.load();

            // Pass the username to the HomeController
            HomePageController homeController = loader.getController();
            homeController.initialize();

            Stage stage = (Stage) Login.getScene().getWindow();
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

            // Pass the username to the UserController
            UserPageController userPageController = loader.getController();
            userPageController.setLoggedInUser(UserSession.getInstance().getLoggedInUser());

            Stage stage = (Stage) Login.getScene().getWindow();
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

            Stage stage = (Stage) Login.getScene().getWindow();
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
//    private void closeWindow() {
//        Stage stage = (Stage) Name.getScene().getWindow();
//        stage.close();
//
//    }

}
