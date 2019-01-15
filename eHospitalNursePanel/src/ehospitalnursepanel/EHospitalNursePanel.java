/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitalnursepanel;

import ehospitalnursebloodbank.EHospitalNurseBloodBank;
import ehospitalnursedeathbirth.EHospitalNurseDeathBirth;
import ehospitalnursepatienttreatment.EHospitalNursePatientTreatment;
import ehospitalnursepatientview.EHospitalNursePatientView;
import ehospitalnurseprofile.EHospitalNurseProfile;
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
public class EHospitalNursePanel extends Application {
    
    private Parent root;
    private FXMLLoader loader;
    private EHospitalNursePanelController nursePanel;
    private EHospitalNurseBloodBank bloodBank;
    private EHospitalNurseDeathBirth deathBirth;
    private EHospitalNursePatientTreatment treat;
    private EHospitalReport report;
    private EHospitalNurseProfile nurseProfile;
    
    @Override
    public void start(Stage stage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("eHospitalNursePanel.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }
    
    public EHospitalNursePanel(){
        try {
            EHospitalNursePatientView pat=new EHospitalNursePatientView();
            bloodBank=new EHospitalNurseBloodBank();
            deathBirth=new EHospitalNurseDeathBirth();
            treat=new EHospitalNursePatientTreatment();
            report=new EHospitalReport();
            nurseProfile=new EHospitalNurseProfile();
            
            loader=new FXMLLoader(getClass().getResource("EHospitalNursePanel.fxml"));
            root=loader.load();
            
            nursePanel=loader.getController();
            
            nursePanel.setRootPatientView(pat.getFxml());
            nursePanel.setRootBloodBank(bloodBank.getFxml());
            nursePanel.setRootDeathBirth(deathBirth.getFxml());
            nursePanel.setRootPatientTreatment(treat.getFxml());
            nursePanel.setRootReport(report.getFxml());
            nursePanel.setRootProfile(nurseProfile.getFxml());
            
            nursePanel.setDeathBirth(deathBirth);
            nursePanel.setBloodBank(bloodBank);
            nursePanel.setTreat(treat);
            nursePanel.setNurseProfile(nurseProfile);
            
            nursePanel.openPatientModule();
        } catch (Exception e) {
        }
    }
    
    public Parent getFxml(){
        return root;
    }
    
    public void setLoginRoot(Parent loginRoot){
        nursePanel.setLoginRoot(loginRoot);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
