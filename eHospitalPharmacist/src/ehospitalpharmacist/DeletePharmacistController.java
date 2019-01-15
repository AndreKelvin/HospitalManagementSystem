/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitalpharmacist;

import ehospitalstaff.DeleteStaffController;

/**
 * FXML Controller class
 *
 * @author Andre Kelvin
 */
public class DeletePharmacistController extends DeleteStaffController {
    
    public DeletePharmacistController(){
        super.setTableName("PHARMACIST");
    }
    
}
