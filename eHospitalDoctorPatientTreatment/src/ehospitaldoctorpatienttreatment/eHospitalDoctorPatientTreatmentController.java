/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitaldoctorpatienttreatment;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.events.JFXDialogEvent;
import ehospitalbridge.EHospitalBridge;
import ehospitaldb.EHospitalDB;
import ehospitaldialog.EHospitalDialog;
import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;

/**
 *
 * @author Andre Kelvin
 */
public class eHospitalDoctorPatientTreatmentController implements Initializable {
    
    @FXML
    private StackPane stackPane;
    @FXML
    private JFXTextField textSearch, textPatTreat;
    @FXML
    private JFXListView listView;
    @FXML
    private JFXButton buttonAdd, buttonEdit, buttonDelete;
    @FXML
    private TableView<PatientTreatment> tableView;
    @FXML
    private TableColumn columnPatientName, columnDiagnosis;
    private PreparedStatement ps;
    private ResultSet rs;
    private ObservableList obList;
    private List searchList;
    private SuggestionProvider suggestion;
    private PatientTreatment selectedPatient;
    private FXMLLoader loaderAdd, loaderEdit, loaderDelete;
    private Parent rootAdd, rootEdit, rootDelete;
    private AddPatientTreatmentController add;
    private EditPatientTreatmentController edit;
    private DeletePatientTreatmentController del;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            searchList = new ArrayList();
            suggestion = SuggestionProvider.create(searchList);
            AutoCompletionTextFieldBinding auto = new AutoCompletionTextFieldBinding<>(textSearch, suggestion);

            buttonAdd.setDisable(true);
            buttonDelete.setDisable(true);
            buttonEdit.setDisable(true);

            obList = FXCollections.observableArrayList();
            tableView.setItems(obList);

            columnPatientName.setCellValueFactory(new PropertyValueFactory("patientName"));
            columnDiagnosis.setCellValueFactory(new PropertyValueFactory("diagnosis"));

            //Select the Searched Patient
            textSearch.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                tableView.getItems().stream()
                        .filter(item -> (item.getPatientName() == null ? newValue == null : item.getPatientName().equals(newValue)))
                        .findAny().ifPresent(item -> {
                            tableView.getSelectionModel().select(item);
                            //tableView.scrollTo(item);
                        });
            });

            //Display Patient Treatment Details
            tableView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends PatientTreatment> observable, PatientTreatment oldValue, PatientTreatment newValue) -> {
                try {
                    selectedPatient = newValue;

                    displayPatientTreatmentDetails();

                    buttonAdd.setDisable(false);
                    buttonDelete.setDisable(false);
                    buttonEdit.setDisable(false);

                    if (!textPatTreat.getText().isEmpty()) {
                        buttonAdd.setDisable(true);
                    }

                    textSearch.clear();
                } catch (Exception e) {
                }
            });

            tableView.getFocusModel().focus(-1);

        } catch (Exception e) {
        }
    }

    private void displayPatientTreatmentDetails() {
        try {
            textPatTreat.clear();
            listView.getItems().clear();
            
            ps = EHospitalDB.getCon().prepareStatement("Select TREATMENT_NAME,DRUGS From PATIENT_TREATMENT Where PATIENT_NAME=?");
            ps.setString(1, selectedPatient.getPatientName());
            rs = ps.executeQuery();
            
            while (rs.next()) {
                textPatTreat.setText(rs.getString("TREATMENT_NAME"));
                listView.getItems().add(rs.getString("DRUGS"));
            }
        } catch (Exception e) {
        }
    }

    /**
     * Populate Table with Patient Name(In and Out) and Diagnosis
     */
    public void populateTableView() {
        try {
            ps = EHospitalDB.getCon().prepareStatement("Select PATIENT_NAME,DIAGNOSIS From PATIENT Where CATEGORY In(?,?) and DOCTOR=?");
            ps.setString(1, "In Patient");
            ps.setString(2, "Out Patient");
            ps.setString(3, EHospitalBridge.loginUser);
            rs = ps.executeQuery();
            
            obList.clear();
            searchList.clear();
            
            while (rs.next()) {
                obList.add(new PatientTreatment(rs.getString("PATIENT_NAME"), rs.getString("DIAGNOSIS")));
                searchList.add(rs.getString("PATIENT_NAME"));
            }
            suggestion.clearSuggestions();
            suggestion.addPossibleSuggestions(searchList);
        } catch (SQLException ex) {
        }
    }

    @FXML
    private void addAction() {
        try {
            if (loaderAdd == null) {
                loaderAdd = new FXMLLoader(getClass().getResource("AddPatientTreatment.fxml"));
                rootAdd = loaderAdd.load();

                add = loaderAdd.getController();
            }
            EHospitalDialog.showDialog(stackPane, rootAdd);
            
            add.setSelectedPatient(selectedPatient);
            add.populateTreatmentComboBox();
            add.populateCheckListView();

            EHospitalDialog.dialog.setOnDialogClosed((JFXDialogEvent event) -> {
                displayPatientTreatmentDetails();

                buttonAdd.setDisable(true);
                buttonEdit.setDisable(true);
                buttonDelete.setDisable(true);
            });
        } catch (Exception e) {
        }
    }

    @FXML
    private void editAction() {
        try {
            if (loaderEdit == null) {
                loaderEdit = new FXMLLoader(getClass().getResource("EditPatientTreatment.fxml"));
                rootEdit = loaderEdit.load();

                edit = loaderEdit.getController();
            }
            EHospitalDialog.showDialog(stackPane, rootEdit);
            
            edit.populateTreatmentComboBox();
            edit.populateCheckListView();
            edit.setSelectedPatient(selectedPatient);
            edit.setPatientTreatmentName(textPatTreat.getText());

            EHospitalDialog.dialog.setOnDialogClosed((JFXDialogEvent event) -> {
                displayPatientTreatmentDetails();

                buttonAdd.setDisable(true);
                buttonEdit.setDisable(true);
                buttonDelete.setDisable(true);
            });
        } catch (Exception e) {
        }
    }

    @FXML
    private void deleteAction() {
        try {
            if (loaderDelete == null) {
                loaderDelete = new FXMLLoader(getClass().getResource("DeletePatientTreatment.fxml"));
                rootDelete = loaderDelete.load();

                del = loaderDelete.getController();
            }
            del.setSelectedPatient(selectedPatient);

            EHospitalDialog.showDialog(stackPane, rootDelete);

            EHospitalDialog.dialog.setOnDialogClosed((JFXDialogEvent event) -> {
                displayPatientTreatmentDetails();

                buttonAdd.setDisable(true);
                buttonEdit.setDisable(true);
                buttonDelete.setDisable(true);
            });
        } catch (Exception e) {
        }
    }

    public StackPane getStackPane() {
        return stackPane;
    }
    
}
