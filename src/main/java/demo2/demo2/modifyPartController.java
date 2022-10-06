package demo2.demo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class modifyPartController implements Initializable {

    public RadioButton modifyIHRadio;
    public RadioButton modifyOSRadio;
    public ToggleGroup modifyPartRadio;
    public Label modifyMachineIdLabel;
    public TextField modPartIDTF;
    public TextField modPartNameTF;
    public TextField modPartStockTF;
    public TextField modPartPriceTF;
    public TextField modPartMaxTF;
    public TextField modPartMinTF;
    public TextField modPartMIDTF;
    private int selectedID;

    /**
     * Initializes page.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
     * Loads selected part
     * @param selectedPart
     */
    public void loadPart(Part selectedPart) {
         selectedID = Inventory.getAllParts().indexOf(selectedPart);
        modPartIDTF.setText(String.valueOf(selectedPart.getId()));
        modPartNameTF.setText(selectedPart.getName());
        modPartStockTF.setText(String.valueOf(selectedPart.getStock()));
        modPartPriceTF.setText(String.valueOf(selectedPart.getPrice()));
        modPartMinTF.setText(String.valueOf(selectedPart.getMin()));
        modPartMaxTF.setText(String.valueOf(selectedPart.getMax()));
        if (selectedPart instanceof InHouse ) {
            modifyIHRadio.setSelected(true);
            modifyMachineIdLabel.setText("Machine ID");
            modPartMIDTF.setText(String.valueOf(((InHouse) selectedPart).getMachineId()));
        }
        else if(selectedPart instanceof OutSourced) {
            modifyOSRadio.setSelected(true);
            modifyMachineIdLabel.setText("Company Name");
            modPartMIDTF.setText(String.valueOf(((OutSourced) selectedPart).getCompanyName()));
        }
    }

    /**
     * upon clicking InHouse radio button the "machine id/company name" label text is changed
     * @param actionEvent
     */
    public void onModifyIH(ActionEvent actionEvent) {
        modifyMachineIdLabel.setText("Machine ID");
    }

    /**
     * upon clicking outsorced radio button the "machine id/company name" label text is changed
     * @param actionEvent
     */
    public void onModifyOS(ActionEvent actionEvent) {
        modifyMachineIdLabel.setText("Company Name");
    }

    /**
     * upon clicking save button the current part is saved.
     * @param actionEvent
     * @throws IOException
     */
    public void onSave(ActionEvent actionEvent) throws IOException {
        try{
            int id = Integer.parseInt(modPartIDTF.getText());
            String name = modPartNameTF.getText();
            int stock = Integer.parseInt(modPartStockTF.getText());
            double price = Double.parseDouble(modPartPriceTF.getText());
            int min = Integer.parseInt(modPartMinTF.getText());
            int max = Integer.parseInt(modPartMaxTF.getText());
            if(min > max || stock < min || stock > max) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setContentText("Min needs to be less than stock which is less than max");
                Optional<ButtonType> result = alert.showAndWait();
            } else if (modifyIHRadio.isSelected()) {
                int machineID = Integer.parseInt(modPartMIDTF.getText());
                InHouse modifiedPart = new InHouse(id, name, price, stock, min, max, machineID);
                Inventory.updatePart(selectedID, modifiedPart);
                Parent root = FXMLLoader.load(getClass().getResource("/demo2/demo2/mainScreen.fxml"));
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }

            else if (modifyOSRadio.isSelected()) {
                String companyName = modPartMIDTF.getText();
                OutSourced modifiedPart = new OutSourced(id, name, price, stock, min, max, companyName);
                Inventory.updatePart(selectedID, modifiedPart);
                Parent root = FXMLLoader.load(getClass().getResource("/demo2/demo2/mainScreen.fxml"));
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }
        }
        catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Incorrect or missing info");
            alert.setTitle("Warning");
            Optional<ButtonType> result = alert.showAndWait();
        }
    }

}
