/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitaldoctorprofile;

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
public class EHospitalDoctorProfile extends Application {
    
    private Parent root;
    private FXMLLoader loader;
    private eHospitalDoctorProfileController profile;
    
    @Override
    public void start(Stage stage) throws Exception {
//        root = FXMLLoader.load(getClass().getResource("eHospitalProfile.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }
    
    public EHospitalDoctorProfile(){
        try {
            EHospitalDB.initDB();
            loader=new FXMLLoader(getClass().getResource("eHospitalDoctorProfile.fxml"));
            root=loader.load();
            
            profile=loader.getController();
        } catch (Exception e) {
        }
    }
    
    public Parent getFxml(){
        return root;
    }
    
    public void displayProfileDetails(){
        profile.displayProfileDetails();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
