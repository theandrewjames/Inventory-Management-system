package demo2.demo2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * MainApplication opens the stage and loads the main screen fxml file.
 * Javadoc location: file:///Users/andrewvelasquez/Desktop/Javadoc/allclasses-index.html
 */
public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception{
        addTestData();
        Parent root = FXMLLoader.load(getClass().getResource("mainScreen.fxml"));
        stage.setTitle("Inventory Management System");
        Scene scene = new Scene(root, 800, 400);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Loads test data
     */
    private void addTestData() {
        InHouse a = new InHouse(1, "screen",10.00,1,1, 5, 12);
        System.out.println(a.getPrice());
        Inventory.addPart(a);
        OutSourced b = new OutSourced(2,"cable",2.00,1, 1, 1, "company1");
        Inventory.addPart(b);
        Product abc = new Product(555, "TV", 5.00, 1, 1, 1);
        Inventory.addProduct(abc);
    }

    public static void main(String[] args) {

        launch();
    }
}