/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitalnursepatientview;

import ehospitaldb.EHospitalDB;
import ehospitalpatientviewcontroller.EHospitalPatientViewController;

/**
 *
 * @author Andre Kelvin
 */
public class EHospitalNursePatientViewController extends EHospitalPatientViewController {    

    @Override
    public void displayPatientName() {
        try {
            obList.clear();
            ps = EHospitalDB.getCon().prepareStatement("Select PATIENT_NAME From PATIENT Where CATEGORY=?");
            ps.setString(1, comboPatientCat.getValue().toString());
            rs = ps.executeQuery();

            while (rs.next()) {
                obList.add(rs.getString("PATIENT_NAME"));
            }   
            ps.close();
        } catch (Exception e) {
        }
    }

    @Override
    public void searchPatient() {
        try {
            searchList.clear();
            ps = EHospitalDB.getCon().prepareStatement("Select PATIENT_NAME From PATIENT");
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
    
}
