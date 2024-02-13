package carshop.carshop;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;


public class VehicleController {

    @FXML
    private ImageView Heart;
    private Listing listing;
    @FXML
    private Label Delete;
    @FXML
    private VBox box2;
    @FXML
    private ImageView carListImage;
    @FXML
    private Label carListName;
    @FXML
    private Label carListType;
    private String[] colors = {"FFF6B6"};
    private List<Listing> listings;

    private Listing selectedListing;
    private VehicleProfileController vehicleProfileController;
    private Dialog_Interface dialogInterface;





    // Getter and setter for Listing
    public Listing getListing() {
        return listing;
    }
    public void setListing(Listing listing) {
        this.listing = listing;
    }
    public List<Listing> listings(){
        List<Listing> ls = new ArrayList<>();
        for (Listing list : ListingDatabase.getListings()){
            list.getMake();
            list.getModel();
            list.getYear();
            list.getPrice();
            list.getMileage();
            list.getDriveTerrain();
            list.getExteriorColor();
            list.getInteriorColor();
            list.getSeller();
            list.getBuyer();
            list.getSellerPhone();
            ls.add(list);
        }
        return ls;
    }

    @FXML
    public void initialize() {
        // Use the listings() method to get the list of listings
        listings = listings();


        // Additional initialization logic...
    }
    public void setDialogInterface(Dialog_Interface dialogInterface) {
        this.dialogInterface = dialogInterface;
    }
    @FXML
    void Car_Clicked(MouseEvent event) {
//        try {
//            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("VehicleProfile.fxml")));
//            Scene scene = new Scene(root);
//            Stage stage = (Stage) carListImage.getScene().getWindow();
//            stage.setScene(scene);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }
    public void setData(Listing car){
//        Image image = new Image(getClass().getResourceAsStream("/https://www.iconsdb.com/icons/preview/gray/car-26-xxl.png"));
//        Image image = new Image(getClass().getClassLoader().getResourceAsStream(car.getImage()));
//        carListImage.setImage(image);
        Image image = ListingDatabase.getImage(car.getId());
        carListImage.setImage(image);
        carListName.setText(car.getMake());
        carListType.setText(car.getDriveTerrain());
        Delete.setStyle("-fx-background-color: transparent");
        if (car.getBuyer() != 0){
            Delete.setText("Sold!");
            Delete.setStyle("-fx-text-fill: black; -fx-font-size: 27px");
            //dialogInterface.sold();
        }else {
            Delete.setText("");
        }
        Heart.setVisible(false);

        box2.setStyle("-fx-background-color: #"+ colors[(int)(Math.random()*colors.length)] + ";" +
                "-fx-background-radius: 15; ");
    }
    public void setData2(Listing car){
        //Image image = new Image(getClass().getResourceAsStream(car.getImage()));
        // Image image = new Image("https://media.istockphoto.com/id/1189903200/photo/red-generic-sedan-car-isolated-on-white-background-3d-illustration.jpg?s=612x612&w=is&k=20&c=8HIi_4MPZGooiiFb-vrjvW4TmJXLnwXgpnn6wI8jjk8=");
        Image image = ListingDatabase.getImage(car.getId());
        carListImage.setImage(image);
        carListName.setText(car.getMake());
        carListType.setText(car.getDriveTerrain());
        Delete.setStyle("-fx-background-color: transparent");
        if (car.getBuyer() != 0){
            Delete.setText("Sold!");
            Delete.setStyle("-fx-text-fill: black; -fx-font-size: 27px");
            //Delete.setStyle("-fx-background-color: transparent");
        }else{
            Delete.setText("");
        }
        Heart.setVisible(false);

        box2.setStyle("-fx-background-color: #"+ colors[(int)(Math.random()*colors.length)] + ";" +
                "-fx-background-radius: 15; ");
    }


    @FXML
    void Delete_Clicked(MouseEvent event) {
    }


}