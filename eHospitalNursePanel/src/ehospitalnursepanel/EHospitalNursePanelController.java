/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitalnursepanel;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPopup;
import ehospitalnursebloodbank.EHospitalNurseBloodBank;
import ehospitalnursedeathbirth.EHospitalNurseDeathBirth;
import ehospitalnursepatienttreatment.EHospitalNursePatientTreatment;
import ehospitalnurseprofile.EHospitalNurseProfile;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
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
public class EHospitalNursePanelController implements Initializable {

    @FXML
    private StackPane stackPane;
    @FXML
    private BorderPane borderPane;
    @FXML
    private JFXButton buttonTheme;
    private Parent rootPatientView, loginRoot, rootBloodBank, rootPatientTreatment, rootReport, rootDeathBirth, rootProfile;
    private EHospitalNurseBloodBank bloodBank;
    private EHospitalNurseDeathBirth deathBirth;
    private EHospitalNursePatientTreatment treat;
    private EHospitalNurseProfile nurseProfile;
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
                rootPatientView.getStylesheets().setAll(getClass().getResource("blue.css").toExternalForm());
                rootPatientTreatment.getStylesheets().setAll(getClass().getResource("blue.css").toExternalForm());
                rootBloodBank.getStylesheets().setAll(getClass().getResource("blue.css").toExternalForm());
                rootProfile.getStylesheets().setAll(getClass().getResource("blue.css").toExternalForm());
                rootDeathBirth.getStylesheets().setAll(getClass().getResource("blue.css").toExternalForm());
            } catch (Exception e) {
            }
        });

        green.setOnMouseClicked((MouseEvent event) -> {
            try {
                stackPane.getStylesheets().setAll(getClass().getResource("Green.css").toExternalForm());
                rootReport.getStylesheets().setAll(getClass().getResource("Green.css").toExternalForm());
                rootPatientView.getStylesheets().setAll(getClass().getResource("Green.css").toExternalForm());
                rootPatientTreatment.getStylesheets().setAll(getClass().getResource("Green.css").toExternalForm());
                rootBloodBank.getStylesheets().setAll(getClass().getResource("Green.css").toExternalForm());
                rootProfile.getStylesheets().setAll(getClass().getResource("Green.css").toExternalForm());
                rootDeathBirth.getStylesheets().setAll(getClass().getResource("Green.css").toExternalForm());
            } catch (Exception e) {
            }
        });

        red.setOnMouseClicked((MouseEvent event) -> {
            try {
                stackPane.getStylesheets().setAll(getClass().getResource("Main.css").toExternalForm());
                rootReport.getStylesheets().setAll(getClass().getResource("Main.css").toExternalForm());
                rootPatientView.getStylesheets().setAll(getClass().getResource("Main.css").toExternalForm());
                rootPatientTreatment.getStylesheets().setAll(getClass().getResource("Main.css").toExternalForm());
                rootBloodBank.getStylesheets().setAll(getClass().getResource("Main.css").toExternalForm());
                rootProfile.getStylesheets().setAll(getClass().getResource("Main.css").toExternalForm());
                rootDeathBirth.getStylesheets().setAll(getClass().getResource("Main.css").toExternalForm());
            } catch (Exception e) {
            }
        });

        orange.setOnMouseClicked((MouseEvent event) -> {
            try {
                stackPane.getStylesheets().setAll(getClass().getResource("Orange.css").toExternalForm());
                rootReport.getStylesheets().setAll(getClass().getResource("Orange.css").toExternalForm());
                rootPatientView.getStylesheets().setAll(getClass().getResource("Orange.css").toExternalForm());
                rootPatientTreatment.getStylesheets().setAll(getClass().getResource("Orange.css").toExternalForm());
                rootBloodBank.getStylesheets().setAll(getClass().getResource("Orange.css").toExternalForm());
                rootProfile.getStylesheets().setAll(getClass().getResource("Orange.css").toExternalForm());
                rootDeathBirth.getStylesheets().setAll(getClass().getResource("Orange.css").toExternalForm());
            } catch (Exception e) {
            }
        });

        purple.setOnMouseClicked((MouseEvent event) -> {
            try {
                stackPane.getStylesheets().setAll(getClass().getResource("Purple.css").toExternalForm());
                rootReport.getStylesheets().setAll(getClass().getResource("Purple.css").toExternalForm());
                rootPatientView.getStylesheets().setAll(getClass().getResource("Purple.css").toExternalForm());
                rootPatientTreatment.getStylesheets().setAll(getClass().getResource("Purple.css").toExternalForm());
                rootBloodBank.getStylesheets().setAll(getClass().getResource("Purple.css").toExternalForm());
                rootProfile.getStylesheets().setAll(getClass().getResource("Purple.css").toExternalForm());
                rootDeathBirth.getStylesheets().setAll(getClass().getResource("Purple.css").toExternalForm());
            } catch (Exception e) {
            }
        });
    }

    public void setRootPatientView(Parent rootPatientView) {
        this.rootPatientView = rootPatientView;
    }

    public void setLoginRoot(Parent loginRoot) {
        this.loginRoot = loginRoot;
    }

    public void setRootBloodBank(Parent rootBloodBank) {
        this.rootBloodBank = rootBloodBank;
    }

    public void setRootPatientTreatment(Parent rootPatientTreatment) {
        this.rootPatientTreatment = rootPatientTreatment;
    }

    public void setRootReport(Parent rootReport) {
        this.rootReport = rootReport;
    }

    public void setRootDeathBirth(Parent rootDeathBirth) {
        this.rootDeathBirth = rootDeathBirth;
    }

    public void setRootProfile(Parent rootProfile) {
        this.rootProfile = rootProfile;
    }

    public void setBloodBank(EHospitalNurseBloodBank bloodBank) {
        this.bloodBank = bloodBank;
    }

    public void setDeathBirth(EHospitalNurseDeathBirth deathBirth) {
        this.deathBirth = deathBirth;
    }

    public void setTreat(EHospitalNursePatientTreatment treat) {
        this.treat = treat;
    }
    
    public void setNurseProfile(EHospitalNurseProfile nurseProfile) {
        this.nurseProfile = nurseProfile;
    }

    @FXML
    private void logOut() {
        stackPane.getScene().getWindow().setWidth(680);
        stackPane.getScene().getWindow().setHeight(400);
        stackPane.getScene().getWindow().centerOnScreen();
        stackPane.getScene().setRoot(loginRoot);
    }

    @FXML
    private void openBloodBankModule() {
        borderPane.setCenter(rootBloodBank);
        bloodBank.populateTableView();
    }

    @FXML
    private void openDeathBirthModule() {
        borderPane.setCenter(rootDeathBirth);
        deathBirth.populateListView();
    }

    @FXML
    public void openPatientModule() {
        borderPane.setCenter(rootPatientView);
    }

    @FXML
    private void openPatientTreatmentModule() {
        borderPane.setCenter(rootPatientTreatment);
        treat.populateTable();
    }

    @FXML
    private void openProfileModule() {
        borderPane.setCenter(rootProfile);
        nurseProfile.displayProfileDetails();
    }

    @FXML
    private void openReportModule() {
        borderPane.setCenter(rootReport);
    }

    @FXML
    private void openThemeModule() {
        popup.show(buttonTheme, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.RIGHT);
    }

}
