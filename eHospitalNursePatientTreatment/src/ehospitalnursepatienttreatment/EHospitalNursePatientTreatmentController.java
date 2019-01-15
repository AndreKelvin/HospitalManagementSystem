/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitalnursepatienttreatment;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import ehospitaldb.EHospitalDB;
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
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;

/**
 *
 * @author Andre Kelvin
 */
public class EHospitalNursePatientTreatmentController implements Initializable {
    
    @FXML
    private StackPane stackPane;
    @FXML
    private JFXTextField textSearch, textPatTreat;
    @FXML
    private JFXListView listView;
    @FXML
    private TableView<PatientTreatment> tableView;
    @FXML
    private TableColumn columnPatientName, columnDiagnosis;
    private PreparedStatement ps;
    private ResultSet rs;
    private List searchList;
    private SuggestionProvider suggestion;
    private PatientTreatment selectedPatient;
    private ObservableList obList;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            searchList = new ArrayList();
            suggestion = SuggestionProvider.create(searchList);
            AutoCompletionTextFieldBinding auto = new AutoCompletionTextFieldBinding<>(textSearch, suggestion);

            obList=FXCollections.observableArrayList();
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

                    textSearch.clear();
                } catch (Exception e) {
                }
            });

            tableView.getFocusModel().focus(-1);

        } catch (Exception e) {e.printStackTrace();
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
            ps = EHospitalDB.getCon().prepareStatement("Select PATIENT_NAME,DIAGNOSIS From PATIENT Where CATEGORY In(?,?)");
            ps.setString(1, "In Patient");
            ps.setString(2, "Out Patient");
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
    
}
