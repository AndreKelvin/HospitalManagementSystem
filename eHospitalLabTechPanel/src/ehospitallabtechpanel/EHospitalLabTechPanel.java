/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitallabtechpanel;

import ehospitalbloodbank.EHospitalBloodBank;
import ehospitallabtechpatient.EHospitalLabTechPatient;
import ehospitallabtechprofile.EHospitalLabTechProfile;
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
public class EHospitalLabTechPanel extends Application {
    
    private Parent root;
    private FXMLLoader loader;
    private EHospitalLabTechPanelController labPanel;
    
    @Override
    public void start(Stage stage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("EHospitalLabTechPanel.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }
    
    public EHospitalLabTechPanel(){
        try {
            EHospitalLabTechPatient pat=new EHospitalLabTechPatient();
            EHospitalBloodBank blood=new EHospitalBloodBank();
            EHospitalReport report=new EHospitalReport();
            EHospitalLabTechProfile profile=new EHospitalLabTechProfile();
            
            loader=new FXMLLoader(getClass().getResource("EHospitalLabTechPanel.fxml"));
            root=loader.load();
            
            labPanel=loader.getController();
            
            labPanel.setRootPatient(pat.getFxml());
            labPanel.setRootBloodBank(blood.getFxml());
            labPanel.setRootReport(report.getFxml());
            labPanel.setRootProfile(profile.getFxml());
            
            labPanel.setBlood(blood);
            labPanel.setPat(pat);
            labPanel.setProfile(profile);
            
            labPanel.openPatientModule();
        } catch (Exception e) {
        }
    }
    public Parent getFxml(){
        return root;
    }
    
    public void setLoginRoot(Parent loginRoot){
        labPanel.setLoginRoot(loginRoot);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
