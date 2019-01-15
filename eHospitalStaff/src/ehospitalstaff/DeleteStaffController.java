/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitalstaff;

import com.jfoenix.controls.JFXSpinner;
import ehospitaldb.EHospitalDB;
import ehospitaldialog.EHospitalDialog;
import java.io.File;
import java.sql.PreparedStatement;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Andre Kelvin
 */
public class DeleteStaffController /*extends DeleteStaff*/ {

//    public DeleteLabTechnicianController(){
//        super.setTableName("LabTechnician");
//    }
    @FXML
    private VBox vBox;
    @FXML
    private JFXSpinner spinner;
    @FXML
    private Label label;
    private String selectedStaffName, tableName;
    private int selectedStaffID;
    private PreparedStatement ps;
    private File staffImageFile;
    private Label labelName, labelPhone, labelCity, labelAddress, labelMatStatus, labelGender, labelAge, labelEmail, labelID, labelDateJoined, labelSalary, labelDegree;
    private ImageView imageView;
    private Image defaultImage;

    public void setSelectedStaffName(String selectedStaffName) {
        this.selectedStaffName = selectedStaffName;
        label.setText("Are your sure you want to Delete " + selectedStaffName + "?");
    }

    public void setStaffImageFile(File staffImageFile) {
        this.staffImageFile = staffImageFile;
    }

    public void setStaffID(int selectedStaffID) {
        this.selectedStaffID = selectedStaffID;
    }

    public void setLabelName(Label labelName) {
        this.labelName = labelName;
    }

    public void setLabelPhone(Label labelPhone) {
        this.labelPhone = labelPhone;
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

    public void setLabelEmail(Label labelEmail) {
        this.labelEmail = labelEmail;
    }

    public void setLabelID(Label labelID) {
        this.labelID = labelID;
    }

    public void setLabelDateJoined(Label labelDateJoined) {
        this.labelDateJoined = labelDateJoined;
    }

    public void setLabelSalary(Label labelSalary) {
        this.labelSalary = labelSalary;
    }

    public void setLabelDegree(Label labelDegree) {
        this.labelDegree = labelDegree;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public void setDefaultImage(Image defaultImage) {
        this.defaultImage = defaultImage;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    private class DeleteTask extends Task<Object> {

        @Override
        protected Object call() throws Exception {

            try {
                ps = EHospitalDB.getCon().prepareStatement("Delete From " + tableName + " Where Name=? and ID=?");
                ps.setString(1, selectedStaffName);
                ps.setInt(2, selectedStaffID);
                ps.executeUpdate();

                //Delete the Selected Staff Image
                staffImageFile.delete();
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

        //Empty All Info
        labelID.setText("");
        labelName.setText("");
        labelAge.setText("");
        labelGender.setText("");
        labelAddress.setText("");
        labelCity.setText("");
        labelPhone.setText("");
        labelMatStatus.setText("");
        labelDateJoined.setText("");
        labelSalary.setText("");
        labelEmail.setText("");
        labelDegree.setText("");
        imageView.setImage(defaultImage);

        EHospitalDialog.dialogAlert(Alert.AlertType.INFORMATION, "Delete Succesful", label.getScene().getWindow());
        });
        new Thread(task).start();
        
    }

    @FXML
    private void noAction() {
        EHospitalDialog.dialog.close();
    }

}
