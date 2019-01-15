/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitalbloodbank;

import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import ehospitaldialog.EHospitalDialog;
import ehospitaldb.EHospitalDB;
import java.awt.Toolkit;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
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
public class EditBloodGroupController implements Initializable {

    @FXML
    private VBox vBox;
    @FXML
    private JFXSpinner spinner;
    @FXML
    private JFXTextField textBlood, textBags;
    private PreparedStatement ps;
    private ResultSet rs;
    private BloodGroup selectedValue;
    private String blood, bags;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Force the text field to be numeric only
        textBags.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                textBags.setText(newValue.replaceAll("[^\\d]", ""));
                Toolkit.getDefaultToolkit().beep();
            }
        });
    }

    public void setSelectedValue(BloodGroup selectedValue) throws SQLException {
        this.selectedValue = selectedValue;

        ps = EHospitalDB.getCon().prepareStatement("Select * From BLOOD_GROUP Where NAME=?");
        ps.setString(1, selectedValue.getBlood());
        rs = ps.executeQuery();

        while (rs.next()) {
            textBlood.setText(rs.getString("NAME"));
            textBags.setText(rs.getString("BAGS"));
        }
        ps.close();
    }

    private class SaveEdit extends Task<Object> {

        @Override
        protected Object call() throws Exception {

            try {
                ps = EHospitalDB.getCon().prepareStatement("Update Blood_Group Set Name=?,Bags=? Where Name=?");
                ps.setString(1, blood);
                ps.setInt(2, Integer.parseInt(bags));
                ps.setString(3, selectedValue.getBlood());
                ps.executeUpdate();
                ps.close();

                selectedValue.setBlood(blood);
                selectedValue.setBags(Integer.parseInt(bags));
            } catch (Exception e) {
                vBox.setDisable(false);
                spinner.setVisible(false);

                Platform.runLater(() -> {
                    EHospitalDialog.dialogAlert(Alert.AlertType.ERROR, "Database connection failure", textBags.getScene().getWindow());
                });
            }

            return null;
        }

    }

    @FXML
    private void saveAction() {
        blood = textBlood.getText().trim();
        bags = textBags.getText().trim();

        if (blood.isEmpty() || bags.isEmpty()) {
            EHospitalDialog.dialogAlert(Alert.AlertType.ERROR, "Please Fill All Required Input", textBags.getScene().getWindow());
        } else {
            vBox.setDisable(true);
            spinner.setVisible(true);

            SaveEdit task = new SaveEdit();
            task.setOnSucceeded((event) -> {
                vBox.setDisable(false);
                spinner.setVisible(false);

                EHospitalDialog.dialog.close();
                EHospitalDialog.dialogAlert(Alert.AlertType.INFORMATION, "Save Successful", textBags.getScene().getWindow());
            });
            new Thread(task).start();
        }
    }
}
