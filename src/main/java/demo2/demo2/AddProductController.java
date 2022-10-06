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

public class AddProductController implements Initializable {
    public static int newId = 111;
    private ObservableList<Part> associatedList = FXCollections.observableArrayList();
    public TextField addProductIDTF;
    public TextField addProductNameTF;
    public TableView addProductPartTable;
    public Button AddAssocPartBttn;
    public TableView addProductAssocPartsTable;
    public Button RemoveAssocPartBttn;
    public Button addProductSaveBttn;
    public TextField addProductStockTF;
    public TextField addProductPriceTF;
    public TextField addProductMaxTF;
    public TextField addProductMinTF;
    public TableColumn addProdPartIDCol;
    public TableColumn addProdPartName;
    public TableColumn addProdStockCol;
    public TableColumn addProdPriceCol;
    public TextField addProductSearchTF;
    public TableColumn assocPartIdCol;
    public TableColumn assocPartNameCol;
    public TableColumn assocPartStockCol;
    public TableColumn assocPartPriceCol;

    /**
     * Initializes addProductPartTable and associatedpart Table
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addProductPartTable.setItems(Inventory.getAllParts());
        addProdPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProdPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProdStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProdPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        assocPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        assocPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        assocPartStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assocPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));


    }

    /**
     * Returns user to main screen
     * @param actionEvent
     * @throws IOException
     */
    public void ReturnHome(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/demo2/demo2/mainScreen.fxml"));
        Scene scene = new Scene(root, 800, 400);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Search text field searches partResults observablelist
     * @param actionEvent
     */
    public void addProductSearch(ActionEvent actionEvent) {
        String qString = addProductSearchTF.getText();
        ObservableList<Part> partResults = Inventory.lookupPart(qString);
        try {
            if (partResults.size() > 0) {
                addProductPartTable.setItems(partResults);
            } else if (Integer.parseInt(qString) > 0) {

                int resultId = Integer.parseInt(qString);
                partResults.add(Inventory.lookupPart(resultId));
                addProductPartTable.setItems(partResults);
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
     * Removes a part from being associated with the current product
     * @param actionEvent
     */
    public void removeAssocBttn(ActionEvent actionEvent) {
        Part selectedPart = (Part) addProductAssocPartsTable.getSelectionModel().getSelectedItem();
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
                addProductAssocPartsTable.refresh();
            }

        }

    }

    /**
     * upon clicking Save button, the product is saved to inventory.
     * RUNTIME ERROR: incompatible types: javafx.collections.ObservableList<demo2.demo2.Part> cannot be converted to demo2.demo2.Part
     * I fixed this by using a for loop.
     * @param actionEvent
     * @throws IOException
     */
    public void addProductSaveBttn(ActionEvent actionEvent) throws IOException {
        try{
            String name = addProductNameTF.getText();
            Integer stock = Integer.parseInt(addProductStockTF.getText());
            double price = Double.parseDouble(addProductPriceTF.getText());
            Integer min = Integer.parseInt(addProductMinTF.getText());
            Integer max = Integer.parseInt(addProductMaxTF.getText());
            if(min > max || stock < min || stock > max) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setContentText("Min needs to be less than stock which is less than max");
                Optional<ButtonType> result = alert.showAndWait();
            }
            else {
                Product newProduct = new Product(newId, name, price, stock, min, max);
                for(Part part : associatedList) {
                    newProduct.addAssociatedPart( part);
                }
                Inventory.addProduct(newProduct);
                newId++;
                ReturnHome(actionEvent);
            }
        }
    catch (Exception e) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText("Missing or incorrect info entered");
        alert.setTitle("Warning");
        Optional<ButtonType> result = alert.showAndWait();
    }

    }

    /**
     * Associates the current part to the current product.
     * @param actionEvent
     */
    public void addPartToAssocBttn(ActionEvent actionEvent) {
        Part selectedPart = (Part) addProductPartTable.getSelectionModel().getSelectedItem();
        if(selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Can't associate");
            alert.setContentText("Nothing selected");
            Optional<ButtonType> result = alert.showAndWait();
        }
        else {
            associatedList.add(selectedPart);
            addProductAssocPartsTable.setItems(associatedList);
        }

    }
}
