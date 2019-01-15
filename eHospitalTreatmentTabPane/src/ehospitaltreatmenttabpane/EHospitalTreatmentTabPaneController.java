/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitaltreatmenttabpane;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author Andre Kelvin
 */
public class EHospitalTreatmentTabPaneController {
    
    @FXML
    private BorderPane bPaneTreatment,bPanePatientTreatment;
    private Parent rootTreatment,rootPatientTreatment;
    
    public void setRoots(Parent rootTreatment, Parent rootPatientTreatment){
        this.rootTreatment=rootTreatment;
        this.rootPatientTreatment=rootPatientTreatment;
    }

    @FXML
    private void openPatientTreatmentModule() {
        try {
            bPanePatientTreatment.setCenter(rootPatientTreatment);
        } catch (Exception e) {e.printStackTrace();
        }
    }

    @FXML
    public void openTreatmentModule() {
        try {
            bPaneTreatment.setCenter(rootTreatment);
        } catch (Exception e) {e.printStackTrace();
        }
    }
    
}
