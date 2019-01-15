/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitalpharmacist;

import ehospitalstaff.AddStaffController;

/**
 * FXML Controller class
 *
 * @author Andre Kelvin
 */
public class AddPharmacistController extends AddStaffController {
    
    public AddPharmacistController(){
        super.setImageFolder("PharmacistImage");
        super.setTableName("PHARMACIST");
    }
    
}
