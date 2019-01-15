/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitalwardroomtabpane;

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
public class EditWardController implements Initializable {

    @FXML
    private VBox vBox;
    @FXML
    private JFXSpinner spinner;
    @FXML
    private JFXTextField textWardName, textBeds;
    private Ward selectedValue;
    private PreparedStatement ps;
    private ResultSet rs;
    private String ward, bed;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Force the text field to be numeric only
        textBeds.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                textBeds.setText(newValue.replaceAll("[^\\d]", ""));
                Toolkit.getDefaultToolkit().beep();
            }
        });
    }

    public void setSelectedValue(Ward selectedValue) throws SQLException {
        this.selectedValue = selectedValue;

        ps = EHospitalDB.getCon().prepareStatement("Select * From WARD Where Ward=?");
        ps.setString(1, selectedValue.getWard());
        rs = ps.executeQuery();

        while (rs.next()) {
            textWardName.setText(rs.getString("Ward"));
            textBeds.setText(rs.getString("Beds"));
        }
        ps.close();
    }

    private class EditTask extends Task<Object> {

        @Override
        protected Object call() throws Exception {

            try {
                ps = EHospitalDB.getCon().prepareStatement("Update WARD Set Ward=?,Beds=? Where Ward=?");
                ps.setString(1, ward);
                ps.setInt(2, Integer.parseInt(bed));
                ps.setString(3, selectedValue.getWard());
                ps.executeUpdate();
                ps.close();

                selectedValue.setWard(ward);
                selectedValue.setBed(Integer.parseInt(bed));
            } catch (Exception e) {
                vBox.setDisable(false);
                spinner.setVisible(false);

                Platform.runLater(() -> {
                    EHospitalDialog.dialogAlert(Alert.AlertType.ERROR, "Database connection failure", textBeds.getScene().getWindow());
                });
            }

            return null;
        }

    }

    @FXML
    private void saveAction() {
        ward = textWardName.getText().trim();
        bed = textBeds.getText().trim();

        if (ward.isEmpty() || bed.isEmpty()) {
            EHospitalDialog.dialogAlert(Alert.AlertType.ERROR, "Please Fill All Required Input", textBeds.getScene().getWindow());
        } else {
            vBox.setDisable(true);
            spinner.setVisible(true);

            EditTask task = new EditTask();
            task.setOnSucceeded((event) -> {
                vBox.setDisable(false);
                spinner.setVisible(false);

                EHospitalDialog.dialog.close();
                EHospitalDialog.dialogAlert(Alert.AlertType.INFORMATION, "Save Successful", textBeds.getScene().getWindow());
            });
            new Thread(task).start();
        }
    }

}
