/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitaldoctorappointment;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.events.JFXDialogEvent;
import ehospitalbridge.EHospitalBridge;
import ehospitaldialog.EHospitalDialog;
import ehospitaldb.EHospitalDB;
import java.awt.Toolkit;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 *
 * @author Andre Kelvin
 */
public class EHospitalDoctorAppointmentController implements Initializable {

    @FXML
    private StackPane stackPane;
    @FXML
    private JFXButton buttonEdit;
    @FXML
    private JFXButton buttonDelete;
    @FXML
    private TableView<Appointment> tableView;
    @FXML
    private TableColumn columnDoctor, columnPatient, columnDate, columnFrom, columnTo, columnStatus;
    private ObservableList obList;
    private PreparedStatement ps;
    private ResultSet rs;
    private FXMLLoader loaderAdd, loaderEdit, loaderDelete;
    private Parent rootAdd, rootEdit, rootDelete;
    private EditAppointmentController edit;
    private DeleteAppointmentController del;
    private AddAppointmentController add;
    private Appointment selectedAppointment;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            obList = FXCollections.observableArrayList();
            tableView.setItems(obList);

            buttonDelete.setDisable(true);
            buttonEdit.setDisable(true);

            columnDoctor.setCellValueFactory(new PropertyValueFactory("doctor"));
            columnPatient.setCellValueFactory(new PropertyValueFactory("patient"));
            columnDate.setCellValueFactory(new PropertyValueFactory("date"));
            columnFrom.setCellValueFactory(new PropertyValueFactory("from"));
            columnTo.setCellValueFactory(new PropertyValueFactory("to"));
            columnStatus.setCellValueFactory(new PropertyValueFactory("status"));

            tableView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Appointment> observable, Appointment oldValue, Appointment newValue) -> {
                buttonDelete.setDisable(false);
                buttonEdit.setDisable(false);

                selectedAppointment = newValue;
            });

            tableView.getFocusModel().focus(-1);
        } catch (Exception e) {
        }

        displayAppointmentNotification();
    }

    public void populateTable() {
        try {
            ps = EHospitalDB.getCon().prepareStatement("Select * From APPOINTMENT Where DOCTOR=?");
            ps.setString(1, EHospitalBridge.loginUser);
            rs = ps.executeQuery();
            
            obList.clear();
            while (rs.next()) {
                obList.add(new Appointment(rs.getString("DOCTOR"), rs.getString("PATIENT"), rs.getString("DATE"), rs.getString("START"), rs.getString("ENDING"), rs.getString("STATUS")));
            }
        } catch (SQLException ex) {
        }
    }

    @FXML
    private void openAddDialog() {
        try {
            if (loaderAdd == null) {
                loaderAdd = new FXMLLoader(getClass().getResource("AddAppointment.fxml"));
                rootAdd = loaderAdd.load();

                add = loaderAdd.getController();
                add.setObervableList(obList);
                add.setDoctorName(EHospitalBridge.loginUser);
            }
            EHospitalDialog.showDialog(stackPane, rootAdd);
            add.populateComboBoxs();
        } catch (Exception e) {
        }
    }

    @FXML
    private void openEditDialog() {
        try {
            if (loaderEdit == null) {
                loaderEdit = new FXMLLoader(getClass().getResource("EditAppointment.fxml"));
                rootEdit = loaderEdit.load();

                edit = loaderEdit.getController();
                edit.setDoctorName(EHospitalBridge.loginUser);
            }
            EHospitalDialog.showDialog(stackPane, rootEdit);
            edit.populateComboBoxs();
            edit.setSelectedAppointment(selectedAppointment);

            EHospitalDialog.dialog.setOnDialogClosed((JFXDialogEvent event) -> {
                tableView.getSelectionModel().clearSelection();

                buttonDelete.setDisable(true);
                buttonEdit.setDisable(true);
            });
        } catch (Exception e) {
        }
    }

    @FXML
    private void openDeleteDialog() {
        try {
            if (loaderDelete == null) {
                loaderDelete = new FXMLLoader(getClass().getResource("DeleteAppointment.fxml"));
                rootDelete = loaderDelete.load();

                del = loaderDelete.getController();
                del.setObservableList(obList);
                del.setDoctorName(EHospitalBridge.loginUser);
            }
            EHospitalDialog.showDialog(stackPane, rootDelete);
            del.setSelectedAppointment(selectedAppointment);

            EHospitalDialog.dialog.setOnDialogClosed((JFXDialogEvent event) -> {
                tableView.getSelectionModel().clearSelection();

                buttonDelete.setDisable(true);
                buttonEdit.setDisable(true);
            });
        } catch (Exception e) {
        }
    }

    private void displayAppointmentNotification() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");

        Timer timer = new Timer();

        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                try {
                    ps = EHospitalDB.getCon().prepareStatement("Select * From NOTIFICATION Where DOCTOR=?");
                    ps.setString(1, EHospitalBridge.loginUser);
                    rs = ps.executeQuery();

                    while (rs.next()) {

                        if (rs.getString("REMINDER").contentEquals(dateFormat.format(new Date()))) {
                            Notifications noti = Notifications.create();
                            noti.text("Doctor " + rs.getString("DOCTOR") + " has an Appointment with Patient " + rs.getString("PATIENT"));
                            noti.title("Appointment");
                            noti.hideAfter(Duration.seconds(30));
                            noti.position(Pos.BOTTOM_RIGHT);

                            Platform.runLater(() -> {
                                noti.show();
                                Toolkit.getDefaultToolkit().beep();
                            });

                            //Change The Appointment Status to Done
                            ps = EHospitalDB.getCon().prepareStatement("Update APPOINTMENT Set STATUS=? Where PATIENT=? and DOCTOR=?");
                            ps.setString(1, "Done");
                            ps.setString(2, rs.getString("PATIENT"));
                            ps.setString(3, EHospitalBridge.loginUser);
                            ps.executeUpdate();

                            populateTable();
                        }

                    }
                } catch (Exception e) {
                }
            }

        };
        timer.schedule(task, 0, 1000);
    }

}
