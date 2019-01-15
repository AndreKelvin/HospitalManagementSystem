/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitallabtechprofile;

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
public class EHospitalLabTechProfile extends Application {
    
    private Parent root;
    private FXMLLoader loader;
    private EHospitalLabTechProfileController lab;
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("EHospitalLabTechProfile.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }
    
    public EHospitalLabTechProfile(){
        try {
            EHospitalDB.initDB();
            loader=new FXMLLoader(getClass().getResource("EHospitalLabTechProfile.fxml"));
            root=loader.load();
            
            lab=loader.getController();
        } catch (Exception e) {
        }
    }
    
    public Parent getFxml(){
        return root;
    }
    
    public void displayProfileDetails(){
        lab.displayProfileDetails();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
