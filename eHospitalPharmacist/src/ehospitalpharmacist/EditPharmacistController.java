/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitalpharmacist;

import ehospitalstaff.EditStaffController;

/**
 * FXML Controller class
 *
 * @author Andre Kelvin
 */
public class EditPharmacistController extends EditStaffController {
    
    public EditPharmacistController(){
        super.setImageFolder("PharmacistImage");
        super.setTableName("PHARMACIST");
    }
    
}
