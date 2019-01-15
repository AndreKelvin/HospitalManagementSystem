/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitalnursedeathbirth;

import ehospitaldb.EHospitalDB;
import ehospitaldeathbirthcontroller.EHospitalDeathBirthController;

/**
 *
 * @author Andre Kelvin
 */
public class EHospitalNurseDeathBirthController extends EHospitalDeathBirthController{    

    @Override
    public void searchDeadPatient() {
        try {
            searchDeathList.clear();
            ps = EHospitalDB.getCon().prepareStatement("Select NAME From DEATH");
            rs = ps.executeQuery();

            while (rs.next()) {
                searchDeathList.add(rs.getString("NAME"));
            }
            ps.close();

            deathSuggestions.clearSuggestions();
            deathSuggestions.addPossibleSuggestions(searchDeathList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void searchBabies() {
        try {
            searchBirthList.clear();
            ps = EHospitalDB.getCon().prepareStatement("Select BABY From BIRTH");
            rs = ps.executeQuery();

            while (rs.next()) {
                searchBirthList.add(rs.getString("BABY"));
            }
            ps.close();

            birthSuggestions.clearSuggestions();
            birthSuggestions.addPossibleSuggestions(searchBirthList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void populateDeathListView() {
        try {
            listViewDeath.getItems().clear();
            ps = EHospitalDB.getCon().prepareStatement("Select NAME From DEATH");
            rs = ps.executeQuery();

            while (rs.next()) {
                listViewDeath.getItems().add(rs.getString("NAME"));
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void populateBabyListView() {
        try {
            listViewBirth.getItems().clear();
            ps = EHospitalDB.getCon().prepareStatement("Select BABY From BIRTH");
            rs = ps.executeQuery();

            while (rs.next()) {
                listViewBirth.getItems().add(rs.getString("BABY"));
            }
        } catch (Exception e) {
        }
    }
    
}
