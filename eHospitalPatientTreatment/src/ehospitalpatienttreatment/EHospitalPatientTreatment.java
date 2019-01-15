/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitalpatienttreatment;

import ehospitaldb.EHospitalDB;
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
public class EHospitalPatientTreatment extends Application {
    
    private Parent root;
    private FXMLLoader loader;
    private EHospitalPatientTreatmentController patientTreat;
    private StackPane stackPaneTreat;
    
    @Override
    public void start(Stage stage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("EHospitalPatientTreatment.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }
    
    public EHospitalPatientTreatment(){
        try {
            EHospitalDB.initDB();
            loader=new FXMLLoader(getClass().getResource("EHospitalPatientTreatment.fxml"));
            root=loader.load();
            
            patientTreat=loader.getController();
            stackPaneTreat=patientTreat.getStackPane();
        } catch (Exception e) {
        }
    }
    
    public Parent getFxml(){
        return root;
    }
    
    public void populateTable() {
        patientTreat.populateTableView();
    }
    
    public StackPane getStackPane() {
        return stackPaneTreat;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
