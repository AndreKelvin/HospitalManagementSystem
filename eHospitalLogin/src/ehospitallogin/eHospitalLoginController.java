/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitallogin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import ehospitaladminpanel.EHospitalAdminPanel;
import ehospitalbridge.EHospitalBridge;
import ehospitaldb.EHospitalDB;
import ehospitaldialog.EHospitalDialog;
import ehospitaldoctorpanel.EHospitalDoctorPanel;
import ehospitallabtechpanel.EHospitalLabTechPanel;
import ehospitalnursepanel.EHospitalNursePanel;
import ehospitalpharmacistpanel.EHospitalPharmacistPanel;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Andre Kelvin
 */
public class eHospitalLoginController implements Initializable {

    @FXML
    private StackPane stackPane;
    @FXML
    private JFXSpinner spinner;
    @FXML
    private VBox vBox;
    @FXML
    private JFXComboBox comboBox;
    @FXML
    private JFXTextField textUsername, textUnMaskPassword;
    @FXML
    private JFXPasswordField textPassword;
    @FXML
    private JFXCheckBox checkBox;
    @FXML
    private JFXButton buttonLogin;
    private Parent rootCreateAccount;
    private FXMLLoader loaderCreateAccount;
    private CreateAccountController create;
    private PreparedStatement ps;
    private ResultSet rs;
    private EHospitalDoctorPanel doctorPanel;
    private EHospitalNursePanel nursePanel;
    private EHospitalPharmacistPanel pharmacistPanel;
    private EHospitalAdminPanel adminPanel;
    private EHospitalLabTechPanel labPanel;
    private String username, password;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // Bind properties. textField and passwordField
            // visibility and managability properties mutually when checkbox's state is changed.
            // Because i want to display only one component (textField or passwordField)
            // on the scene at a time.
            textUnMaskPassword.managedProperty().bind(checkBox.selectedProperty());
            textUnMaskPassword.visibleProperty().bind(checkBox.selectedProperty());

            textPassword.managedProperty().bind(checkBox.selectedProperty().not());
            textPassword.visibleProperty().bind(checkBox.selectedProperty().not());

            buttonLogin.setDisable(true);

            comboBox.getItems().addAll("Admin", "Doctor", "Nurse", "Pharmacist", "Lab Technician");

            comboBox.getSelectionModel().selectedItemProperty().addListener((ObservableValue observable, Object oldValue, Object newValue) -> {
                buttonLogin.setDisable(false);
            });
        } catch (Exception e) {
        }
    }

    private class LoginTask extends Task<Object> {

        @Override
        protected Object call() throws Exception {

            try {
                ps = EHospitalDB.getCon().prepareStatement("Select * from HOSPITALLOGIN Where Login_As=?");
                ps.setString(1, comboBox.getValue().toString());
                rs = ps.executeQuery();

                while (rs.next()) {

                    if (rs.getString("UserName").contentEquals(username) && rs.getString("PASSWORD").contentEquals(password) && rs.getString("Login_As").contentEquals("Admin")) {
                        //open admin panel
                        if (adminPanel == null) {
                            adminPanel = new EHospitalAdminPanel();
                            new File(System.getProperty("user.home")+File.separator+"AdminImage").mkdirs();
                        }
                        adminPanel.setLoginRoot(stackPane);
                        getLoginUser();

                        stackPane.getScene().getWindow().setWidth(1320);
                        stackPane.getScene().getWindow().setHeight(700);

                        Platform.runLater(() -> {
                            stackPane.getScene().getWindow().centerOnScreen();
                            stackPane.getScene().setRoot(adminPanel.getFxml());
                        });
                    } else if (rs.getString("UserName").contentEquals(username) && rs.getString("PASSWORD").contentEquals(password) && rs.getString("Login_As").contentEquals("Doctor")) {
                        //open doctor panel
                        if (doctorPanel == null) {
                            doctorPanel = new EHospitalDoctorPanel();
                        }
                        doctorPanel.setLoginRoot(stackPane);
                        getLoginUser();

                        stackPane.getScene().getWindow().setWidth(1320);
                        stackPane.getScene().getWindow().setHeight(700);

                        Platform.runLater(() -> {
                            stackPane.getScene().getWindow().centerOnScreen();
                            stackPane.getScene().setRoot(doctorPanel.getFxml());
                        });
                    } else if (rs.getString("UserName").contentEquals(username) && rs.getString("PASSWORD").contentEquals(password) && rs.getString("Login_As").contentEquals("Nurse")) {
                        //open nurse panel
                        if (nursePanel == null) {
                            nursePanel = new EHospitalNursePanel();
                        }
                        nursePanel.setLoginRoot(stackPane);
                        getLoginUser();

                        stackPane.getScene().getWindow().setWidth(1320);
                        stackPane.getScene().getWindow().setHeight(700);

                        Platform.runLater(() -> {
                            stackPane.getScene().getWindow().centerOnScreen();
                            stackPane.getScene().setRoot(nursePanel.getFxml());
                        });
                    } else if (rs.getString("UserName").contentEquals(username) && rs.getString("PASSWORD").contentEquals(password) && rs.getString("Login_As").contentEquals("Pharmacist")) {
                        //open pharmacist panel
                        if (pharmacistPanel == null) {
                            pharmacistPanel = new EHospitalPharmacistPanel();
                        }
                        pharmacistPanel.setLoginRoot(stackPane);
                        getLoginUser();

                        stackPane.getScene().getWindow().setWidth(1320);
                        stackPane.getScene().getWindow().setHeight(700);

                        Platform.runLater(() -> {
                            stackPane.getScene().getWindow().centerOnScreen();
                            stackPane.getScene().setRoot(pharmacistPanel.getFxml());
                        });
                    } else if (rs.getString("UserName").contentEquals(username) && rs.getString("PASSWORD").contentEquals(password) && rs.getString("Login_As").contentEquals("Lab Technician")) {
                        //open lab technician panel
                        if (labPanel == null) {
                            labPanel = new EHospitalLabTechPanel();
                        }
                        labPanel.setLoginRoot(stackPane);
                        getLoginUser();

                        stackPane.getScene().getWindow().setWidth(1320);
                        stackPane.getScene().getWindow().setHeight(700);

                        Platform.runLater(() -> {
                            stackPane.getScene().getWindow().centerOnScreen();
                            stackPane.getScene().setRoot(labPanel.getFxml());
                        });
                    }

                }
            } catch (Exception e) {
                vBox.setDisable(false);
                spinner.setVisible(false);
                //e.printStackTrace();
                Platform.runLater(() -> {
                    EHospitalDialog.dialogAlert(Alert.AlertType.ERROR, "Database connection failure", textUsername.getScene().getWindow());
                });
            }

            return null;
        }

    }

    @FXML
    private void login() {
        try {
            username = textUsername.getText().trim();
            password = textPassword.getText().trim();

            ps = EHospitalDB.getCon().prepareStatement("Select USERNAME,PASSWORD From HOSPITALLOGIN Where USERNAME=? and PASSWORD=?");
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();

            if (rs.next()) {
                if (username.isEmpty() || password.isEmpty()) {
                    EHospitalDialog.dialogAlert(Alert.AlertType.ERROR, "Invalid Input", textUsername.getScene().getWindow());
                } else {
                    vBox.setDisable(true);
                    spinner.setVisible(true);

                    LoginTask loginTask = new LoginTask();
                    loginTask.setOnSucceeded((WorkerStateEvent event) -> {
                        vBox.setDisable(false);
                        spinner.setVisible(false);
                        textPassword.clear();
                    });
                    new Thread(loginTask).start();
                }
            } else {
                EHospitalDialog.dialogAlert(Alert.AlertType.ERROR, "Invalid Username/Password", textUsername.getScene().getWindow());
            }
        } catch (Exception e) {
            EHospitalDialog.dialogAlert(Alert.AlertType.ERROR, "Database connection failure", textUsername.getScene().getWindow());
        }
    }

    @FXML
    private void openCreateAccountDialog() throws IOException {
        try {
            if (loaderCreateAccount == null) {
                loaderCreateAccount = new FXMLLoader(getClass().getResource("CreateAccount.fxml"));
                rootCreateAccount = loaderCreateAccount.load();

                create = loaderCreateAccount.getController();
            }
            EHospitalDialog.showDialog(stackPane, rootCreateAccount);
            create.populateComboBox();
        } catch (Exception e) {
        }
    }

    @FXML
    private void showOrHidePassword() {
        // Bind the textField and passwordField text values bidirectionally.
        textUnMaskPassword.textProperty().bindBidirectional(textPassword.textProperty());
    }

    public void getLoginUser() throws SQLException {
        ps = EHospitalDB.getCon().prepareStatement("Select Name from HOSPITALLOGIN Where UserName=? and PASSWORD=?");
        ps.setString(1, username);
        ps.setString(2, password);
        rs = ps.executeQuery();

        if (rs.next()) {
            EHospitalBridge.loginUser = rs.getString("Name");
        }
    }

}
