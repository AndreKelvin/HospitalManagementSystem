/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitalnursebloodbank;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Andre Kelvin
 */
public class EHospitalNurseBloodBank extends Application {
    
    private Parent root;
    private FXMLLoader loader;
    private EHospitalNurseBloodBankController bBank;
    
    @Override
    public void start(Stage stage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("EHospitalNurseBloodBank.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }
    
    public EHospitalNurseBloodBank(){
        try {
            loader=new FXMLLoader(getClass().getResource("EHospitalNurseBloodBank.fxml"));
            root=loader.load();
            
            bBank=loader.getController();
        } catch (Exception e) {
        }
    }
    
    public Parent getFxml(){
        return root;
    }
    
    public void populateTableView(){
        bBank.populateBloodGroupTable();
        bBank.populateDonorTable();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
