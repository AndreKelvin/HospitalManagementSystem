/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitallabtechpatient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Andre Kelvin
 */
public class EHospitalLabTechPatient extends Application {
    
    private Parent root;
    private FXMLLoader loader;
    private EHospitalLabTechPatientController lab;
    
    @Override
    public void start(Stage stage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("EHospitalLabTechPatient.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }
    
    public EHospitalLabTechPatient(){
        try {
            loader=new FXMLLoader(getClass().getResource("EHospitalLabTechPatient.fxml"));
            root=loader.load();
            
            lab=loader.getController();
        } catch (Exception e) {
        }
    }
    
    public void populateTable(){
        lab.populateTable();
    }

    public Parent getFxml() {
        return root;
    }
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
