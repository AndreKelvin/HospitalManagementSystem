/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitalappointment;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTimePicker;
import ehospitaldialog.EHospitalDialog;
import ehospitaldb.EHospitalDB;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
public class EditAppointmentController implements Initializable {

    @FXML
    private VBox vBox;
    @FXML
    private JFXSpinner spinner;
    @FXML
    private JFXComboBox comboDoctor, comboPatient;
    @FXML
    private JFXDatePicker datePicker;
    @FXML
    private JFXTimePicker timePickerFrom, timePickerTo;
    @FXML
    private JFXCheckBox checkBoxPending, checkBoxDone;
    private PreparedStatement ps;
    private ResultSet rs;
    private Appointment selectedAppointment;
    private String doc, patient, date, from, to, checkDone, checkPending, format;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            //Manually Apply a Toggle Group to the Check Boxes
            checkBoxPending.selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                if (newValue) {
                    checkBoxDone.setSelected(false);
                }
            });

            checkBoxDone.selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                if (newValue) {
                    checkBoxPending.setSelected(false);
                }
            });

            timePickerFrom.setEditable(false);
            timePickerTo.setEditable(false);
        } catch (Exception e) {
        }
    }

    public void populateComboBoxs() throws SQLException {
        ps = EHospitalDB.getCon().prepareStatement("Select Name From DOCTOR");
        rs = ps.executeQuery();

        comboDoctor.getItems().clear();
        while (rs.next()) {
            comboDoctor.getItems().add(rs.getString("Name"));
        }

        ps = EHospitalDB.getCon().prepareStatement("Select Patient_Name From PATIENT");
        rs = ps.executeQuery();

        comboPatient.getItems().clear();
        while (rs.next()) {
            comboPatient.getItems().add(rs.getString("Patient_Name"));
        }
    }

    public void setSelectedAppointment(Appointment selectedAppointment) throws SQLException {
        this.selectedAppointment = selectedAppointment;

        ps = EHospitalDB.getCon().prepareStatement("Select * From APPOINTMENT Where Patient=?");
        ps.setString(1, selectedAppointment.getPatient());
        rs = ps.executeQuery();

        while (rs.next()) {
            comboDoctor.setValue(rs.getString("Doctor"));
            comboPatient.setValue(rs.getString("Patient"));
            datePicker.setValue(LocalDate.parse(rs.getString("Date"), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            timePickerFrom.setValue(LocalTime.parse(rs.getString("Start"), DateTimeFormatter.ofPattern("hh:mm a")));
            timePickerTo.setValue(LocalTime.parse(rs.getString("Ending"), DateTimeFormatter.ofPattern("hh:mm a")));

            if (rs.getString("Status").contentEquals("Pending")) {
                checkBoxPending.setSelected(true);
            } else {
                checkBoxDone.setSelected(true);
            }
        }
    }

    private class EditAppointmentTask extends Task<Object> {

        @Override
        protected Object call() throws Exception {

            try {
                doc = comboDoctor.getValue().toString();
                patient = comboPatient.getValue().toString();
                date = datePicker.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                from = timePickerFrom.getValue().format(DateTimeFormatter.ofPattern("hh:mm a"));
                format = timePickerFrom.getValue().format(DateTimeFormatter.ofPattern("hh:mm:ss a"));
                to = timePickerTo.getValue().format(DateTimeFormatter.ofPattern("hh:mm a"));

                ps = EHospitalDB.getCon().prepareStatement("Update APPOINTMENT Set Doctor=?,Patient=?,Date=?,Start=?,Ending=?,Status=? Where Patient=?");
                ps.setString(1, doc);
                ps.setString(2, patient);
                ps.setString(3, date);
                ps.setString(4, from);
                ps.setString(5, to);

                if (checkBoxPending.isSelected()) {
                    checkPending = checkBoxPending.getText();
                    ps.setString(6, checkPending);
                    selectedAppointment.setStatus(checkPending);
                } else {
                    checkDone = checkBoxDone.getText();
                    ps.setString(6, checkDone);
                    selectedAppointment.setStatus(checkDone);
                }
                ps.setString(7, selectedAppointment.getPatient());
                ps.executeUpdate();
                ps.close();

                ps = EHospitalDB.getCon().prepareStatement("Update NOTIFICATION Set Reminder=?,Doctor=?,Patient=? Where Patient=? And Doctor=?");
                ps.setString(1, date + " " + format);
                ps.setString(2, doc);
                ps.setString(3, patient);
                ps.setString(4, selectedAppointment.getPatient());
                ps.setString(5, selectedAppointment.getDoctor());
                ps.executeUpdate();

                selectedAppointment.setDoctor(doc);
                selectedAppointment.setPatient(patient);
                selectedAppointment.setDate(date);
                selectedAppointment.setFrom(from);
                selectedAppointment.setTo(to);
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
    private void save() {
        if (comboDoctor.getValue() == null || comboPatient.getValue() == null || datePicker.getValue() == null || timePickerFrom.getValue() == null || timePickerTo.getValue() == null) {
            EHospitalDialog.dialogAlert(Alert.AlertType.ERROR, "Please Fill All Required Input", comboDoctor.getScene().getWindow());
        } else {
            vBox.setDisable(true);
            spinner.setVisible(true);

            EditAppointmentTask task = new EditAppointmentTask();
            task.setOnSucceeded((WorkerStateEvent event) -> {
                vBox.setDisable(false);
                spinner.setVisible(false);
                
                EHospitalDialog.dialog.close();
                EHospitalDialog.dialogAlert(Alert.AlertType.INFORMATION, "Save Successfull", comboDoctor.getScene().getWindow());

                comboDoctor.setValue(null);
                comboPatient.setValue(null);
                datePicker.setValue(null);
                timePickerFrom.setValue(null);
                timePickerTo.setValue(null);
                checkBoxDone.setSelected(false);
                checkBoxPending.setSelected(false);
            });
            new Thread(task).start();
        }
    }

}
