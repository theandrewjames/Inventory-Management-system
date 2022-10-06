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

public class AddPartController implements Initializable {
    public static int newId = 3;

    public RadioButton partIHradio;
    public RadioButton partOSradio;
    public TextField addPartMachineIDTF;
    public Button addPartSaveB;
    public TextField addPartNameTF;
    public TextField addPartInvTF;
    public TextField addPartPriceTF;
    public TextField addPartMaxTF;
    public TextField addPartMinTF;
    public Label addPartMachineId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * Returns/loads to main screen
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
     * when Inhouse radio button is clicked the "Machine ID/company name" label text is changed.
     * @param actionEvent
     */
    public void onPartIH(ActionEvent actionEvent) {
        addPartMachineId.setText("Machine ID");
    }

    /**
     * when Outsourced radio button is clicked the "Machine ID/company name" label text is changed.
     * @param actionEvent
     */
    public void OnPartOS(ActionEvent actionEvent) {
    addPartMachineId.setText("Company Name");
    }

    /**
     * clickingSave button adds the part to inventory
     * @param actionEvent
     * @throws IOException
     */
    public void addPartSave(ActionEvent actionEvent) throws IOException {
        try {
            String name = addPartNameTF.getText();
            Integer stock = Integer.parseInt(addPartInvTF.getText());
            Double price = Double.parseDouble(addPartPriceTF.getText());
            Integer max = Integer.parseInt(addPartMaxTF.getText());
            Integer min = Integer.parseInt(addPartMinTF.getText());
            if(min > max || stock < min || stock > max) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setContentText("Min needs to be less than stock which is less than max");
                Optional<ButtonType> result = alert.showAndWait();
            } else if (partIHradio.isSelected()) {
                int machineId = Integer.parseInt(addPartMachineIDTF.getText());
                InHouse addPart = new InHouse(newId, name, price, stock, min, max, machineId);
                Inventory.addPart(addPart);
                newId++;
                ReturnHome(actionEvent);
            }
            else {
                String companyName = addPartMachineIDTF.getText();
                OutSourced addPart = new OutSourced(newId, name, price, stock, min, max, companyName);
                Inventory.addPart(addPart);
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
}
