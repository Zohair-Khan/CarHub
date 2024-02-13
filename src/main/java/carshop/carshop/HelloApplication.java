package carshop.carshop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Stack;

public class HelloApplication extends Application  {
    private Stage primaryStage;
    private Navigation navManager = new Navigation();
    private Stack<Scene> sceneStack = new Stack<>();


    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        loadScene("Login.fxml");
        primaryStage.setTitle("Home");
        //primaryStage.setMaximized(true);
        primaryStage.show();
    }
    public void loadScene(String fxmlFiLeName) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFiLeName));
            Parent root = loader.load();
            LoginController controller = loader.getController();

            Scene scene = new Scene(root, 1200, 800);
            navManager.pushScene(primaryStage.getScene());
            //primaryStage.setMaximized(true);
            primaryStage.setScene(scene );

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}