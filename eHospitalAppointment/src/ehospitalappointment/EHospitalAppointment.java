/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitalappointment;

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
public class EHospitalAppointment extends Application {
    
    private Parent root;
    private FXMLLoader loader;
    private EHospitalAppointmentController app;
    
    @Override
    public void start(Stage stage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("EHospitalAppointment.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }
    
    public EHospitalAppointment(){
        try {
            EHospitalDB.initDB();
            loader=new FXMLLoader(getClass().getResource("EHospitalAppointment.fxml"));
            root=loader.load();
            
            app=loader.getController();
        } catch (Exception e) {
        }
    }
    
    public void populateTableView(){
        app.populateTable();
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
