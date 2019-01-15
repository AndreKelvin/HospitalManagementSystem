/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitalpatient;

import com.jfoenix.controls.JFXSpinner;
import ehospitalbridge.EHospitalBridge;
import ehospitaldb.EHospitalDB;
import ehospitaldialog.EHospitalDialog;
import java.sql.PreparedStatement;
import java.util.List;
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
public class DeletePatientController {

    @FXML
    private VBox vBox;
    @FXML
    private JFXSpinner spinner;
    @FXML
    private Label label;
    private List takenBedList, takenRoomList;
    private PreparedStatement ps;
    private String selectedPatientName;
    private int selectedPatientID;
    private Label labelCity, labelAddress, labelMatStatus, labelGender, labelAge, labelDiagnosis, labelRoom, labelFloor, labelBed, labelBloodG, labelDoctor;
    private Label labelID, labelName, labelAdmit, labelWard, labelPhone;
    private String bedValue, roomValue;

    public void setTakenBedandRoomList(List takenBedList, List takenRoomList) {
        this.takenBedList = takenBedList;
        this.takenRoomList = takenRoomList;
    }

    public void setSelectedPatientName(String selectedPatientName) {
        this.selectedPatientName = selectedPatientName;
        label.setText("Are your sure you want to Delete " + selectedPatientName + "?");
    }

    public void setPatientID(int selectedPatientID) {
        this.selectedPatientID = selectedPatientID;
    }

    public void setLabelCity(Label labelCity) {
        this.labelCity = labelCity;
    }

    public void setLabelAddress(Label labelAddress) {
        this.labelAddress = labelAddress;
    }

    public void setLabelMatStatus(Label labelMatStatus) {
        this.labelMatStatus = labelMatStatus;
    }

    public void setLabelGender(Label labelGender) {
        this.labelGender = labelGender;
    }

    public void setLabelAge(Label labelAge) {
        this.labelAge = labelAge;
    }

    public void setLabelDiagnosis(Label labelDiagnosis) {
        this.labelDiagnosis = labelDiagnosis;
    }

    public void setLabelRoom(Label labelRoom) {
        this.labelRoom = labelRoom;
    }

    public void setLabelFloor(Label labelFloor) {
        this.labelFloor = labelFloor;
    }

    public void setLabelBed(Label labelBed) {
        this.labelBed = labelBed;
    }

    public void setLabelBloodG(Label labelBloodG) {
        this.labelBloodG = labelBloodG;
    }

    public void setLabelDoctor(Label labelDoctor) {
        this.labelDoctor = labelDoctor;
    }

    public void setLabelID(Label labelID) {
        this.labelID = labelID;
    }

    public void setLabelName(Label labelName) {
        this.labelName = labelName;
    }

    public void setLabelAdmit(Label labelAdmit) {
        this.labelAdmit = labelAdmit;
    }

    public void setLabelWard(Label labelWard) {
        this.labelWard = labelWard;
    }

    public void setLabelPhone(Label labelPhone) {
        this.labelPhone = labelPhone;
    }

    public void setSelectedBedOrRoomValue(String bedValue, String roomValue) {
        this.bedValue = bedValue;
        this.roomValue = roomValue;
    }

    private class DeleteTask extends Task<Object> {

        @Override
        protected Object call() throws Exception {

            try {
                ps = EHospitalDB.getCon().prepareStatement("Delete From PATIENT Where Patient_Name=? and ID=?");
                ps.setString(1, selectedPatientName);
                ps.setInt(2, selectedPatientID);
                ps.executeUpdate();

                //Delete the Selected Patient Bed or Room Value
                takenBedList.remove(bedValue);
                takenRoomList.remove(roomValue);

                ps = EHospitalDB.getCon().prepareStatement("Delete From DISCHARGED_DATE Where Patient_Name=?");
                ps.setString(1, selectedPatientName);
                ps.executeUpdate();

                //Remove Discharged Patient From Bill Module Combo Box
                EHospitalBridge.comboBoxBillPatientName.getItems().remove(selectedPatientName);
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

            labelCity.setText("");
            labelAddress.setText("");
            labelMatStatus.setText("");
            labelGender.setText("");
            labelAge.setText("");
            labelDiagnosis.setText("");
            labelRoom.setText("");
            labelFloor.setText("");
            labelBloodG.setText("");
            labelDoctor.setText("");
            labelID.setText("");
            labelName.setText("");
            labelAdmit.setText("");
            labelWard.setText("");
            labelPhone.setText("");
            labelBed.setText("");

            EHospitalDialog.dialogAlert(Alert.AlertType.INFORMATION, "Delete Succesful", label.getScene().getWindow());
        });
        new Thread(task).start();
    }

    @FXML
    private void noAction() {
        EHospitalDialog.dialog.close();
    }

}
