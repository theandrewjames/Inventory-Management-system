package demo2.demo2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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

public class ModifyProductController implements Initializable {
    public TextField modSearchTF;
    public Button addPartBttn;
    public Button removeBttn;
    public Button modSveBttn;
    private ObservableList<Part> associatedList = FXCollections.observableArrayList();
    public TextField modProdIDTF;
    public TextField modProdNameTF;
    public TextField modProdInvTF;
    public TextField modProdPriceTF;
    public TextField modProdMaxTF;
    public TextField modProdMinTF;
    public TableView modProdPartTV;
    public TableColumn partIdCol;
    public TableColumn partNameCol;
    public TableColumn StockCol;
    public TableColumn Pricecol;
    public TableView modProdAssocTV;
    public TableColumn assocPartIDCol;
    public TableColumn assocPartNamecol;
    public TableColumn assocStockCol;
    public TableColumn assocPricecol;
    private int selectedIndex;
    private Product selectedProduct;

    /**
     * Initializes modify product page and sets modified product item and table views.
     * @param url
     * @param resourceBundle
     */
    @Override

    public void initialize(URL url, ResourceBundle resourceBundle) {
    modProdPartTV.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        StockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        Pricecol.setCellValueFactory(new PropertyValueFactory<>("price"));

        modProdAssocTV.setItems(associatedList);
        assocPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        assocPartNamecol.setCellValueFactory(new PropertyValueFactory<>("name"));
        assocStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assocPricecol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * returns user to home screen
     * @param actionEvent
     * @throws IOException
     */
    public void ReturnHome(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/demo2/demo2/mainScreen.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * searched partResults by hitting enter button after entering text
     * @param actionEvent
     */
    public void modProdSearch(ActionEvent actionEvent) {
        String qString = modSearchTF.getText();
        ObservableList<Part> partResults = Inventory.lookupPart(qString);
        try {
            if (partResults.size() > 0) {
                modProdPartTV.setItems(partResults);
            } else if (Integer.parseInt(qString) > 0) {

                int resultId = Integer.parseInt(qString);
                partResults.add(Inventory.lookupPart(resultId));
                modProdPartTV.setItems(partResults);
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
     * Loads the current product to modify
     * @param modifiedProduct
     */
    public void loadProduct(Product modifiedProduct) {
        selectedProduct = modifiedProduct;
        selectedIndex = Inventory.getAllProducts().indexOf(modifiedProduct);
        associatedList = modifiedProduct.getAllAssociatedParts();
        modProdAssocTV.setItems(associatedList);
    modProdIDTF.setText(String.valueOf(modifiedProduct.getId()));
    modProdNameTF.setText(modifiedProduct.getName());
    modProdInvTF.setText(String.valueOf(modifiedProduct.getStock()));
    modProdPriceTF.setText(String.valueOf(modifiedProduct.getPrice()));
    modProdMinTF.setText(String.valueOf(modifiedProduct.getMin()));
    modProdMaxTF.setText(String.valueOf(modifiedProduct.getMax()));
    }

    /**
     * adds the currently selected part to the associated list
     * @param actionEvent
     */
    public void addPart(ActionEvent actionEvent) {
        Part selectedPart = (Part) modProdPartTV.getSelectionModel().getSelectedItem();
        if(selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Can't associate");
            alert.setContentText("Nothing selected");
            Optional<ButtonType> result = alert.showAndWait();
        }
        else {
            associatedList.add(selectedPart);
            modProdAssocTV.setItems(associatedList);
        }
    }

    /**
     * removes the currently selected part from the associated list
     * @param actionEvent
     */
    public void removeAssocBttn(ActionEvent actionEvent) {
        Part selectedPart = (Part) modProdAssocTV.getSelectionModel().getSelectedItem();
        if(selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Can't remove");
            alert.setContentText("Nothing selected");
            Optional<ButtonType> result = alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Remove Associated");
            alert.setContentText("Are you sure you want to remove associated part?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                associatedList.remove(selectedPart);
                modProdAssocTV.refresh();
            }

        }
    }

    /**
     * if product meets requirements, current product is deleted and new product is saved.
     * @param actionEvent
     * @throws IOException
     */
    public void modSave(ActionEvent actionEvent) throws IOException {
        try {
            int id = Integer.parseInt(modProdIDTF.getText());
            String name = modProdNameTF.getText();
            int stock = Integer.parseInt(modProdInvTF.getText());
            double price = Double.parseDouble(modProdPriceTF.getText());
            int min = Integer.parseInt(modProdMinTF.getText());
            int max = Integer.parseInt(modProdMaxTF.getText());
            if(min > max || stock < min || stock > max) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setContentText("Min needs to be less than stock which is less than max");
                Optional<ButtonType> result = alert.showAndWait();
            }
            else {
                Product newProduct = new Product(id, name, price, stock, min, max);
                for(Part part : associatedList) {
                    newProduct.addAssociatedPart( part);
                }
                Inventory.deleteProduct(selectedProduct);
                Inventory.addProduct(newProduct);
                ReturnHome(actionEvent);
            }
        }
    catch (Exception e) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText("Incorrect or missing info");
        alert.setTitle("Warning");
        Optional<ButtonType> result = alert.showAndWait();
    }

    }
}
