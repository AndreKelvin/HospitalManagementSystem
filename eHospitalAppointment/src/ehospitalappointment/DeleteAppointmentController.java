/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitalappointment;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSpinner;
import ehospitaldialog.EHospitalDialog;
import ehospitaldb.EHospitalDB;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Andre Kelvin
 */
public class DeleteAppointmentController {

    @FXML
    private VBox vBox;
    @FXML
    private JFXSpinner spinner;
    @FXML
    private JFXButton buttonYes;
    private PreparedStatement ps;
    private ResultSet rs;
    private ObservableList obList;
    private Appointment selectedAppointment;

    public void setSelectedAppointment(Appointment selectedAppointment) {
        this.selectedAppointment = selectedAppointment;
    }

    public void setObservableList(ObservableList obList) {
        this.obList = obList;
    }

    private class DeleteAppointmentTask extends Task<Object> {

        @Override
        protected Object call() throws Exception {

            try {
                ps = EHospitalDB.getCon().prepareStatement("Delete From APPOINTMENT Where Patient=?");
                ps.setString(1, selectedAppointment.getPatient());
                ps.executeUpdate();
                ps.close();

                ps = EHospitalDB.getCon().prepareStatement("Delete From NOTIFICATION Where Patient=? And Doctor=?");
                ps.setString(1, selectedAppointment.getPatient());
                ps.setString(2, selectedAppointment.getDoctor());
                ps.executeUpdate();

                obList.clear();

                ps = EHospitalDB.getCon().prepareStatement("Select * From APPOINTMENT");
                rs = ps.executeQuery();

                while (rs.next()) {
                    obList.add(new Appointment(rs.getString("Doctor"), rs.getString("Patient"), rs.getString("Date"), rs.getString("Start"), rs.getString("Ending"), rs.getString("Status")));
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

        DeleteAppointmentTask task = new DeleteAppointmentTask();
        task.setOnSucceeded((WorkerStateEvent event) -> {
            vBox.setDisable(false);
            spinner.setVisible(false);

            EHospitalDialog.dialog.close();
            EHospitalDialog.dialogAlert(Alert.AlertType.INFORMATION, "Delete Successful", buttonYes.getScene().getWindow());
        });
        new Thread(task).start();
    }

    @FXML
    private void noAction() {
        EHospitalDialog.dialog.close();
    }

}
