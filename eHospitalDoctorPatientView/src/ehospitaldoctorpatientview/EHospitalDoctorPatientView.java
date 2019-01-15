/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitaldoctorpatientview;

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
public class EHospitalDoctorPatientView extends Application {
    
    private Parent root;
    
    @Override
    public void start(Stage stage) throws Exception {
        //EHospitalDB.initDB();
        //Parent root = FXMLLoader.load(getClass().getResource("eHospitalPatientView.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }
    
    public EHospitalDoctorPatientView(){
        try {
            EHospitalDB.initDB();
            root=FXMLLoader.load(getClass().getResource("EHospitalDoctorPatientView.fxml"));
        } catch (Exception e) {
        }
    }
    
    public Parent getFxml(){
        return root;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
