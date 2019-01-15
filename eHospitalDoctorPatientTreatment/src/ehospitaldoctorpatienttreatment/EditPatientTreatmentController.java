/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitaldoctorpatienttreatment;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import ehospitaldialog.EHospitalDialog;
import ehospitaldb.EHospitalDB;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import org.controlsfx.control.CheckListView;

/**
 * FXML Controller class
 *
 * @author Andre Kelvin
 */
public class EditPatientTreatmentController implements Initializable {

    @FXML
    private JFXTextField textPatName, textDiagnosis;
    @FXML
    private VBox vBox;
    @FXML
    private JFXSpinner spinner;
    @FXML
    private JFXComboBox comboTreat;
    @FXML
    private CheckListView checkListView;
    @FXML
    private ListView listView;
    private PreparedStatement ps;
    private ResultSet rs;
    private byte loopListValue;
    private int value;
    private PatientTreatment selectedPatient;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            value = 0;

            populateTreatmentComboBox();

            populateCheckListView();

            //Display Selected/Checked Items on List View
            checkListView.getCheckModel().getCheckedItems().addListener((ListChangeListener.Change c) -> {
                listView.getItems().clear();
                while (value < c.getList().size()) {
                    listView.getItems().add(checkListView.getCheckModel().getCheckedItems().get(value));
                    value++;
                }
                value = 0;
            });

        } catch (Exception e) {
        }
    }

    public void populateTreatmentComboBox() throws SQLException {
        ps = EHospitalDB.getCon().prepareStatement("Select * From TREATMENT");
        rs = ps.executeQuery();

        comboTreat.getItems().clear();
        while (rs.next()) {
            comboTreat.getItems().add(rs.getString("TREATMENT_NAME"));
        }
        ps.close();
    }

    public void populateCheckListView() throws SQLException {
        ps = EHospitalDB.getCon().prepareStatement("Select NAME From DRUG");
        rs = ps.executeQuery();

        checkListView.getItems().clear();
        while (rs.next()) {
            checkListView.getItems().add(rs.getString("NAME"));
        }
    }

    public void setSelectedPatient(PatientTreatment selectedPatient) {
        try {
            this.selectedPatient = selectedPatient;

            textPatName.setText(selectedPatient.getPatientName());
            textDiagnosis.setText(selectedPatient.getDiagnosis());

            listView.getItems().clear();
            checkListView.getCheckModel().clearChecks();

            ps = EHospitalDB.getCon().prepareStatement("Select DRUGS From PATIENT_TREATMENT Where PATIENT_NAME=?");
            ps.setString(1, selectedPatient.getPatientName());
            rs = ps.executeQuery();

            while (rs.next()) {
                checkListView.getCheckModel().check(rs.getString("DRUGS"));
            }

        } catch (Exception e) {
        }
    }

    public void setPatientTreatmentName(String patientTreatmentName) {
        comboTreat.setValue(patientTreatmentName);
    }

    private class EditTask extends Task<Object> {

        @Override
        protected Object call() throws Exception {

            try {
                /*
                  Delete the Selected Values from Database
                  Before inputing new values
                  Because using the "Update" Statement in a Loop will always
                  Update the Database value with the Last Looped value
                 */
                ps = EHospitalDB.getCon().prepareStatement("Delete From PATIENT_TREATMENT Where PATIENT_NAME=?");
                ps.setString(1, selectedPatient.getPatientName());
                ps.executeUpdate();

                loopListValue = 0;
                while (listView.getItems().size() > loopListValue) {
                    ps = EHospitalDB.getCon().prepareStatement("Insert Into PATIENT_TREATMENT Values(?,?,?,?)");
                    ps.setString(1, textPatName.getText());
                    ps.setString(2, textDiagnosis.getText());
                    ps.setString(3, comboTreat.getValue().toString());
                    ps.setString(4, listView.getItems().get(loopListValue).toString());
                    ps.executeUpdate();

                    loopListValue++;
                }
            } catch (Exception e) {
                vBox.setDisable(false);
                spinner.setVisible(false);

                Platform.runLater(() -> {
                    EHospitalDialog.dialogAlert(Alert.AlertType.ERROR, "Database connection failure", textPatName.getScene().getWindow());
                });
            }

            return null;
        }

    }

    @FXML
    private void save() {
        if (comboTreat.getValue() == null || listView.getItems().isEmpty()) {
            EHospitalDialog.dialogAlert(Alert.AlertType.ERROR, "Please Fill All Required Input", textDiagnosis.getScene().getWindow());
        } else {
            vBox.setDisable(true);
            spinner.setVisible(true);

            EditTask task = new EditTask();
            task.setOnSucceeded((event) -> {
                vBox.setDisable(false);
                spinner.setVisible(false);

                EHospitalDialog.dialog.close();
                EHospitalDialog.dialogAlert(Alert.AlertType.INFORMATION, "Save Successful", textDiagnosis.getScene().getWindow());
            });
            new Thread(task).start();
        }
    }

}
