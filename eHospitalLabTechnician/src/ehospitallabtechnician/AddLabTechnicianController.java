/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitallabtechnician;

import ehospitalstaff.AddStaffController;

/**
 * FXML Controller class
 *
 * @author Andre Kelvin
 */
public class AddLabTechnicianController extends AddStaffController {
    
    public AddLabTechnicianController(){
        super.setImageFolder("LabTechnicianImage");
        super.setTableName("LABTECHNICIAN");
    }
    
}
