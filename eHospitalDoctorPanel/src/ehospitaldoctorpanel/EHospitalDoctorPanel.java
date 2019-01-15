/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitaldoctorpanel;

import ehospitaldoctorappointment.EHospitalDoctorAppointment;
import ehospitaldoctordeathbirth.EHospitalDoctorDeathBirth;
import ehospitaldoctorpatienttreatment.EHospitalDoctorPatientTreatment;
import ehospitaldoctorprofile.EHospitalDoctorProfile;
import ehospitaldoctorpatientview.EHospitalDoctorPatientView;
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
public class EHospitalDoctorPanel extends Application {
    
    private Parent root;
    private FXMLLoader loader;
    private eHospitalDoctorPanelController docPanel;
    private EHospitalDoctorPatientView patView;
    private EHospitalDoctorAppointment appoint;
    private EHospitalDoctorPatientTreatment patientTreat;
    private EHospitalReport report;
    private EHospitalDoctorDeathBirth deathBirth;
    private EHospitalDoctorProfile docProfile;
    
    @Override
    public void start(Stage stage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("eHospitalDoctorPanel.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }
    
    public EHospitalDoctorPanel(){
        try {
            patView=new EHospitalDoctorPatientView();
            appoint=new EHospitalDoctorAppointment();
            patientTreat=new EHospitalDoctorPatientTreatment();
            deathBirth=new EHospitalDoctorDeathBirth();
            report=new EHospitalReport();
            docProfile=new EHospitalDoctorProfile();
            
            loader=new FXMLLoader(getClass().getResource("eHospitalDoctorPanel.fxml"));
            root=loader.load();
            
            docPanel=loader.getController();
            
            docPanel.setRootPatientView(patView.getFxml());
            docPanel.setRootAppointment(appoint.getFxml());
            docPanel.setRootPatientTreatment(patientTreat.getFxml());
            docPanel.setRootDeathBirth(deathBirth.getFxml());
            docPanel.setRootReport(report.getFxml());
            docPanel.setRootProfile(docProfile.getFxml());
            
            docPanel.setAppoint(appoint);
            docPanel.setPatientTreat(patientTreat);
            docPanel.setDeathBirth(deathBirth);
            docPanel.setDocProfile(docProfile);
            
            docPanel.openPatientModule();
        } catch (Exception e) {
        }
    }
    
    public Parent getFxml(){
        return root;
    }
    
    public void setLoginRoot(Parent loginRoot){
        docPanel.setLoginRoot(loginRoot);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
