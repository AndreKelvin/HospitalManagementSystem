/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitaldoctorappointment;

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
public class EHospitalDoctorAppointment extends Application {
    
    private Parent root;
    private FXMLLoader loader;
    private EHospitalDoctorAppointmentController app;
    
    @Override
    public void start(Stage stage) throws Exception {
//        EHospitalDB.initDB();
//        Parent root = FXMLLoader.load(getClass().getResource("eHospitalAppointment.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    public EHospitalDoctorAppointment() {
        try {
            EHospitalDB.initDB();
            loader = new FXMLLoader(getClass().getResource("EHospitalDoctorAppointment.fxml"));
            root=loader.load();
            
            app=loader.getController();
        } catch (Exception e) {
        }
    }
    
    public Parent getFxml(){
        return root;
    }
    
    /**
     * Since the Table View In Appointment Controller only needs to have
     * The logged in Doctor patient's appointments
     * This will populate The Table view in Appointment Controller
     * Because the Doctor name will be gotten form Login Controller
     */
    public void populateTable(){
        app.populateTable();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
