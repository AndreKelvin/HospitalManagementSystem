/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitaldrugs;

import com.jfoenix.controls.JFXSpinner;
import ehospitaldialog.EHospitalDialog;
import ehospitaldb.EHospitalDB;
import java.io.File;
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
public class DeleteDrugController {

    @FXML
    private JFXSpinner spinner;
    @FXML
    private VBox vBox;
    @FXML
    private Label label;
    private Drug selectedDrug;
    private File drugImageFile;
    private PreparedStatement ps;

    public void setSelectedDrug(Drug selectedDrug) {
        this.selectedDrug = selectedDrug;
        label.setText("Are your sure you want to Delete " + selectedDrug.getName() + "?");
    }

    public void setDrugImageFile(File drugImageFile) {
        this.drugImageFile = drugImageFile;
    }

    private class DeleteDrugTask extends Task<Object> {

        @Override
        protected Object call() throws Exception {

            try {
                ps = EHospitalDB.getCon().prepareStatement("Delete From DRUG Where NAME=?");
                ps.setString(1, selectedDrug.getName());
                ps.executeUpdate();

                //Delete the Selected Drug Image
                drugImageFile.delete();

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

        DeleteDrugTask task = new DeleteDrugTask();
        task.setOnSucceeded((event) -> {
            vBox.setDisable(false);
            spinner.setVisible(false);

            EHospitalDialog.dialog.close();
            EHospitalDialog.dialogAlert(Alert.AlertType.INFORMATION, "Delete Successful", label.getScene().getWindow());
        });
        new Thread(task).start();
    }

    @FXML
    private void noAction() {
        EHospitalDialog.dialog.close();
    }

}
