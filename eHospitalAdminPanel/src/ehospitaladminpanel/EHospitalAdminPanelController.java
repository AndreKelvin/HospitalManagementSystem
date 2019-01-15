/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitaladminpanel;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPopup;
import ehospitaladminprofile.EHospitalAdminProfile;
import ehospitalappointment.EHospitalAppointment;
import ehospitaldb.EHospitalDB;
import ehospitaldeathbirth.EHospitalDeathBirth;
import ehospitaldialog.EHospitalDialog;
import ehospitaldrugs.EHospitalDrug;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Andre Kelvin
 */
public class EHospitalAdminPanelController implements Initializable {

    @FXML
    private StackPane stackPane;
    @FXML
    private BorderPane borderPane;
    @FXML
    private JFXButton buttonTheme;
    @FXML
    private VBox vBoxDashBoard;
    @FXML
    private Label labelTotalPatient,labelTotalDoctor,labelTotalNurse,labelTotalPhar,labelTotalLabTech,labelTotalWard,labelTotalRoom,labelTotalBirth;
    @FXML
    private Label labelTotalDeath,labelTotalDept;
    private Parent rootDepartment, rootStaffTabPane, rootPatient, rootAppointment, rootBloodBank, rootBill, rootDrug, rootHospitalInfo, rootTreatmentTabPane;
    private Parent rootDeathBirthTabPane, rootWardRoomTabPane, rootReport, rootTreat, rootPatientTreat, rootDoctor, rootNurse, rootPharmacist, rootLabTech;
    private Parent rootAbout, rootProfile, loginRoot;
    private EHospitalDrug drug;
    private EHospitalAppointment app;
    private EHospitalAdminProfile profile;
    private EHospitalDeathBirth deathBirth;
    private JFXPopup popup;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        popup = new JFXPopup();

        Label blue = new Label("Blue");
        Label green = new Label("Green");
        Label red = new Label("Red");
        Label orange = new Label("Orange");
        Label purple = new Label("Purple");

        blue.setPadding(new Insets(10));
        green.setPadding(new Insets(10));
        red.setPadding(new Insets(10));
        orange.setPadding(new Insets(10));
        purple.setPadding(new Insets(10));

        VBox vBox = new VBox(blue, green, red, orange, purple);

        popup.setPopupContent(vBox);

        //Include pointing hand cursor to popup labels
        blue.getStylesheets().setAll(getClass().getResource("PopupLabel.css").toExternalForm());
        green.getStylesheets().setAll(getClass().getResource("PopupLabel.css").toExternalForm());
        red.getStylesheets().setAll(getClass().getResource("PopupLabel.css").toExternalForm());
        orange.getStylesheets().setAll(getClass().getResource("PopupLabel.css").toExternalForm());
        purple.getStylesheets().setAll(getClass().getResource("PopupLabel.css").toExternalForm());

        //Attach Image to label
        Image imageBlue = new Image(getClass().getResourceAsStream("icon/blue.png"));
        blue.setGraphic(new ImageView(imageBlue));

        Image imageGreen = new Image(getClass().getResourceAsStream("icon/green.jpg"));
        green.setGraphic(new ImageView(imageGreen));

        Image imageRed = new Image(getClass().getResourceAsStream("icon/redd.jpg"));
        red.setGraphic(new ImageView(imageRed));

        Image imageOrange = new Image(getClass().getResourceAsStream("icon/orange.png"));
        orange.setGraphic(new ImageView(imageOrange));

        Image imagePurple = new Image(getClass().getResourceAsStream("icon/purple.png"));
        purple.setGraphic(new ImageView(imagePurple));
        
        //Clicking on any popup Label. Changes the Theme
        blue.setOnMouseClicked((MouseEvent event) -> {
            try {
                stackPane.getStylesheets().setAll(getClass().getResource("blue.css").toExternalForm());
                rootReport.getStylesheets().setAll(getClass().getResource("blue.css").toExternalForm());
                rootPatient.getStylesheets().setAll(getClass().getResource("blue.css").toExternalForm());

                rootDeathBirthTabPane.getStylesheets().setAll(getClass().getResource("blue.css").toExternalForm());

                rootBloodBank.getStylesheets().setAll(getClass().getResource("blue.css").toExternalForm());

                rootWardRoomTabPane.getStylesheets().setAll(getClass().getResource("blue.css").toExternalForm());

                rootTreatmentTabPane.getStylesheets().setAll(getClass().getResource("blue.css").toExternalForm());
                rootTreat.getStylesheets().setAll(getClass().getResource("blue.css").toExternalForm());
                rootPatientTreat.getStylesheets().setAll(getClass().getResource("blue.css").toExternalForm());

                rootStaffTabPane.getStylesheets().setAll(getClass().getResource("blue.css").toExternalForm());
                rootDoctor.getStylesheets().setAll(getClass().getResource("blue.css").toExternalForm());
                rootNurse.getStylesheets().setAll(getClass().getResource("blue.css").toExternalForm());
                rootPharmacist.getStylesheets().setAll(getClass().getResource("blue.css").toExternalForm());
                rootLabTech.getStylesheets().setAll(getClass().getResource("blue.css").toExternalForm());

                rootDepartment.getStylesheets().setAll(getClass().getResource("blue.css").toExternalForm());
                rootBill.getStylesheets().setAll(getClass().getResource("blue.css").toExternalForm());
                rootDrug.getStylesheets().setAll(getClass().getResource("blue.css").toExternalForm());
                rootAppointment.getStylesheets().setAll(getClass().getResource("blue.css").toExternalForm());
                rootHospitalInfo.getStylesheets().setAll(getClass().getResource("blue.css").toExternalForm());
                
                rootProfile.getStylesheets().setAll(getClass().getResource("blue.css").toExternalForm());
            } catch (Exception e) {
            }
        });

        green.setOnMouseClicked((MouseEvent event) -> {
            try {
                stackPane.getStylesheets().setAll(getClass().getResource("Green.css").toExternalForm());
                rootReport.getStylesheets().setAll(getClass().getResource("Green.css").toExternalForm());
                rootPatient.getStylesheets().setAll(getClass().getResource("Green.css").toExternalForm());

                rootDeathBirthTabPane.getStylesheets().setAll(getClass().getResource("Green.css").toExternalForm());

                rootBloodBank.getStylesheets().setAll(getClass().getResource("Green.css").toExternalForm());

                rootWardRoomTabPane.getStylesheets().setAll(getClass().getResource("Green.css").toExternalForm());

                rootTreatmentTabPane.getStylesheets().setAll(getClass().getResource("Green.css").toExternalForm());
                rootTreat.getStylesheets().setAll(getClass().getResource("Green.css").toExternalForm());
                rootPatientTreat.getStylesheets().setAll(getClass().getResource("Green.css").toExternalForm());

                rootStaffTabPane.getStylesheets().setAll(getClass().getResource("Green.css").toExternalForm());
                rootDoctor.getStylesheets().setAll(getClass().getResource("Green.css").toExternalForm());
                rootNurse.getStylesheets().setAll(getClass().getResource("Green.css").toExternalForm());
                rootPharmacist.getStylesheets().setAll(getClass().getResource("Green.css").toExternalForm());
                rootLabTech.getStylesheets().setAll(getClass().getResource("Green.css").toExternalForm());

                rootDepartment.getStylesheets().setAll(getClass().getResource("Green.css").toExternalForm());
                rootBill.getStylesheets().setAll(getClass().getResource("Green.css").toExternalForm());
                rootDrug.getStylesheets().setAll(getClass().getResource("Green.css").toExternalForm());
                rootAppointment.getStylesheets().setAll(getClass().getResource("Green.css").toExternalForm());
                rootHospitalInfo.getStylesheets().setAll(getClass().getResource("Green.css").toExternalForm());
                
                rootProfile.getStylesheets().setAll(getClass().getResource("Green.css").toExternalForm());
            } catch (Exception e) {
            }
        });

        red.setOnMouseClicked((MouseEvent event) -> {
            try {
                stackPane.getStylesheets().setAll(getClass().getResource("Main.css").toExternalForm());
                rootReport.getStylesheets().setAll(getClass().getResource("Main.css").toExternalForm());
                rootPatient.getStylesheets().setAll(getClass().getResource("Main.css").toExternalForm());

                rootDeathBirthTabPane.getStylesheets().setAll(getClass().getResource("Main.css").toExternalForm());

                rootBloodBank.getStylesheets().setAll(getClass().getResource("Main.css").toExternalForm());

                rootWardRoomTabPane.getStylesheets().setAll(getClass().getResource("Main.css").toExternalForm());

                rootTreatmentTabPane.getStylesheets().setAll(getClass().getResource("Main.css").toExternalForm());
                rootTreat.getStylesheets().setAll(getClass().getResource("Main.css").toExternalForm());
                rootPatientTreat.getStylesheets().setAll(getClass().getResource("Main.css").toExternalForm());

                rootStaffTabPane.getStylesheets().setAll(getClass().getResource("Main.css").toExternalForm());
                rootDoctor.getStylesheets().setAll(getClass().getResource("Main.css").toExternalForm());
                rootNurse.getStylesheets().setAll(getClass().getResource("Main.css").toExternalForm());
                rootPharmacist.getStylesheets().setAll(getClass().getResource("Main.css").toExternalForm());
                rootLabTech.getStylesheets().setAll(getClass().getResource("Main.css").toExternalForm());

                rootDepartment.getStylesheets().setAll(getClass().getResource("Main.css").toExternalForm());
                rootBill.getStylesheets().setAll(getClass().getResource("Main.css").toExternalForm());
                rootDrug.getStylesheets().setAll(getClass().getResource("Main.css").toExternalForm());
                rootAppointment.getStylesheets().setAll(getClass().getResource("Main.css").toExternalForm());
                rootHospitalInfo.getStylesheets().setAll(getClass().getResource("Main.css").toExternalForm());
                
                rootProfile.getStylesheets().setAll(getClass().getResource("Main.css").toExternalForm());
            } catch (Exception e) {
            }
        });

        orange.setOnMouseClicked((MouseEvent event) -> {
            try {
                stackPane.getStylesheets().setAll(getClass().getResource("Orange.css").toExternalForm());
                rootReport.getStylesheets().setAll(getClass().getResource("Orange.css").toExternalForm());
                rootPatient.getStylesheets().setAll(getClass().getResource("Orange.css").toExternalForm());

                rootDeathBirthTabPane.getStylesheets().setAll(getClass().getResource("Orange.css").toExternalForm());

                rootBloodBank.getStylesheets().setAll(getClass().getResource("Orange.css").toExternalForm());

                rootWardRoomTabPane.getStylesheets().setAll(getClass().getResource("Orange.css").toExternalForm());

                rootTreatmentTabPane.getStylesheets().setAll(getClass().getResource("Orange.css").toExternalForm());
                rootTreat.getStylesheets().setAll(getClass().getResource("Orange.css").toExternalForm());
                rootPatientTreat.getStylesheets().setAll(getClass().getResource("Orange.css").toExternalForm());

                rootStaffTabPane.getStylesheets().setAll(getClass().getResource("Orange.css").toExternalForm());
                rootDoctor.getStylesheets().setAll(getClass().getResource("Orange.css").toExternalForm());
                rootNurse.getStylesheets().setAll(getClass().getResource("Orange.css").toExternalForm());
                rootPharmacist.getStylesheets().setAll(getClass().getResource("Orange.css").toExternalForm());
                rootLabTech.getStylesheets().setAll(getClass().getResource("Orange.css").toExternalForm());

                rootDepartment.getStylesheets().setAll(getClass().getResource("Orange.css").toExternalForm());
                rootBill.getStylesheets().setAll(getClass().getResource("Orange.css").toExternalForm());
                rootDrug.getStylesheets().setAll(getClass().getResource("Orange.css").toExternalForm());
                rootAppointment.getStylesheets().setAll(getClass().getResource("Orange.css").toExternalForm());
                rootHospitalInfo.getStylesheets().setAll(getClass().getResource("Orange.css").toExternalForm());
                
                rootProfile.getStylesheets().setAll(getClass().getResource("Orange.css").toExternalForm());
            } catch (Exception e) {
            }
        });

        purple.setOnMouseClicked((MouseEvent event) -> {
            try {
                stackPane.getStylesheets().setAll(getClass().getResource("Purple.css").toExternalForm());
                rootReport.getStylesheets().setAll(getClass().getResource("Purple.css").toExternalForm());
                rootPatient.getStylesheets().setAll(getClass().getResource("Purple.css").toExternalForm());

                rootDeathBirthTabPane.getStylesheets().setAll(getClass().getResource("Purple.css").toExternalForm());

                rootBloodBank.getStylesheets().setAll(getClass().getResource("Purple.css").toExternalForm());

                rootWardRoomTabPane.getStylesheets().setAll(getClass().getResource("Purple.css").toExternalForm());

                rootTreatmentTabPane.getStylesheets().setAll(getClass().getResource("Purple.css").toExternalForm());
                rootTreat.getStylesheets().setAll(getClass().getResource("Purple.css").toExternalForm());
                rootPatientTreat.getStylesheets().setAll(getClass().getResource("Purple.css").toExternalForm());

                rootStaffTabPane.getStylesheets().setAll(getClass().getResource("Purple.css").toExternalForm());
                rootDoctor.getStylesheets().setAll(getClass().getResource("Purple.css").toExternalForm());
                rootNurse.getStylesheets().setAll(getClass().getResource("Purple.css").toExternalForm());
                rootPharmacist.getStylesheets().setAll(getClass().getResource("Purple.css").toExternalForm());
                rootLabTech.getStylesheets().setAll(getClass().getResource("Purple.css").toExternalForm());

                rootDepartment.getStylesheets().setAll(getClass().getResource("Purple.css").toExternalForm());
                rootBill.getStylesheets().setAll(getClass().getResource("Purple.css").toExternalForm());
                rootDrug.getStylesheets().setAll(getClass().getResource("Purple.css").toExternalForm());
                rootAppointment.getStylesheets().setAll(getClass().getResource("Purple.css").toExternalForm());
                rootHospitalInfo.getStylesheets().setAll(getClass().getResource("Purple.css").toExternalForm());
                
                rootProfile.getStylesheets().setAll(getClass().getResource("Purple.css").toExternalForm());
            } catch (Exception e) {
            }
        });
        
        openDashboard();
    }    

    @FXML
    private void openDashboard() {
        borderPane.setCenter(vBoxDashBoard);
        try {
            PreparedStatement ps = EHospitalDB.getCon().prepareStatement("SELECT Count(Patient_Name) FROM PATIENT where CATEGORY=? "
                    + "Union All "
                    + "SELECT Count(Name) FROM DOCTOR "
                    + "Union All "
                    + "SELECT Count(Name) FROM NURSE "
                    + "Union All "
                    + "SELECT Count(Name) FROM LABTECHNICIAN "
                    + "Union All "
                    + "SELECT Count(Name) FROM PHARMACIST "
                    + "Union All "
                    + "SELECT Count(Ward) FROM WARD "
                    + "Union All "
                    + "SELECT Sum(Room) FROM ROOM "
                    + "Union All "
                    + "SELECT Count(Baby) FROM BIRTH "
                    + "Union All "
                    + "SELECT Count(Name) FROM DEATH "
                    + "Union All "
                    + "SELECT Count(Name) FROM DEPARTMENT");
            ps.setString(1, "In Patient");
            ResultSet rs = ps.executeQuery();

            rs.next();
            labelTotalPatient.setText(rs.getString(1));
            rs.next();
            labelTotalDoctor.setText(rs.getString(1));
            rs.next();
            labelTotalNurse.setText(rs.getString(1));
            rs.next();
            labelTotalLabTech.setText(rs.getString(1));
            rs.next();
            labelTotalPhar.setText(rs.getString(1));
            rs.next();
            labelTotalWard.setText(rs.getString(1));
            rs.next();
            labelTotalRoom.setText(rs.getString(1));
            rs.next();
            labelTotalBirth.setText(rs.getString(1));
            rs.next();
            labelTotalDeath.setText(rs.getString(1));
            rs.next();
            labelTotalDept.setText(rs.getString(1));
        } catch (Exception e) {
        }
    }
    
    public void setLoginRoot(Parent loginRoot) {
        this.loginRoot = loginRoot;
    }

    public void setRootDepartment(Parent rootDepartment) {
        this.rootDepartment = rootDepartment;
    }

    public void setRootStaffTabPane(Parent rootStaffTabPane) {
        this.rootStaffTabPane = rootStaffTabPane;
    }

    public void setRootPatient(Parent rootPatient) {
        this.rootPatient = rootPatient;
    }

    public void setRootAppointment(Parent rootAppointment) {
        this.rootAppointment = rootAppointment;
    }

    public void setRootBloodBank(Parent rootBloodBank) {
        this.rootBloodBank = rootBloodBank;
    }

    public void setRootBill(Parent rootBill) {
        this.rootBill = rootBill;
    }

    public void setRootDrug(Parent rootDrug) {
        this.rootDrug = rootDrug;
    }

    public void setRootHospitalInfo(Parent rootHospitalInfo) {
        this.rootHospitalInfo = rootHospitalInfo;
    }

    public void setRootTreatmentTabPane(Parent rootTreatmentTabPane) {
        this.rootTreatmentTabPane = rootTreatmentTabPane;
    }

    public void setRootDeathBirthTabPane(Parent rootDeathBirthTabPane) {
        this.rootDeathBirthTabPane = rootDeathBirthTabPane;
    }

    public void setRootWardRoomTabPane(Parent rootWardRoomTabPane) {
        this.rootWardRoomTabPane = rootWardRoomTabPane;
    }

    public void setRootReport(Parent rootReport) {
        this.rootReport = rootReport;
    }

    public void setRootTreat(Parent rootTreat) {
        this.rootTreat = rootTreat;
    }

    public void setRootPatientTreat(Parent rootPatientTreat) {
        this.rootPatientTreat = rootPatientTreat;
    }

    public void setRootDoctor(Parent rootDoctor) {
        this.rootDoctor = rootDoctor;
    }

    public void setRootNurse(Parent rootNurse) {
        this.rootNurse = rootNurse;
    }

    public void setRootPharmacist(Parent rootPharmacist) {
        this.rootPharmacist = rootPharmacist;
    }

    public void setRootLabTech(Parent rootLabTech) {
        this.rootLabTech = rootLabTech;
    }

    public void setRootProfile(Parent rootProfile) {
        this.rootProfile = rootProfile;
    }

    public void setDrug(EHospitalDrug drug) {
        this.drug = drug;
    }

    public void setApp(EHospitalAppointment app) {
        this.app = app;
    }
    
    public void setProfile(EHospitalAdminProfile profile) {
        this.profile = profile;
    }

    public void setDeathBirth(EHospitalDeathBirth deathBirth) {
        this.deathBirth = deathBirth;
    }

    @FXML
    private void openPatientModule() {
        borderPane.setCenter(rootPatient);
    }

    @FXML
    private void openDrugModule() {
        borderPane.setCenter(rootDrug);
        drug.populateTableAndDrugSearch();
    }

    @FXML
    private void openAppointmentModule() {
        borderPane.setCenter(rootAppointment);
        app.populateTableView();
    }

    @FXML
    private void openDeathBirthModule() {
        borderPane.setCenter(rootDeathBirthTabPane);
        deathBirth.populateListView();
        deathBirth.sreachBabyAndDeadPatient();
    }

    @FXML
    private void openBillModule() {
        borderPane.setCenter(rootBill);
        
    }

    @FXML
    private void openStaffModule() {
        borderPane.setCenter(rootStaffTabPane);
    }

    @FXML
    private void openReportModule() {
        borderPane.setCenter(rootReport);
    }

    @FXML
    private void openThemeModule() {
        popup.show(buttonTheme, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.RIGHT);
    }

    @FXML
    private void openWardRoomModule() {
        borderPane.setCenter(rootWardRoomTabPane);
    }

    @FXML
    private void openDepartmentModule() {
        borderPane.setCenter(rootDepartment);
    }

    @FXML
    private void openBloodBankModule() {
        borderPane.setCenter(rootBloodBank);
    }

    @FXML
    private void openTreatmentModule() {
        borderPane.setCenter(rootTreatmentTabPane);
    }

    @FXML
    private void openHospitalInfoModule() {
        borderPane.setCenter(rootHospitalInfo);
    }

    @FXML
    private void openProfileModule() {
        borderPane.setCenter(rootProfile);
        profile.displayProfileDetails();
    }

    @FXML
    private void openAboutModule() {
        try {
            if (rootAbout == null) {
                rootAbout = FXMLLoader.load(getClass().getResource("HospitalAbout.fxml"));
            }
            EHospitalDialog.showDialog(stackPane, rootAbout);
        } catch (Exception e) {
        }
    }

    @FXML
    private void logoOut() {
        stackPane.getScene().getWindow().setWidth(680);
        stackPane.getScene().getWindow().setHeight(400);
        stackPane.getScene().getWindow().centerOnScreen();
        stackPane.getScene().setRoot(loginRoot);
    }
    
}
