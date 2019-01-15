/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitaldoctordeathbirth;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Andre Kelvin
 */
public class EHospitalDoctorDeathBirth extends Application {
    
    private Parent root;
    private FXMLLoader loader;
    private EHospitalDoctorDeathBirthController deathBirth;
    
    @Override
    public void start(Stage stage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("eHospitalDeathBirth.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }
    
    public EHospitalDoctorDeathBirth(){
        try {
            loader=new FXMLLoader(getClass().getResource("EHospitalDoctorDeathBirth.fxml"));
            root=loader.load();
            
            deathBirth=loader.getController();
        } catch (Exception e) {
        }
    }

    public Parent getFxml(){
        return root;
    }
    
    /**
     * Populate Both Death and Birth List View
     */
    public void populateListView(){
        deathBirth.populateDeathListView();
        deathBirth.populateBabyListView();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
