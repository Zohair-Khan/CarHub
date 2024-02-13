package carshop.carshop;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class Dialog_Box implements Dialog_Interface{
    @Override
    public void purchaseSuccessful() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Purchase Successful");
        alert.setHeaderText(null);
        alert.setContentText("You've successfully bought the car!");

        // Customize the alert dialog button (e.g., change the default "OK" button text)
        alert.getButtonTypes().setAll(ButtonType.OK);

        // Show and wait for the user to close the dialog
        alert.showAndWait();
    }

    @Override
    public void signOutSuccessful() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sign Out Successful");
        alert.setHeaderText(null);
        alert.setContentText("Signed Out!");

        // Customize the alert dialog button (e.g., change the default "OK" button text)
        alert.getButtonTypes().setAll(ButtonType.OK);

        // Show and wait for the user to close the dialog
        alert.showAndWait();
    }

    @Override
    public void LoginSuccessful() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Loin Successful");
        alert.setHeaderText(null);
        alert.setContentText("Logged in");

        // Customize the alert dialog button (e.g., change the default "OK" button text)
        alert.getButtonTypes().setAll(ButtonType.OK);

        // Show and wait for the user to close the dialog
        alert.showAndWait();
    }

    @Override
    public void SignUpSuccessful() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sign Up Successful");
        alert.setHeaderText(null);
        alert.setContentText("Successfully Signed Up. Welcome!");

        // Customize the alert dialog button (e.g., change the default "OK" button text)
        alert.getButtonTypes().setAll(ButtonType.OK);

        // Show and wait for the user to close the dialog
        alert.showAndWait();
    }

    @Override
    public void deleteSuccessful() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Delete Successful");
        alert.setHeaderText(null);
        alert.setContentText("You've successfully deleted your listing");

        // Customize the alert dialog button (e.g., change the default "OK" button text)
        alert.getButtonTypes().setAll(ButtonType.OK);

        // Show and wait for the user to close the dialog
        alert.showAndWait();
    }

    @Override
    public void purchaseFailed() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Purchase Failed");
        alert.setHeaderText(null);
        alert.setContentText("Unfortunately, this vehicle is unavailable for purchase. Try a different vehicle.");

        // Customize the alert dialog button (e.g., change the default "OK" button text)
        alert.getButtonTypes().setAll(ButtonType.OK);

        // Show and wait for the user to close the dialog
        alert.showAndWait();
    }



    @Override
    public void LoginFailed() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Login Failed");
        alert.setHeaderText(null);
        alert.setContentText("Invalid Email/Password. Try again!");

        // Customize the alert dialog button (e.g., change the default "OK" button text)
        alert.getButtonTypes().setAll(ButtonType.OK);

        // Show and wait for the user to close the dialog
        alert.showAndWait();
    }

    @Override
    public void SignUpFailed() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sign Up Failed");
        alert.setHeaderText(null);
        alert.setContentText("Invalid credentials. Register with valid credentials.");

        // Customize the alert dialog button (e.g., change the default "OK" button text)
        alert.getButtonTypes().setAll(ButtonType.OK);

        // Show and wait for the user to close the dialog
        alert.showAndWait();
    }

    @Override
    public void deleteFailed() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Delete Failed");
        alert.setHeaderText(null);
        alert.setContentText("You cannot delete this vehicle.");

        // Customize the alert dialog button (e.g., change the default "OK" button text)
        alert.getButtonTypes().setAll(ButtonType.OK);

        // Show and wait for the user to close the dialog
        alert.showAndWait();
    }
    @Override
    public void AddListSuccessful() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Add Listing Successful");
        alert.setHeaderText(null);
        alert.setContentText("Successfully added. Congrats!");

        // Customize the alert dialog button (e.g., change the default "OK" button text)
        alert.getButtonTypes().setAll(ButtonType.OK);

        // Show and wait for the user to close the dialog
        alert.showAndWait();
    }
    @Override
    public void sold() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Listing Sold");
        alert.setHeaderText(null);
        alert.setContentText("This vehicle is SOLD!. Please choose another vehicle!");

        // Customize the alert dialog button (e.g., change the default "OK" button text)
        alert.getButtonTypes().setAll(ButtonType.OK);

        // Show and wait for the user to close the dialog
        alert.showAndWait();
    }
}
