/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitalnurse;

import ehospitalstaff.DeleteStaffController;

/**
 * FXML Controller class
 *
 * @author Andre Kelvin
 */
public class DeleteNurseController extends DeleteStaffController {
    
    public DeleteNurseController(){
        super.setTableName("NURSE");
    }
    
}
