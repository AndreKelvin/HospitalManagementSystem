/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitaltreatment;

import com.jfoenix.controls.JFXSpinner;
import ehospitaldialog.EHospitalDialog;
import ehospitaldb.EHospitalDB;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.application.Platform;
import javafx.collections.ObservableList;
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
public class DeleteTreatmentController {

    @FXML
    private VBox vBox;
    @FXML
    private JFXSpinner spinner;
    @FXML
    private Label label;
    private PreparedStatement ps;
    private ResultSet rs;
    private Treatment selectedTreatment;
    private ObservableList obList;

    public void setselectedTreatment(Treatment selectedTreatment) throws SQLException {
        this.selectedTreatment = selectedTreatment;
        label.setText("Are you sure you want to Delete " + selectedTreatment.getTreatment() + "?");
    }

    public void setObList(ObservableList obList) {
        this.obList = obList;
    }

    private class DeleteTask extends Task<Object> {

        @Override
        protected Object call() throws Exception {

            try {
                ps = EHospitalDB.getCon().prepareStatement("Delete From TREATMENT Where Treatment_Name=?");
                ps.setString(1, selectedTreatment.getTreatment());
                ps.executeUpdate();

                //refresh table
                obList.clear();
                ps = EHospitalDB.getCon().prepareStatement("Select * From TREATMENT");
                rs = ps.executeQuery();

                while (rs.next()) {
                    obList.add(new Treatment(rs.getString("Treatment_Name"), rs.getInt("Price")));
                }
            } catch (Exception e) {
                vBox.setDisable(false);
                spinner.setVisible(false);

                Platform.runLater(() -> {
                    EHospitalDialog.dialogAlert(Alert.AlertType.ERROR, "Database connection failure", vBox.getScene().getWindow());
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
            EHospitalDialog.dialogAlert(Alert.AlertType.INFORMATION, "Delete Successful", label.getScene().getWindow());
        });
        new Thread(task).start();
    }

    @FXML
    private void noAction() {
        EHospitalDialog.dialog.close();
    }

}
