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
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Andre Kelvin
 */
public class AddDepartmentController {

    @FXML
    private VBox vBox;
    @FXML
    private JFXSpinner spinner;
    @FXML
    private JFXTextField textDepart;
    @FXML
    private JFXTextArea textDescrip;
    private ObservableList obList;
    private PreparedStatement ps;
    private String department;
    private String descrip;

    public void setOblist(ObservableList obList) {
        this.obList = obList;
    }

    private class SaveTask extends Task<Object> {

        @Override
        protected Object call() throws Exception {

            try {
                ps = EHospitalDB.getCon().prepareStatement("Insert Into DEPARTMENT Values(?,?)");
                ps.setString(1, department);
                ps.setString(2, descrip);
                ps.executeUpdate();

                obList.add(new Department(department, descrip));

                //Add Department Combo Box Values in Doctor,Nurse,Pharmacist,LabTech Module
                EHospitalBridge.comboBoxDepartmentDoc.getItems().add(department);
                EHospitalBridge.comboBoxDepartmentNurse.getItems().add(department);
                EHospitalBridge.comboBoxDepartmentPhar.getItems().add(department);
                EHospitalBridge.comboBoxDepartmentLabTech.getItems().add(department);
            } catch (Exception e) {
                vBox.setDisable(false);
                spinner.setVisible(false);

                Platform.runLater(() -> {
                    EHospitalDialog.dialogAlert(Alert.AlertType.ERROR, "Database connection failure", textDepart.getScene().getWindow());
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

            SaveTask task = new SaveTask();
            task.setOnSucceeded((event) -> {
                vBox.setDisable(false);
                spinner.setVisible(false);

                EHospitalDialog.dialogAlert(Alert.AlertType.INFORMATION, "Save Successful", textDepart.getScene().getWindow());

                textDepart.clear();
                textDescrip.clear();
            });
            new Thread(task).start();
        }
    }
}
