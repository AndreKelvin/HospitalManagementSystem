/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitallogin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import ehospitaldb.EHospitalDB;
import ehospitaldialog.EHospitalDialog;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Andre Kelvin
 */
public class CreateAccountController implements Initializable {

    @FXML
    private VBox vBox;
    @FXML
    private JFXComboBox comboBox;
    @FXML
    private JFXSpinner spinner;
    @FXML
    private JFXTextField textName, textUsername, textUnMaskPassword, textUnMaskComfirmPass;
    @FXML
    private JFXPasswordField textPass, textComPass;
    @FXML
    private JFXCheckBox checkBox;
    @FXML
    private JFXButton buttonSave;
    private PreparedStatement ps;
    private ResultSet rs;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // Bind properties. textField and passwordField
            // visibility and managability properties mutually when checkbox's state is changed.
            // Because we want to display only one component (textField or passwordField)
            // on the scene at a time.
            textUnMaskPassword.managedProperty().bind(checkBox.selectedProperty());
            textUnMaskPassword.visibleProperty().bind(checkBox.selectedProperty());
            
            textUnMaskComfirmPass.managedProperty().bind(checkBox.selectedProperty());
            textUnMaskComfirmPass.visibleProperty().bind(checkBox.selectedProperty());

            textPass.managedProperty().bind(checkBox.selectedProperty().not());
            textPass.visibleProperty().bind(checkBox.selectedProperty().not());
            
            textComPass.managedProperty().bind(checkBox.selectedProperty().not());
            textComPass.visibleProperty().bind(checkBox.selectedProperty().not());

            buttonSave.setDisable(true);

            comboBox.getSelectionModel().selectedItemProperty().addListener((ObservableValue observable, Object oldValue, Object newValue) -> {
                buttonSave.setDisable(false);

                if (newValue.equals("Admin")) {
                    textName.setPromptText("Admin Name");
                } else if (newValue.equals("Doctor")) {
                    textName.setPromptText("Doctor Name");
                } else if (newValue.equals("Nurse")) {
                    textName.setPromptText("Nurse Name");
                } else if (newValue.equals("Pharmacist")) {
                    textName.setPromptText("Pharmacist Name");
                } else if (newValue.equals("Lab Technician")) {
                    textName.setPromptText("Lab Technician Name");
                }
            });
        } catch (Exception e) {
        }
    }

    /**
     * This make sure only one Administrator account is created. checks if
     * Administrator exist in database if true remove "Admin" from combo box
     * else don't
     *
     * @throws SQLException
     */
    public void populateComboBox() throws SQLException {
        ps = EHospitalDB.getCon().prepareStatement("Select Login_As From HOSPITALLOGIN Where Login_As=?");
        ps.setString(1, "Admin");
        rs = ps.executeQuery();

        if (rs.next()) {
            comboBox.getItems().setAll("Doctor", "Nurse", "Pharmacist", "Lab Technician");
        } else {
            comboBox.getItems().setAll("Admin", "Doctor", "Nurse", "Pharmacist", "Lab Technician");
        }
    }

    private class SavePasswordTask extends Task<Object> {

        @Override
        protected Object call() throws Exception {

            try {
                ps = EHospitalDB.getCon().prepareStatement("Insert Into HOSPITALLOGIN Values(?,?,?,?)");
                ps.setString(1, textUsername.getText().trim());
                ps.setString(2, textPass.getText().trim());
                ps.setString(3, comboBox.getValue().toString());
                ps.setString(4, textName.getText().trim());
                ps.executeUpdate();

                EHospitalDialog.dialog.close();
            } catch (Exception e) {
                vBox.setDisable(false);
                spinner.setVisible(false);
                Platform.runLater(() -> {
                    EHospitalDialog.dialogAlert(Alert.AlertType.ERROR, "Database connection failure", textComPass.getScene().getWindow());
                });
            }
            return null;
        }

    }

    @FXML
    private void savePassword() {
        if (textName.getText().isEmpty() || textUsername.getText().isEmpty() || textPass.getText().isEmpty() || textComPass.getText().isEmpty() || !textComPass.getText().contentEquals(textPass.getText())) {
            EHospitalDialog.dialogAlert(Alert.AlertType.ERROR, "invalid input", textComPass.getScene().getWindow());
        } else {
            vBox.setDisable(true);
            spinner.setVisible(true);

            SavePasswordTask task = new SavePasswordTask();
            task.setOnSucceeded((WorkerStateEvent event) -> {
                vBox.setDisable(false);
                spinner.setVisible(false);

                EHospitalDialog.dialogAlert(Alert.AlertType.INFORMATION, "Save Successful", textComPass.getScene().getWindow());
                textComPass.clear();
                textName.clear();
                textPass.clear();
                textUnMaskComfirmPass.clear();
                textUnMaskPassword.clear();
                textUsername.clear();
                comboBox.setValue("");
                textName.setPromptText("Name");
                buttonSave.setDisable(true);
                checkBox.setSelected(false);
            });
            new Thread(task).start();
        }
    }

    @FXML
    private void showOrHidePassword() {
        // Bind the textField and passwordField text values bidirectionally.
        textUnMaskPassword.textProperty().bindBidirectional(textPass.textProperty());
        textUnMaskComfirmPass.textProperty().bindBidirectional(textComPass.textProperty());
    }

}
