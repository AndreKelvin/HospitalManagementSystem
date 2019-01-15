/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitaldoctor;

import ehospitalstaff.AddStaffController;

/**
 * FXML Controller class
 *
 * @author Andre Kelvin
 */
public class AddDoctorController  extends AddStaffController{
    
    public AddDoctorController(){
        super.setImageFolder("DoctorImage");
        super.setTableName("DOCTOR");
    }
    
}
