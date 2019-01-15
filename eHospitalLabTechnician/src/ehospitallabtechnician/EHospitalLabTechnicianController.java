/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitallabtechnician;

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
public class EHospitalLabTechnicianController extends EHospitalStaff implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            super.setImageFolder("LabTechnicianImage");
            super.setTableName("LABTECHNICIAN");
            super.setFxml("AddLabTechnician.fxml", "EditLabTechnician.fxml", "DeleteLabTechnician.fxml");
            super.setComboBoxItems(comboDeptLabTech);

            //Populate Department Combo Box
            ps = EHospitalDB.getCon().prepareStatement("Select Name From DEPARTMENT");
            rs = ps.executeQuery();

            while (rs.next()) {
                comboDeptLabTech.getItems().add(rs.getString("Name"));
            }
            ps.close();
            
            initialize();

            comboDeptLabTech.getSelectionModel().selectedItemProperty().addListener((ObservableValue observable, Object oldValue, Object newValue) -> {
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
            ps = EHospitalDB.getCon().prepareStatement("Select Department From LABTECHNICIAN Where Name=?");
            ps.setString(1, searchName);
            rs = ps.executeQuery();

            if (rs.next()) {
                comboDeptLabTech.setValue(rs.getString("Department"));
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
            ps = EHospitalDB.getCon().prepareStatement("Select Name From LABTECHNICIAN Where Department=?");
            ps.setString(1, comboDeptLabTech.getValue().toString());
            rs = ps.executeQuery();

            while (rs.next()) {
                obList.add(rs.getString("Name"));
            }
            ps.close();
        } catch (Exception e) {
        }
    }

}
