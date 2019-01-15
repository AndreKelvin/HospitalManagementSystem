/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitaldoctor;

import ehospitaldb.EHospitalDB;
import ehospitalstaff.EHospitalStaff;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;

/**
 *
 * @author Andre Kelvin
 */
public class EHospitalDoctorController extends EHospitalStaff implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            super.setTableName("DOCTOR");
            super.setFxml("AddDoctor.fxml", "EditDoctor.fxml", "DeleteDoctor.fxml");
            super.setImageFolder("DoctorImage");
            super.setComboBoxItems(comboDeptDoc);
            
            //Populate Department Combo Box
            ps = EHospitalDB.getCon().prepareStatement("Select Name From DEPARTMENT");
            rs = ps.executeQuery();

            while (rs.next()) {
                comboDeptDoc.getItems().add(rs.getString("Name"));
            }
            ps.close();
            
            initialize();
            
            comboDeptDoc.getSelectionModel().selectedItemProperty().addListener((ObservableValue observable, Object oldValue, Object newValue) -> {
                try {
                    displayStaffName();

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

                    buttonEdit.setDisable(true);
                    buttonDelete.setDisable(true);

                } catch (Exception e) {
                }
            });
        } catch (Exception e) {
        }
    }

    @Override
    public void searchAction() {
        try {
            searchName = textSearch.getText();
            ps = EHospitalDB.getCon().prepareStatement("Select Department From DOCTOR Where Name=?");
            ps.setString(1, searchName);
            rs = ps.executeQuery();

            if (rs.next()) {
                comboDeptDoc.setValue(rs.getString("Department"));
                listView.getSelectionModel().select(searchName);
            }
            ps.close();
        } catch (Exception e) {
        }
    }

    @Override
    public void displayStaffName() {
        try {
            obList.clear();
            ps = EHospitalDB.getCon().prepareStatement("Select Name From DOCTOR Where Department=?");
            ps.setString(1, comboDeptDoc.getValue().toString());
            rs = ps.executeQuery();

            while (rs.next()) {
                obList.add(rs.getString("Name"));
            }
            ps.close();
        } catch (Exception e) {
        }
    }

}
