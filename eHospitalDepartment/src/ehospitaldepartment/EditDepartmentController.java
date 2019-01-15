/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitaldepartment;

import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import ehospitalbridge.EHospitalBridge;
import ehospitaldialog.EHospitalDialog;
import ehospitaldb.EHospitalDB;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Andre Kelvin
 */
public class EditDepartmentController {

    @FXML
    private VBox vBox;
    @FXML
    private JFXSpinner spinner;
    @FXML
    private JFXTextField textDepart;
    @FXML
    private JFXTextArea textDescrip;
    private PreparedStatement ps;
    private ResultSet rs;
    private Department selectedValue;
    private String department, descrip;

    public void setSelectedValue(Department selectedValue) throws SQLException {
        this.selectedValue = selectedValue;

        ps = EHospitalDB.getCon().prepareStatement("Select * From DEPARTMENT Where Name=?");
        ps.setString(1, selectedValue.getDepartment());
        rs = ps.executeQuery();

        while (rs.next()) {
            textDepart.setText(rs.getString("Name"));
            textDescrip.setText(rs.getString("Description"));
        }
    }

    private class EditTask extends Task<Object> {

        @Override
        protected Object call() throws Exception {

            try {
                ps = EHospitalDB.getCon().prepareStatement("Update DEPARTMENT Set Name=?,Description=? Where Name=?");
                ps.setString(1, department);
                ps.setString(2, descrip);
                ps.setString(3, selectedValue.getDepartment());
                ps.executeUpdate();

                //Edit Department Combo Box Values in Doctor,Nurse,Pharmacist,LabTech Module 
                EHospitalBridge.comboBoxDepartmentDoc.getItems().remove(selectedValue.getDepartment());
                EHospitalBridge.comboBoxDepartmentNurse.getItems().remove(selectedValue.getDepartment());
                EHospitalBridge.comboBoxDepartmentPhar.getItems().remove(selectedValue.getDepartment());
                EHospitalBridge.comboBoxDepartmentLabTech.getItems().remove(selectedValue.getDepartment());

                EHospitalBridge.comboBoxDepartmentDoc.getItems().add(department);
                EHospitalBridge.comboBoxDepartmentNurse.getItems().add(department);
                EHospitalBridge.comboBoxDepartmentPhar.getItems().add(department);
                EHospitalBridge.comboBoxDepartmentLabTech.getItems().add(department);

                selectedValue.setDepartment(department);
                selectedValue.setDescription(descrip);
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
    private void saveAction() {
        department = textDepart.getText().trim();
        descrip = textDescrip.getText().trim();

        if (department.isEmpty() || descrip.isEmpty()) {
            EHospitalDialog.dialogAlert(Alert.AlertType.ERROR, "Please Fill All Required Input", textDepart.getScene().getWindow());
        } else {
            vBox.setDisable(true);
            spinner.setVisible(true);

            EditTask task = new EditTask();
            task.setOnSucceeded((event) -> {
                vBox.setDisable(false);
                spinner.setVisible(false);

                EHospitalDialog.dialog.close();
                EHospitalDialog.dialogAlert(Alert.AlertType.INFORMATION, "Save Successful", textDepart.getScene().getWindow());
            });
            new Thread(task).start();
        }
    }
}
