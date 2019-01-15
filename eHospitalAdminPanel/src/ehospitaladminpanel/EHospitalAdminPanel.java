/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitaladminpanel;

import ehospitaladminprofile.EHospitalAdminProfile;
import ehospitalappointment.EHospitalAppointment;
import ehospitalbill.EHospitalBill;
import ehospitalbloodbank.EHospitalBloodBank;
import ehospitaldeathbirth.EHospitalDeathBirth;
import ehospitaldepartment.EHospitalDepartment;
import ehospitaldrugs.EHospitalDrug;
import ehospitalinfo.EHospitalInfo;
import ehospitalpatient.EHospitalPatient;
import ehospitalreport.EHospitalReport;
import ehospitalstafftabpane.EHospitalStaffTabPane;
import ehospitaltreatmenttabpane.EHospitalTreatmentTabPane;
import ehospitalwardroomtabpane.EHospitalWardRoomTabPane;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Andre Kelvin
 */
public class EHospitalAdminPanel extends Application {

    private Parent root;
    private FXMLLoader loader;
    private EHospitalAdminPanelController adminPanel;

    @Override
    public void start(Stage stage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("EHospitalAdminPanel.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    public EHospitalAdminPanel() {
        try {
            EHospitalDeathBirth deathBirthTab = new EHospitalDeathBirth();
            EHospitalStaffTabPane staffTabPane = new EHospitalStaffTabPane();
            EHospitalDepartment department = new EHospitalDepartment();
            EHospitalBill bill = new EHospitalBill();
            EHospitalAppointment app = new EHospitalAppointment();
            EHospitalBloodBank blood = new EHospitalBloodBank();
            EHospitalDrug drug = new EHospitalDrug();
            EHospitalTreatmentTabPane treat = new EHospitalTreatmentTabPane();
            EHospitalPatient patient = new EHospitalPatient();
            EHospitalInfo info = new EHospitalInfo();
            EHospitalWardRoomTabPane wardRoomTabPane = new EHospitalWardRoomTabPane();
            EHospitalReport report = new EHospitalReport();
            EHospitalAdminProfile profile = new EHospitalAdminProfile();

            loader = new FXMLLoader(getClass().getResource("EHospitalAdminPanel.fxml"));
            root = loader.load();

            adminPanel = loader.getController();

            adminPanel.setRootStaffTabPane(staffTabPane.getFxml());
            adminPanel.setRootDepartment(department.getFxml());
            adminPanel.setRootBill(bill.getFxml());
            adminPanel.setRootAppointment(app.getFxml());
            adminPanel.setRootBloodBank(blood.getFxml());
            adminPanel.setRootPatient(patient.getFxml());
            adminPanel.setRootDrug(drug.getFxml());
            adminPanel.setRootTreatmentTabPane(treat.getFxml());
            adminPanel.setRootHospitalInfo(info.getFxml());
            adminPanel.setRootDeathBirthTabPane(deathBirthTab.getFxml());
            adminPanel.setRootWardRoomTabPane(wardRoomTabPane.getFxml());
            adminPanel.setRootReport(report.getFxml());
            adminPanel.setRootProfile(profile.getFxml());

            adminPanel.setDrug(drug);
            adminPanel.setApp(app);
            adminPanel.setProfile(profile);
            adminPanel.setDeathBirth(deathBirthTab);
            adminPanel.setProfile(profile);

            //For changing the Theme of Modules That's displayed in TabPanes
            adminPanel.setRootTreat(treat.getStackPaneTreat());
            adminPanel.setRootPatientTreat(treat.getStackPanePatientTreat());

            adminPanel.setRootDoctor(staffTabPane.getStackPaneDoc());
            adminPanel.setRootNurse(staffTabPane.getStackPaneNurse());
            adminPanel.setRootLabTech(staffTabPane.getStackPaneLabTech());
            adminPanel.setRootPharmacist(staffTabPane.getStackPanePhar());
        } catch (Exception e) {
        }
    }

    public Parent getFxml() {
        return root;
    }

    public void setLoginRoot(Parent loginRoot) {
        adminPanel.setLoginRoot(loginRoot);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
