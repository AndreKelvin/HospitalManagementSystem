/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitalpharmacistpanel;

import ehospitaldrugs.EHospitalDrug;
import ehospitalpatienttreatment.EHospitalPatientTreatment;
import ehospitalpharmacistprofile.EHospitalPharmacistProfile;
import ehospitalreport.EHospitalReport;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Andre Kelvin
 */
public class EHospitalPharmacistPanel extends Application {
    
    private Parent root;
    private FXMLLoader loader;
    private EHospitalPharmacistPanelController phar;
    
    @Override
    public void start(Stage stage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("EHospitalPharmacistPanel.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }
    
    public EHospitalPharmacistPanel(){
        try {
            EHospitalDrug drug=new EHospitalDrug();
            EHospitalPatientTreatment pat=new EHospitalPatientTreatment();
            EHospitalReport report=new EHospitalReport();
            EHospitalPharmacistProfile profile=new EHospitalPharmacistProfile();
            
            loader=new FXMLLoader(getClass().getResource("EHospitalPharmacistPanel.fxml"));
            root=loader.load();
            
            phar=loader.getController();
            
            phar.setRootDrug(drug.getFxml());
            phar.setRootPatientTreatment(pat.getFxml());
            phar.setRootReport(report.getFxml());
            phar.setRootProfile(profile.getFxml());
            
            phar.setDrug(drug);
            phar.setPatientTreatment(pat);
            phar.setProfile(profile);
            
            phar.openDrugModule();
        } catch (Exception e) {
        }
    }
    
    public Parent getFxml(){
        return root;
    }
    
    public void setLoginRoot(Parent loginRoot){
        phar.setLoginRoot(loginRoot);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
