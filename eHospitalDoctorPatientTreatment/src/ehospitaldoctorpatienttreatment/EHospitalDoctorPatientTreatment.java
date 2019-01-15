/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitaldoctorpatienttreatment;

import ehospitaldb.EHospitalDB;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Andre Kelvin
 */
public class EHospitalDoctorPatientTreatment extends Application {
    
    private Parent root;
    private FXMLLoader loader;
    private eHospitalDoctorPatientTreatmentController patientTreat;
    
    @Override
    public void start(Stage stage) throws Exception {
//        EHospitalDB.initDB();
//        root = FXMLLoader.load(getClass().getResource("eHospitalDoctorPatientTreatment.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }
    
    public EHospitalDoctorPatientTreatment() {
        try {
            EHospitalDB.initDB();
            loader = new FXMLLoader(getClass().getResource("eHospitalDoctorPatientTreatment.fxml"));
            root=loader.load();
            
            patientTreat=loader.getController();
        } catch (Exception e) {
        }
    }
    
    public Parent getFxml(){
        return root;
    }
    
    public void populateTable() {
        patientTreat.populateTableView();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
