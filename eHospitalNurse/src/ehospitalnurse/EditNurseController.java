/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitalnurse;

import ehospitalstaff.EditStaffController;

/**
 * FXML Controller class
 *
 * @author Andre Kelvin
 */
public class EditNurseController extends EditStaffController {
    
    public EditNurseController(){
        super.setImageFolder("NurseImage");
        super.setTableName("NURSE");
    }
    
}
