package demo2.demo2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    public TextField productTF;
    @FXML
    private Label welcomeText;
    @FXML
    private TextField partTF;

    @FXML
    private TableView<Part>mainPartTable;

    @FXML
    private TableColumn<Part, Integer>partIdCol;

    @FXML
    private TableColumn<Part, String>partNameCol;

    @FXML
    private TableColumn<Part, Integer>partInventoryCol;

    @FXML
    private TableColumn<Part, Double>partPriceCol;

    @FXML
    private TableView<Product>mainProductTable;

    @FXML
    private TableColumn<Product, Integer>productIdCol;

    @FXML
    private TableColumn<Product, String>productNameCol;

    @FXML
    private TableColumn<Product, Integer>productInventoryCol;

    @FXML
    private TableColumn<Product, Double>productPriceCol;

    /**
     * Initializes the home screen mainPart table and mainProduct tables.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        mainPartTable.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        mainProductTable.setItems(Inventory.getAllProducts());
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * Upon clicking add(Part), user is sent to add Part screen.
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void toAddPartScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/demo2/demo2/AddPart.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * upon clicking modify(Part), user is sent to modify product screen.
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void toModifyPartScreen(ActionEvent actionEvent) throws IOException {
        try {
            Part selectedPart = mainPartTable.getSelectionModel().getSelectedItem();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/demo2/demo2/modifyPart.fxml"));
            Parent root = loader.load();
            modifyPartController mpc = loader.getController();
            mpc.loadPart(selectedPart);
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Nothing selected to modify");
            alert.setTitle("Warning");
            Optional<ButtonType> result = alert.showAndWait();
        }
    }

    /**
     * upon clicking add(product), user is sent to add product screen.
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void toAddProductScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/demo2/demo2/addProduct.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * upon clicking modify(product) user is sent to modify product screen
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void toModifyProductScreen(ActionEvent actionEvent) throws IOException {
        try {
            Product modifiedProduct = mainProductTable.getSelectionModel().getSelectedItem();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/demo2/demo2/modifyProduct.fxml"));
            Parent root = loader.load();
            ModifyProductController mpc = loader.getController();
            mpc.loadProduct(modifiedProduct);
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Nothing selected to modify");
            alert.setTitle("Warning");
            Optional<ButtonType> result = alert.showAndWait();
        }
    }

    /**
     * upon clicking delete(part) the selected part is deleted.
     * @param actionEvent
     */
    public void mainDeletePart(ActionEvent actionEvent) {
        Part selectedPart = mainPartTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Can't delete");
            alert.setContentText("Nothing selected");
            Optional<ButtonType> result = alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete");
            alert.setContentText("Are you sure you want to delete?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deletePart(selectedPart);
                mainPartTable.refresh();
            }
        }

    }

    /**
     * upon clicking delete(product) the selected product is deleted if it doesnt have associated parts.
     * @param actionEvent
     */
    public void mainDeleteProduct(ActionEvent actionEvent) {
        Product selectedProduct = mainProductTable.getSelectionModel().getSelectedItem();
        if(selectedProduct == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Can't delete");
            alert.setContentText("Nothing selected");
            Optional<ButtonType> result = alert.showAndWait();
        }
        else if (selectedProduct.getAllAssociatedParts().size() > 0){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Deletion error");
            alert.setContentText("Cannot delete a product with associated items");
            Optional<ButtonType> result = alert.showAndWait();

        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete");
            alert.setContentText("Are you sure you want to delete?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deleteProduct(selectedProduct);
                mainProductTable.refresh();
            }
        }

    }

    /**
     * upon hitting enter after text is entered into (part)search text field, partresults observable list is searched.
     * FUTURE ENHANCEMENT: Users can currently search for part id or name but in the future some may want to search by cost.
     * @param actionEvent
     */
    public void mainPartSearch(ActionEvent actionEvent) {
        String qString = partTF.getText();
        ObservableList<Part> partResults = Inventory.lookupPart(qString);
        try {
            if (partResults.size() > 0) {
                mainPartTable.setItems(partResults);
            } else if (partResults.size() == 0) {
                int resultId = Integer.parseInt(qString);
                partResults.add(Inventory.lookupPart(resultId));
                mainPartTable.setItems(partResults);

            }
        if(partResults.contains(null)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No results found");
            alert.setContentText("No results found");
            Optional<ButtonType> result = alert.showAndWait();
        }
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No results found");
            alert.setContentText("No results found");
            Optional<ButtonType> result = alert.showAndWait();
        }

    }

    /**
     * upon hitting enter after text is entered into (product)search text field, productresults observable list is searched.
     * FUTURE ENHANCEMENT: In the future, text case will not matter. Currently Upper case text returns no results.
     * @param actionEvent
     */
    public void mainProductSearch(ActionEvent actionEvent) {
        String qString = productTF.getText();
        ObservableList<Product> productResults = Inventory.lookupProduct(qString);
        try {
            if(productResults.size() > 0) {
                mainProductTable.setItems(productResults);
            } else if (Integer.parseInt(qString) > 0) {
                int resultId = Integer.parseInt(qString);
                productResults.add(Inventory.lookupProduct(resultId));
                mainProductTable.setItems(productResults);
            }
            if(productResults.contains(null)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No results found");
                alert.setContentText("No results found");
                Optional<ButtonType> result = alert.showAndWait();
            }
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No results found");
            alert.setContentText("No results found");
            Optional<ButtonType> result = alert.showAndWait();
        }
    }

    /**
     * Closes application upon exit button click.
     * @param actionEvent
     */
    public void ExitWindow(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setContentText("Are you sure you want to exit?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
        }
        }


}