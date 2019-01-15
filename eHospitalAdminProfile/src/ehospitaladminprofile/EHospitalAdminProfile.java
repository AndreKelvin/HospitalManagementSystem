/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitaladminprofile;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Andre Kelvin
 */
public class EHospitalAdminProfile extends Application {
    
    private Parent root;
    private FXMLLoader loader;
    private EHospitalAdminProfileController profile;
    
    @Override
    public void start(Stage stage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("EHospitalAdminProfile.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }
    
    public EHospitalAdminProfile(){
        try {
            loader=new FXMLLoader(getClass().getResource("EHospitalAdminProfile.fxml"));
            root=loader.load();
            
            profile=loader.getController();
        } catch (Exception e) {
        }
    }

    public Parent getFxml() {
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
