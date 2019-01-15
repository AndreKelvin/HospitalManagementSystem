/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitaltreatmenttabpane;

import ehospitalpatienttreatment.EHospitalPatientTreatment;
import ehospitaltreatment.EHospitalTreatment;
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
public class EHospitalTreatmentTabPane extends Application {
    
    private Parent root;
    private FXMLLoader loader;
    private StackPane stackPaneTreat,stackPanePatientTreat;
    
    @Override
    public void start(Stage stage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("EHospitalTreatmentTabPane.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }
    
    public EHospitalTreatmentTabPane(){
        try {
            EHospitalPatientTreatment pat=new EHospitalPatientTreatment();
            EHospitalTreatment treat=new EHospitalTreatment();
            
            loader = new FXMLLoader(getClass().getResource("EHospitalTreatmentTabPane.fxml"));
            root=loader.load();
            
            EHospitalTreatmentTabPaneController tab=loader.getController();
            tab.setRoots(treat.getFxml(), pat.getFxml());
            
            stackPaneTreat=treat.getStackPane();
            stackPanePatientTreat=pat.getStackPane();
            tab.openTreatmentModule();
        } catch (Exception e) {
        }
    }
    
    public Parent getFxml(){
        return root;
    }

    public StackPane getStackPaneTreat() {
        return stackPaneTreat;
    }

    public StackPane getStackPanePatientTreat() {
        return stackPanePatientTreat;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
