/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitalpatient;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.events.JFXDialogEvent;
import ehospitaldb.EHospitalDB;
import ehospitaldialog.EHospitalDialog;
import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 *
 * @author Andre Kelvin
 */
public class EHospitalPatientController implements Initializable {
    
    @FXML
    private StackPane stackPane;
    @FXML
    private JFXComboBox comboPatientCat;
    @FXML
    private JFXListView listView;
    @FXML
    private JFXButton buttonEdit, buttonDelete, buttonPatientTreat;
    @FXML
    private Label labelID, labelName, labelAdmit, labelWard, labelPhone;
    @FXML
    private Label labelCity, labelAddress, labelMatStatus, labelGender, labelAge, labelDiagnosis, labelRoom, labelFloor, labelBed, labelBloodG, labelDoctor;
    @FXML
    private JFXTextField textSearch;
    private FXMLLoader loaderAdd, loaderEdit, loaderDelete, loaderViewTreatment;
    private Parent rootAdd, rootEdit, rootDelete, rootViewTreatment;
    private ObservableList obList;
    private PreparedStatement ps;
    private ResultSet rs;
    private byte age;
    private List searchList, takenBedList, takenRoomList;
    private SuggestionProvider suggestions;
    private String searchName, selectedPatientName;
    private EditPatientController edit;
    private DeletePatientController del;
    private AddPatientController add;
    private PatientTreatmentInfoController pat;
    private JFXDialog dialogViewTreatment;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            searchList = new ArrayList();
            takenBedList = new ArrayList();
            takenRoomList = new ArrayList();
            suggestions = SuggestionProvider.create(searchList);
            Calendar calendar = new GregorianCalendar();
            obList = FXCollections.observableArrayList();
            listView.setItems(obList);

            buttonEdit.setDisable(true);
            buttonDelete.setDisable(true);
            buttonPatientTreat.setDisable(true);

            //Get the Taken Bed and Room Values
            ps = EHospitalDB.getCon().prepareStatement("Select Bed From PATIENT");
            rs = ps.executeQuery();

            while (rs.next()) {
                takenBedList.add(rs.getInt("Bed") + "");
            }
            ps.close();

            ps = EHospitalDB.getCon().prepareStatement("Select Room From PATIENT");
            rs = ps.executeQuery();

            while (rs.next()) {
                takenRoomList.add(rs.getInt("Room") + "");
            }
            ps.close();

            searchPatient();
            //Bind Search Textfield with Suggestions
            AutoCompletionTextFieldBinding autoBind = new AutoCompletionTextFieldBinding<>(textSearch, suggestions);

            //When Patient Name is Selected thats when the Search Begins
            textSearch.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                searchAction();
            });

            //Populate Patient Category Combo box
            comboPatientCat.getItems().addAll("In Patient", "Out Patient", "Discharge Patient");

            //Display Patient Name When Category is Selected
            comboPatientCat.getSelectionModel().selectedItemProperty().addListener((ObservableValue observable, Object oldValue, Object newValue) -> {
                displayPatientName();

                labelAddress.setText("");
                labelAdmit.setText("");
                labelAge.setText("");
                labelBed.setText("");
                labelBloodG.setText("");
                labelCity.setText("");
                labelDiagnosis.setText("");
                labelDoctor.setText("");
                labelFloor.setText("");
                labelGender.setText("");
                labelID.setText("");
                labelMatStatus.setText("");
                labelName.setText("");
                labelPhone.setText("");
                labelRoom.setText("");
                labelWard.setText("");
            });

            //Display Info When Patinet Name is been Selected
            listView.getSelectionModel().selectedItemProperty().addListener((ObservableValue observable, Object oldValue, Object newValue) -> {
                try {
                    if (newValue == null) {

                    } else {
                        selectedPatientName = newValue.toString();

                        ps = EHospitalDB.getCon().prepareStatement("Select * From PATIENT Where Patient_Name=?");
                        ps.setString(1, selectedPatientName);
                        rs = ps.executeQuery();

                        if (rs.next()) {
                            labelAddress.setText(rs.getString("Address"));
                            labelAdmit.setText(rs.getDate("Admited_Date").toString());

                            //Dispaly Patient Age
                            age = (byte) (calendar.get(Calendar.YEAR) - rs.getDate("Date_Of_Birth").toLocalDate().getYear());
                            labelAge.setText(age + "");
                            labelBed.setText(rs.getInt("Bed") + "");
                            labelBloodG.setText(rs.getString("Blood_Group"));
                            labelCity.setText(rs.getString("City"));
                            labelDiagnosis.setText(rs.getString("Diagnosis"));
                            labelDoctor.setText(rs.getString("Doctor"));
                            labelFloor.setText(rs.getString("Floor"));
                            labelGender.setText(rs.getString("Gender"));
                            labelID.setText(rs.getInt("ID") + "");
                            labelMatStatus.setText(rs.getString("Marital_Status"));
                            labelName.setText(rs.getString("Patient_Name"));
                            labelPhone.setText(rs.getString("Phone"));
                            labelRoom.setText(rs.getInt("Room") + "");
                            labelWard.setText(rs.getString("Ward"));
                        }
                        ps.close();

                        textSearch.clear();

                        buttonEdit.setDisable(false);
                        buttonDelete.setDisable(false);
                        buttonPatientTreat.setDisable(false);
                    }
                } catch (Exception e) {
                }
            });
        } catch (Exception e) {
        }
    }

    /**
     * Populate Search Auto Complete Textfield with Patient Name
    */
    private void searchPatient() {
        try {
            searchList.clear();
            ps = EHospitalDB.getCon().prepareStatement("Select Patient_Name From PATIENT");
            rs = ps.executeQuery();

            while (rs.next()) {
                searchList.add(rs.getString("Patient_Name"));
            }
            ps.close();

            suggestions.clearSuggestions();
            suggestions.addPossibleSuggestions(searchList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayPatientName() {
        try {
            obList.clear();
            ps = EHospitalDB.getCon().prepareStatement("Select Patient_Name From PATIENT Where Category=?");
            ps.setString(1, comboPatientCat.getValue().toString());
            rs = ps.executeQuery();

            while (rs.next()) {
                obList.add(rs.getString("Patient_Name"));
            }
            ps.close();
        } catch (Exception e) {
        }
    }

    /**
     * Set Category Combo Box Value To The Category Which The Patient Name is
     * been Searched for This Will Automatically Populate the List View with
     * Patient Names in that Category And Select The Actual Name That is been
     * Searched for and Display the Info
     */
    private void searchAction() {
        try {
            searchName = textSearch.getText();
            ps = EHospitalDB.getCon().prepareStatement("Select Category From PATIENT Where Patient_Name=?");
            ps.setString(1, searchName);
            rs = ps.executeQuery();

            if (rs.next()) {
                comboPatientCat.setValue(rs.getString("Category"));
                listView.getSelectionModel().select(searchName);
            }
            ps.close();
        } catch (Exception e) {
        }
    }

    @FXML
    private void addAction() {
        try {
            if (loaderAdd == null) {
                loaderAdd = new FXMLLoader(getClass().getResource("AddPatient.fxml"));
                rootAdd = loaderAdd.load();

                add = loaderAdd.getController();
                add.setTakenBedandRoomList(takenBedList, takenRoomList);
            }
            EHospitalDialog.showDialog(stackPane, rootAdd);
            add.populateComboBoxs();

            //Refresh List View and Search Suguestion
            EHospitalDialog.dialog.setOnDialogClosed((JFXDialogEvent event) -> {
                displayPatientName();
                searchPatient();
            });
//            comboPatientCat.setDisable(true);
//            textSearch.setDisable(true);
//            listView.setDisable(true);
        } catch (Exception e) {
        }
    }

    @FXML
    private void editAction() {
        try {
            if (loaderEdit == null) {
                loaderEdit = new FXMLLoader(getClass().getResource("EditPatient.fxml"));
                rootEdit = loaderEdit.load();

                edit = loaderEdit.getController();
                edit.setTakenBedandRoomList(takenBedList, takenRoomList);
            }
            EHospitalDialog.showDialog(stackPane, rootEdit);
            edit.populateComboBoxs();
            edit.setSelectedPatientNameAndID(selectedPatientName, Integer.parseInt(labelID.getText()));

            //Refresh List View and Search Suguestion
            EHospitalDialog.dialog.setOnDialogClosed((JFXDialogEvent event) -> {
                displayPatientName();
                searchPatient();

                listView.getSelectionModel().select(selectedPatientName);
                listView.getSelectionModel().clearSelection();

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
                loaderDelete = new FXMLLoader(getClass().getResource("DeletePatient.fxml"));
                rootDelete = loaderDelete.load();

                del = loaderDelete.getController();
                del.setTakenBedandRoomList(takenBedList, takenRoomList);
                
                del.setLabelAddress(labelAddress);
                del.setLabelAdmit(labelAdmit);
                del.setLabelAge(labelAge);
                del.setLabelBed(labelBed);
                del.setLabelBloodG(labelBloodG);
                del.setLabelCity(labelCity);
                del.setLabelDiagnosis(labelDiagnosis);
                del.setLabelDoctor(labelDoctor);
                del.setLabelFloor(labelFloor);
                del.setLabelGender(labelGender);
                del.setLabelID(labelID);
                del.setLabelMatStatus(labelMatStatus);
                del.setLabelName(labelName);
                del.setLabelPhone(labelPhone);
                del.setLabelRoom(labelRoom);
                del.setLabelWard(labelWard);
            }
            EHospitalDialog.showDialog(stackPane, rootDelete);
            del.setPatientID(Integer.parseInt(labelID.getText()));
            del.setSelectedPatientName(selectedPatientName);
            del.setSelectedBedOrRoomValue(labelBed.getText(), labelRoom.getText());

            //Refresh List View and Search Suguestion
            EHospitalDialog.dialog.setOnDialogClosed((JFXDialogEvent event) -> {
                displayPatientName();
                searchPatient();

                buttonEdit.setDisable(true);
                buttonDelete.setDisable(true);
            });
        } catch (Exception e) {
        }
    }

    @FXML
    private void viewPatientTreatment() {
        try {
            if (loaderViewTreatment == null) {
                loaderViewTreatment = new FXMLLoader(getClass().getResource("PatientTreatmentInfo.fxml"));
                rootViewTreatment = loaderViewTreatment.load();

                dialogViewTreatment = new JFXDialog(stackPane, (Region) rootViewTreatment, JFXDialog.DialogTransition.TOP);

                pat = loaderViewTreatment.getController();
            }
            dialogViewTreatment.show();
            
            pat.initializeValues(selectedPatientName, labelDiagnosis.getText(), labelID.getText());
        } catch (Exception e) {
        }
    }
    
}
