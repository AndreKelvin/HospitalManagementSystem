/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitalstafftabpane;

import ehospitaldoctor.EHospitalDoctor;
import ehospitallabtechnician.EHospitalLabTechnician;
import ehospitalnurse.EHospitalNurse;
import ehospitalpharmacist.EHospitalPharmacist;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Andre Kelvin
 */
public class EHospitalStaffTabPane extends Application {
    
    private Parent root;
    private FXMLLoader loader; 
    private StackPane stackPaneDoc,stackPaneNurse,stackPanePhar,stackPaneLabTech;
    
    @Override
    public void start(Stage stage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("EHospitalStaffTabPane.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }
    
    public EHospitalStaffTabPane(){
        try {
            EHospitalDoctor doctor=new EHospitalDoctor();
            EHospitalNurse nurse=new EHospitalNurse();
            EHospitalLabTechnician labTech=new EHospitalLabTechnician();
            EHospitalPharmacist pharmacist=new EHospitalPharmacist();
            
            loader = new FXMLLoader(getClass().getResource("EHospitalStaffTabPane.fxml"));
            root=loader.load();
            
            EHospitalStaffTabPaneController tab=loader.getController();
            tab.setRoots(doctor.getFxml(), nurse.getFxml(), labTech.getFxml(), pharmacist.getFxml());
            
            stackPaneDoc=doctor.getStackPane();
            stackPaneNurse=nurse.getStackPane();
            stackPaneLabTech=labTech.getStackPane();
            stackPanePhar=pharmacist.getStackPane();
            
            tab.openDoctorModule();
        } catch (Exception e) {
        }
    }
    
    public Parent getFxml(){
        return root;
    }

    public StackPane getStackPaneDoc() {
        return stackPaneDoc;
    }

    public StackPane getStackPaneNurse() {
        return stackPaneNurse;
    }

    public StackPane getStackPanePhar() {
        return stackPanePhar;
    }

    public StackPane getStackPaneLabTech() {
        return stackPaneLabTech;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
