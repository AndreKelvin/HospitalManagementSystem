/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitalnurse;

import ehospitalstaff.AddStaffController;

/**
 * FXML Controller class
 *
 * @author Andre Kelvin
 */
public class AddNurseController extends AddStaffController {
    
    public AddNurseController(){
        super.setImageFolder("NurseImage");
        super.setTableName("NURSE");
    }
    
}
