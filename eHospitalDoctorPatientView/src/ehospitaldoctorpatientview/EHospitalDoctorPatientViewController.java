/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitaldoctorpatientview;

import ehospitalbridge.EHospitalBridge;
import ehospitaldb.EHospitalDB;
import ehospitalpatientviewcontroller.EHospitalPatientViewController;

/**
 *
 * @author Andre Kelvin
 */
public class EHospitalDoctorPatientViewController extends EHospitalPatientViewController {

    @Override
    public void searchPatient() {
        try {
            searchList.clear();
            ps = EHospitalDB.getCon().prepareStatement("Select PATIENT_NAME From PATIENT Where DOCTOR=?");
            ps.setString(1, EHospitalBridge.loginUser);
            rs = ps.executeQuery();

            while (rs.next()) {
                searchList.add(rs.getString("PATIENT_NAME"));
            }
            ps.close();

            suggestions.clearSuggestions();
            suggestions.addPossibleSuggestions(searchList);
        } catch (Exception e) {
        }
    }

    @Override
    public void displayPatientName() {
        try {
            obList.clear();
            ps = EHospitalDB.getCon().prepareStatement("Select PATIENT_NAME From PATIENT Where CATEGORY=? and DOCTOR=?");
            ps.setString(1, comboPatientCat.getValue().toString());
            ps.setString(2, EHospitalBridge.loginUser);
            rs = ps.executeQuery();

            while (rs.next()) {
                obList.add(rs.getString("PATIENT_NAME"));
            }   
            ps.close();
        } catch (Exception e) {
        }
    }
    
    
    
}
