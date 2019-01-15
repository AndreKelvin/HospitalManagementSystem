/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitaldoctorappointment;

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
    private JFXComboBox comboPatient;
    @FXML
    private JFXDatePicker datePicker;
    @FXML
    private JFXTimePicker timePickerFrom, timePickerTo;
    @FXML
    private JFXCheckBox checkBoxPending, checkBoxDone;
    @FXML
    private VBox vBox;
    @FXML
    private JFXSpinner spinner;
    private PreparedStatement ps;
    private ResultSet rs;
    private Appointment selectedAppointment;
    private String patient, date, from, to, checkDone, checkPending, format,doctorName;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            populateComboBoxs();

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
        } catch (Exception e) {e.printStackTrace();
        }
    }
    
    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public void populateComboBoxs() throws SQLException {
        ps = EHospitalDB.getCon().prepareStatement("Select PATIENT_NAME From PATIENT Where DOCTOR=?");
        ps.setString(1, doctorName);
        rs = ps.executeQuery();

        comboPatient.getItems().clear();
        while (rs.next()) {
            System.out.println("Combo Box Patient Worked");
            comboPatient.getItems().add(rs.getString("PATIENT_NAME"));
        }
    }

    public void setSelectedAppointment(Appointment selectedAppointment) throws SQLException {
        this.selectedAppointment = selectedAppointment;

        ps = EHospitalDB.getCon().prepareStatement("Select * From APPOINTMENT Where PATIENT=?");
        ps.setString(1, selectedAppointment.getPatient());
        rs = ps.executeQuery();

        if (rs.next()) {
            comboPatient.setValue(rs.getString("PATIENT"));
            datePicker.setValue(LocalDate.parse(rs.getString("DATE"), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            timePickerFrom.setValue(LocalTime.parse(rs.getString("START"), DateTimeFormatter.ofPattern("hh:mm a")));
            timePickerTo.setValue(LocalTime.parse(rs.getString("ENDING"), DateTimeFormatter.ofPattern("hh:mm a")));

            if (rs.getString("STATUS").contentEquals("Pending")) {
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
                patient = comboPatient.getValue().toString();
                date = datePicker.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                from = timePickerFrom.getValue().format(DateTimeFormatter.ofPattern("hh:mm a"));
                format = timePickerFrom.getValue().format(DateTimeFormatter.ofPattern("hh:mm:ss a"));
                to = timePickerTo.getValue().format(DateTimeFormatter.ofPattern("hh:mm a"));

                ps = EHospitalDB.getCon().prepareStatement("Update APPOINTMENT Set DOCTOR=?,PATIENT=?,DATE=?,START=?,ENDING=?,STATUS=? Where PATIENT=? and"
                        + " DATE=? and START=?");
                ps.setString(1, doctorName);
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
                ps.setString(8, selectedAppointment.getDate());
                ps.setString(9, selectedAppointment.getFrom());
                ps.executeUpdate();
                ps.close();

                ps = EHospitalDB.getCon().prepareStatement("Update NOTIFICATION Set REMINDER=?,DOCTOR=?,PATIENT=? Where PATIENT=? And DOCTOR=?");
                ps.setString(1, date + " " + format);
                ps.setString(2, doctorName);
                ps.setString(3, patient);
                ps.setString(4, selectedAppointment.getPatient());
                ps.setString(5, selectedAppointment.getDoctor());
                ps.executeUpdate();
            } catch (Exception e) {
                vBox.setDisable(false);
                spinner.setVisible(false);
                
                Platform.runLater(() -> {
                    EHospitalDialog.dialogAlert(Alert.AlertType.ERROR, "Database connection failure", comboPatient.getScene().getWindow());
                });
            }

            return null;
        }
    }

    @FXML
    private void save() {
        try {
            if (comboPatient.getValue() == null || datePicker.getValue() == null || timePickerFrom.getValue() == null || timePickerTo.getValue() == null) {
                EHospitalDialog.dialogAlert(Alert.AlertType.ERROR, "Please Fill All Required Input", comboPatient.getScene().getWindow());
            } else {
                vBox.setDisable(true);
                spinner.setVisible(true);

                EditAppointmentTask task = new EditAppointmentTask();
                task.setOnSucceeded((event) -> {
                    vBox.setDisable(false);
                    spinner.setVisible(false);

                    selectedAppointment.setDoctor(doctorName);
                    selectedAppointment.setPatient(patient);
                    selectedAppointment.setDate(date);
                    selectedAppointment.setFrom(from);
                    selectedAppointment.setTo(to);

                    EHospitalDialog.dialog.close();
                    EHospitalDialog.dialogAlert(Alert.AlertType.INFORMATION, "Save Successfull", comboPatient.getScene().getWindow());

                    comboPatient.setValue(null);
                    datePicker.setValue(null);
                    timePickerFrom.setValue(null);
                    timePickerTo.setValue(null);
                    checkBoxDone.setSelected(false);
                    checkBoxPending.setSelected(false);
                });
                new Thread(task).start();
            }
        } catch (Exception e) {
        }
    }

}
