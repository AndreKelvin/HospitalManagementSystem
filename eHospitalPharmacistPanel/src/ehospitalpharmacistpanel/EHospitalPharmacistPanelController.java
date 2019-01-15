/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitalpharmacistpanel;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPopup;
import ehospitaldrugs.EHospitalDrug;
import ehospitalpatienttreatment.EHospitalPatientTreatment;
import ehospitalpharmacistprofile.EHospitalPharmacistProfile;
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
public class EHospitalPharmacistPanelController implements Initializable {
    
    @FXML
    private StackPane stackPane;
    @FXML
    private BorderPane borderPane;
    @FXML
    private JFXButton buttonTheme;
    private Parent rootDrug, loginRoot, rootPatientTreatment, rootReport, rootProfile;
    private EHospitalDrug drug;
    private EHospitalPatientTreatment patientTreatment;
    private EHospitalPharmacistProfile profile;
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
                rootDrug.getStylesheets().setAll(getClass().getResource("blue.css").toExternalForm());
                rootPatientTreatment.getStylesheets().setAll(getClass().getResource("blue.css").toExternalForm());
                rootProfile.getStylesheets().setAll(getClass().getResource("blue.css").toExternalForm());
            } catch (Exception e) {
            }
        });

        green.setOnMouseClicked((MouseEvent event) -> {
            try {
                stackPane.getStylesheets().setAll(getClass().getResource("Green.css").toExternalForm());
                rootReport.getStylesheets().setAll(getClass().getResource("Green.css").toExternalForm());
                rootDrug.getStylesheets().setAll(getClass().getResource("Green.css").toExternalForm());
                rootPatientTreatment.getStylesheets().setAll(getClass().getResource("Green.css").toExternalForm());
                rootProfile.getStylesheets().setAll(getClass().getResource("Green.css").toExternalForm());
            } catch (Exception e) {
            }
        });

        red.setOnMouseClicked((MouseEvent event) -> {
            try {
                stackPane.getStylesheets().setAll(getClass().getResource("Main.css").toExternalForm());
                rootReport.getStylesheets().setAll(getClass().getResource("Main.css").toExternalForm());
                rootDrug.getStylesheets().setAll(getClass().getResource("Main.css").toExternalForm());
                rootPatientTreatment.getStylesheets().setAll(getClass().getResource("Main.css").toExternalForm());
                rootProfile.getStylesheets().setAll(getClass().getResource("Main.css").toExternalForm());
            } catch (Exception e) {
            }
        });

        orange.setOnMouseClicked((MouseEvent event) -> {
            try {
                stackPane.getStylesheets().setAll(getClass().getResource("Orange.css").toExternalForm());
                rootReport.getStylesheets().setAll(getClass().getResource("Orange.css").toExternalForm());
                rootDrug.getStylesheets().setAll(getClass().getResource("Orange.css").toExternalForm());
                rootPatientTreatment.getStylesheets().setAll(getClass().getResource("Orange.css").toExternalForm());
                rootProfile.getStylesheets().setAll(getClass().getResource("Orange.css").toExternalForm());
            } catch (Exception e) {
            }
        });

        purple.setOnMouseClicked((MouseEvent event) -> {
            try {
                stackPane.getStylesheets().setAll(getClass().getResource("Purple.css").toExternalForm());
                rootReport.getStylesheets().setAll(getClass().getResource("Purple.css").toExternalForm());
                rootDrug.getStylesheets().setAll(getClass().getResource("Purple.css").toExternalForm());
                rootPatientTreatment.getStylesheets().setAll(getClass().getResource("Purple.css").toExternalForm());
                rootProfile.getStylesheets().setAll(getClass().getResource("Purple.css").toExternalForm());
            } catch (Exception e) {
            }
        });
    }    

    public void setRootDrug(Parent rootDrug) {
        this.rootDrug = rootDrug;
    }

    public void setLoginRoot(Parent loginRoot) {
        this.loginRoot = loginRoot;
    }

    public void setRootPatientTreatment(Parent rootPatientTreatment) {
        this.rootPatientTreatment = rootPatientTreatment;
    }

    public void setRootReport(Parent rootReport) {
        this.rootReport = rootReport;
    }

    public void setRootProfile(Parent rootProfile) {
        this.rootProfile = rootProfile;
    }

    public void setDrug(EHospitalDrug drug) {
        this.drug = drug;
    }  

    public void setPatientTreatment(EHospitalPatientTreatment patientTreatment) {
        this.patientTreatment = patientTreatment;
    }

    public void setProfile(EHospitalPharmacistProfile profile) {
        this.profile = profile;
    }
    
    @FXML
    private void logOut() {
        stackPane.getScene().getWindow().setWidth(680);
        stackPane.getScene().getWindow().setHeight(400);
        stackPane.getScene().getWindow().centerOnScreen();
        stackPane.getScene().setRoot(loginRoot);
    }
    
    @FXML
    public void openDrugModule() {
        borderPane.setCenter(rootDrug);
        drug.populateTableAndDrugSearch();
    }

    @FXML
    private void openPatientTreatmentModule() {
        borderPane.setCenter(rootPatientTreatment);
        patientTreatment.populateTable();
    }

    @FXML
    private void openProfileModule() {
        borderPane.setCenter(rootProfile);
        profile.displayProfileDetails();
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
