/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitallabtechpanel;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPopup;
import ehospitalbloodbank.EHospitalBloodBank;
import ehospitallabtechpatient.EHospitalLabTechPatient;
import ehospitallabtechprofile.EHospitalLabTechProfile;
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
public class EHospitalLabTechPanelController implements Initializable {

    @FXML
    private StackPane stackPane;
    @FXML
    private BorderPane borderPane;
    @FXML
    private JFXButton buttonTheme;
    private Parent rootPatient, loginRoot, rootBloodBank, rootReport, rootProfile;
    private EHospitalLabTechPatient pat;
    private EHospitalBloodBank blood;
    private EHospitalLabTechProfile profile;
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
                rootProfile.getStylesheets().setAll(getClass().getResource("blue.css").toExternalForm());
                rootBloodBank.getStylesheets().setAll(getClass().getResource("blue.css").toExternalForm());
                rootProfile.getStylesheets().setAll(getClass().getResource("blue.css").toExternalForm());
            } catch (Exception e) {
            }
        });

        green.setOnMouseClicked((MouseEvent event) -> {
            try {
                stackPane.getStylesheets().setAll(getClass().getResource("Green.css").toExternalForm());
                rootReport.getStylesheets().setAll(getClass().getResource("Green.css").toExternalForm());
                rootPatient.getStylesheets().setAll(getClass().getResource("Green.css").toExternalForm());
                rootProfile.getStylesheets().setAll(getClass().getResource("Green.css").toExternalForm());
                rootBloodBank.getStylesheets().setAll(getClass().getResource("Green.css").toExternalForm());
                rootProfile.getStylesheets().setAll(getClass().getResource("Green.css").toExternalForm());
            } catch (Exception e) {
            }
        });

        red.setOnMouseClicked((MouseEvent event) -> {
            try {
                stackPane.getStylesheets().setAll(getClass().getResource("Main.css").toExternalForm());
                rootReport.getStylesheets().setAll(getClass().getResource("Main.css").toExternalForm());
                rootPatient.getStylesheets().setAll(getClass().getResource("Main.css").toExternalForm());
                rootProfile.getStylesheets().setAll(getClass().getResource("Main.css").toExternalForm());
                rootBloodBank.getStylesheets().setAll(getClass().getResource("Main.css").toExternalForm());
                rootProfile.getStylesheets().setAll(getClass().getResource("Main.css").toExternalForm());
            } catch (Exception e) {
            }
        });

        orange.setOnMouseClicked((MouseEvent event) -> {
            try {
                stackPane.getStylesheets().setAll(getClass().getResource("Orange.css").toExternalForm());
                rootReport.getStylesheets().setAll(getClass().getResource("Orange.css").toExternalForm());
                rootPatient.getStylesheets().setAll(getClass().getResource("Orange.css").toExternalForm());
                rootProfile.getStylesheets().setAll(getClass().getResource("Orange.css").toExternalForm());
                rootBloodBank.getStylesheets().setAll(getClass().getResource("Orange.css").toExternalForm());
                rootProfile.getStylesheets().setAll(getClass().getResource("Orange.css").toExternalForm());
            } catch (Exception e) {
            }
        });

        purple.setOnMouseClicked((MouseEvent event) -> {
            try {
                stackPane.getStylesheets().setAll(getClass().getResource("Purple.css").toExternalForm());
                rootReport.getStylesheets().setAll(getClass().getResource("Purple.css").toExternalForm());
                rootPatient.getStylesheets().setAll(getClass().getResource("Purple.css").toExternalForm());
                rootProfile.getStylesheets().setAll(getClass().getResource("Purple.css").toExternalForm());
                rootBloodBank.getStylesheets().setAll(getClass().getResource("Purple.css").toExternalForm());
                rootProfile.getStylesheets().setAll(getClass().getResource("Purple.css").toExternalForm());
            } catch (Exception e) {
            }
        });
    }    

    public void setRootPatient(Parent rootPatient) {
        this.rootPatient = rootPatient;
    }

    public void setLoginRoot(Parent loginRoot) {
        this.loginRoot = loginRoot;
    }

    public void setRootBloodBank(Parent rootBloodBank) {
        this.rootBloodBank = rootBloodBank;
    }

    public void setRootReport(Parent rootReport) {
        this.rootReport = rootReport;
    }

    public void setRootProfile(Parent rootProfile) {
        this.rootProfile = rootProfile;
    }

    public void setPat(EHospitalLabTechPatient pat) {
        this.pat = pat;
    }

    public void setBlood(EHospitalBloodBank blood) {
        this.blood = blood;
    }

    public void setProfile(EHospitalLabTechProfile profile) {
        this.profile = profile;
    }
    

    @FXML
    public void openPatientModule() {
        borderPane.setCenter(rootPatient);
        pat.populateTable();
    }

    @FXML
    private void openBloodBankModule() {
        borderPane.setCenter(rootBloodBank);
        blood.populateTableView();
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
    private void openProfileModule() {
        borderPane.setCenter(rootProfile);
        profile.displayProfileDetails();
    }

    @FXML
    private void logOut() {
        stackPane.getScene().getWindow().setWidth(680);
        stackPane.getScene().getWindow().setHeight(400);
        stackPane.getScene().getWindow().centerOnScreen();
        stackPane.getScene().setRoot(loginRoot);
    }
    
}
