/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitalprofile;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import ehospitalbridge.EHospitalBridge;
import ehospitaldb.EHospitalDB;
import ehospitaldialog.EHospitalDialog;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Andre Kelvin
 */
public class EditAccount implements Initializable {

    @FXML
    private VBox vBox;
    @FXML
    private JFXSpinner spinner;
    @FXML
    private JFXTextField textUsername, textPassUnMask, textNewPassUnMask, textConfirmNewPassUnMask;
    @FXML
    private JFXPasswordField password, newPassword, confrimNewPassword;
    @FXML
    private JFXCheckBox checkBox;
    private PreparedStatement ps;
    private ResultSet rs;
    private String dbPassword, username, pass, newPass, confirmPassword;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Bind properties. textField and passwordField
        // visibility and managability properties mutually when checkbox's state is changed.
        // Because i want to display only one component (textField or passwordField)
        // on the scene at a time.
        textPassUnMask.managedProperty().bind(checkBox.selectedProperty());
        textPassUnMask.visibleProperty().bind(checkBox.selectedProperty());

        textNewPassUnMask.managedProperty().bind(checkBox.selectedProperty());
        textNewPassUnMask.visibleProperty().bind(checkBox.selectedProperty());

        textConfirmNewPassUnMask.managedProperty().bind(checkBox.selectedProperty());
        textConfirmNewPassUnMask.visibleProperty().bind(checkBox.selectedProperty());

        password.managedProperty().bind(checkBox.selectedProperty().not());
        password.visibleProperty().bind(checkBox.selectedProperty().not());

        newPassword.managedProperty().bind(checkBox.selectedProperty().not());
        newPassword.visibleProperty().bind(checkBox.selectedProperty().not());

        confrimNewPassword.managedProperty().bind(checkBox.selectedProperty().not());
        confrimNewPassword.visibleProperty().bind(checkBox.selectedProperty().not());
    }

    public void getUsernameAndPassword() {
        try {
            ps = EHospitalDB.getCon().prepareStatement("Select USERNAME,PASSWORD From HOSPITALLOGIN Where NAME=?");
            ps.setString(1, EHospitalBridge.loginUser);
            rs = ps.executeQuery();

            if (rs.next()) {
                dbPassword = rs.getString("PASSWORD");
                textUsername.setText(rs.getString("USERNAME"));
            }
        } catch (Exception e) {
        }
    }

    @FXML
    private void showOrHidePassword() {
        // Bind the textField and passwordField text values bidirectionally.
        textPassUnMask.textProperty().bindBidirectional(password.textProperty());
        textNewPassUnMask.textProperty().bindBidirectional(newPassword.textProperty());
        textConfirmNewPassUnMask.textProperty().bindBidirectional(confrimNewPassword.textProperty());
    }

    private class EditTask extends Task<Object> {

        @Override
        protected Object call() throws Exception {

            try {
                ps = EHospitalDB.getCon().prepareStatement("Update HOSPITALLOGIN Set USERNAME=?, PASSWORD=? Where NAME=?");
                ps.setString(1, username);
                ps.setString(2, confirmPassword);
                ps.setString(3, EHospitalBridge.loginUser);
                ps.executeUpdate();
            } catch (Exception e) {
                vBox.setDisable(false);
                spinner.setVisible(false);
                
                Platform.runLater(() -> {
                    EHospitalDialog.dialogAlert(Alert.AlertType.ERROR, "Database connection failure", textUsername.getScene().getWindow());
                });
            }

            return null;
        }

    }

    @FXML
    private void editAccount() {
        username = textUsername.getText().trim();
        pass = password.getText().trim();
        newPass = newPassword.getText().trim();
        confirmPassword = confrimNewPassword.getText().trim();

        if (username.isEmpty() || confirmPassword.isEmpty() || pass.isEmpty() || newPass.isEmpty() || !pass.contentEquals(dbPassword) || !confirmPassword.contentEquals(newPass)) {
            EHospitalDialog.dialogAlert(Alert.AlertType.ERROR, "invalid input", textUsername.getScene().getWindow());
        } else {
            vBox.setDisable(true);
            spinner.setVisible(true);

            EditTask task = new EditTask();
            task.setOnSucceeded((event) -> {
                vBox.setDisable(false);
                spinner.setVisible(false);

                EHospitalDialog.dialogAlert(Alert.AlertType.INFORMATION, "Save Successful", textUsername.getScene().getWindow());
                checkBox.setSelected(false);
                textConfirmNewPassUnMask.clear();
                textNewPassUnMask.clear();
                textPassUnMask.clear();
                textUsername.clear();
                password.clear();
                newPassword.clear();
                confrimNewPassword.clear();
            });
            new Thread(task).start();
        }
    }
    
}
