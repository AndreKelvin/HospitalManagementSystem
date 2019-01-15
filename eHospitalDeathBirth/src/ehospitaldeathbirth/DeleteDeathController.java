/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitaldeathbirth;

import com.jfoenix.controls.JFXSpinner;
import ehospitaldb.EHospitalDB;
import ehospitaldialog.EHospitalDialog;
import java.sql.PreparedStatement;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Andre Kelvin
 */
public class DeleteDeathController {

    @FXML
    private VBox vBox;
    @FXML
    private JFXSpinner spinner;
    @FXML
    private Label label;
    private PreparedStatement ps;
    private String selectedDeadPatient;

    public void getSelectedDeadPatient(String selectedDeadPatient) {
        this.selectedDeadPatient = selectedDeadPatient;
        label.setText("Are you sure you want to Delete " + selectedDeadPatient + "?");
    }

    private class DeleteTask extends Task<Object> {

        @Override
        protected Object call() throws Exception {

            try {
                ps = EHospitalDB.getCon().prepareStatement("Delete From DEATH Where Name=?");
                ps.setString(1, selectedDeadPatient);
                ps.executeUpdate();
            } catch (Exception e) {
                vBox.setDisable(false);
                spinner.setVisible(false);

                Platform.runLater(() -> {
                    EHospitalDialog.dialogAlert(Alert.AlertType.ERROR, "Database connection failure", label.getScene().getWindow());
                });
            }

            return null;
        }

    }

    @FXML
    private void yesAction() {
        vBox.setDisable(true);
        spinner.setVisible(true);

        DeleteTask task = new DeleteTask();
        task.setOnSucceeded((event) -> {
            vBox.setDisable(false);
            spinner.setVisible(false);

            EHospitalDialog.dialog.close();
            EHospitalDialog.dialogAlert(Alert.AlertType.INFORMATION, "Delete Successfull", label.getScene().getWindow());
        });
        new Thread(task).start();
    }

    @FXML
    private void noAction() {
        EHospitalDialog.dialog.close();
    }

}
