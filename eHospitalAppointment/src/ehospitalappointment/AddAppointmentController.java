/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitalappointment;

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
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.ObservableList;
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
public class AddAppointmentController implements Initializable {

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
    private PreparedStatement ps;
    private ResultSet rs;
    private ObservableList obList;
    private String doc, patient, date, from, to, format;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
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

    public void setObervableList(ObservableList obList) {
        this.obList = obList;
    }

    private class SaveAppointmentTask extends Task<Object> {

        @Override
        protected Object call() throws Exception {

            try {
                doc = comboDoctor.getValue().toString();
                patient = comboPatient.getValue().toString();
                date = datePicker.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                from = timePickerFrom.getValue().format(DateTimeFormatter.ofPattern("hh:mm a"));
                format = timePickerFrom.getValue().format(DateTimeFormatter.ofPattern("hh:mm:ss a"));
                to = timePickerTo.getValue().format(DateTimeFormatter.ofPattern("hh:mm a"));

                ps = EHospitalDB.getCon().prepareStatement("Insert Into APPOINTMENT Values(?,?,?,?,?,?)");
                ps.setString(1, doc);
                ps.setString(2, patient);
                ps.setString(3, date);
                ps.setString(4, from);
                ps.setString(5, to);
                ps.setString(6, "Pending");
                ps.executeUpdate();
                ps.close();

                obList.add(new Appointment(doc, patient, date, from, to, "Pending"));

                ps = EHospitalDB.getCon().prepareStatement("Insert Into NOTIFICATION Values(?,?,?)");
                ps.setString(1, date + " " + format);
                ps.setString(2, doc);
                ps.setString(3, patient);
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
        if (comboDoctor.getValue() == null || comboPatient.getValue() == null || datePicker.getValue() == null || timePickerFrom.getValue() == null || timePickerTo.getValue() == null) {
            EHospitalDialog.dialogAlert(Alert.AlertType.ERROR, "Please Fill All Required Input", comboDoctor.getScene().getWindow());
        } else {
            vBox.setDisable(true);
            spinner.setVisible(true);

            SaveAppointmentTask task = new SaveAppointmentTask();
            task.setOnSucceeded((WorkerStateEvent event) -> {
                vBox.setDisable(false);
                spinner.setVisible(false);

                EHospitalDialog.dialogAlert(Alert.AlertType.INFORMATION, "Save Successfull", comboDoctor.getScene().getWindow());

                comboDoctor.setValue(null);
                comboPatient.setValue(null);
                datePicker.setValue(null);
                timePickerFrom.setValue(null);
                timePickerTo.setValue(null);
            });
            new Thread(task).start();
        }
    }
}
