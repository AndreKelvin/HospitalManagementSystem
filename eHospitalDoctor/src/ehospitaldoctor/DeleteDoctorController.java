/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitaldoctor;

import ehospitalstaff.DeleteStaffController;

/**
 * FXML Controller class
 *
 * @author Andre Kelvin
 */
public class DeleteDoctorController extends DeleteStaffController {
    
    public DeleteDoctorController(){
        super.setTableName("DOCTOR");
    }

}
