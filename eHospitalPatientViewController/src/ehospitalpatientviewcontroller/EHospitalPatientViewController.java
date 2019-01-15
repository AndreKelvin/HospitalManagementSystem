/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitalpatientviewcontroller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import ehospitaldb.EHospitalDB;
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
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 *
 * @author Andre Kelvin
 */
public abstract class EHospitalPatientViewController implements Initializable {
    
    @FXML
    public JFXComboBox comboPatientCat;
    @FXML
    private JFXListView listView;
    @FXML
    private Label labelName,labelPhone,labelCity,labelAddress;
    @FXML
    private Label labelMatStatus,labelGender,labelAge,labelID,labelAdmit,labelWard,labelBed,labelFloor,labelRoom,labelDiagnosis,labelDoctor,labelBloodG;
    @FXML
    private JFXTextField textSearch;
    public SuggestionProvider suggestions;
    public List searchList;
    public PreparedStatement ps;
    public ResultSet rs;
    public ObservableList obList;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            searchList = new ArrayList();
            suggestions = SuggestionProvider.create(searchList);
            Calendar calendar = new GregorianCalendar();
            obList = FXCollections.observableArrayList();
            listView.setItems(obList);
            
            //Bind Search Textfield with Suggestions
            AutoCompletionTextFieldBinding autoBind = new AutoCompletionTextFieldBinding<>(textSearch, suggestions);
            
            //When Patient Name is Selected/Clicked thats when the Search Begins
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
                        ps = EHospitalDB.getCon().prepareStatement("Select * From PATIENT Where PATIENT_NAME=?");
                        ps.setString(1, newValue.toString());
                        rs = ps.executeQuery();

                        if (rs.next()) {
                            labelAddress.setText(rs.getString("ADDRESS"));
                            labelAdmit.setText(rs.getDate("ADMITED_DATE").toString());
                            int age = (byte) (calendar.get(Calendar.YEAR) - rs.getDate("DATE_OF_BIRTH").toLocalDate().getYear());
                            labelAge.setText(age + "");
                            labelBed.setText(rs.getInt("BED") + "");
                            labelBloodG.setText(rs.getString("BLOOD_GROUP"));
                            labelCity.setText(rs.getString("CITY"));
                            labelDiagnosis.setText(rs.getString("DIAGNOSIS"));
                            labelDoctor.setText(rs.getString("DOCTOR"));
                            labelFloor.setText(rs.getString("FLOOR"));
                            labelGender.setText(rs.getString("GENDER"));
                            labelID.setText(rs.getInt("ID") + "");
                            labelMatStatus.setText(rs.getString("MARITAL_STATUS"));
                            labelName.setText(rs.getString("PATIENT_NAME"));
                            labelPhone.setText(rs.getString("PHONE"));
                            labelRoom.setText(rs.getInt("ROOM") + "");
                            labelWard.setText(rs.getString("WARD"));
                        }
                        ps.close();

                        textSearch.clear();
                    }
                } catch (Exception e) {
                }
            });
            
            textSearch.setOnMouseEntered((event) -> {
                searchPatient();
            });
        } catch (Exception e) {
        }
    }

    /**
     * Populate Search Auto Complete Textfield with Patient Name
    */
    public abstract void searchPatient();
    
    /**
     * Set Category Combo Box Value To The Category Which The Patient Name is
     * been Searched for.
     * (Because of the in )This Will Automatically Populate the List View with
     * Patient Names in that Category And Select The Actual Name That is been
     * Searched for and Display the Info
     */
    private void searchAction() {
        try {
            String searchName = textSearch.getText();
            ps = EHospitalDB.getCon().prepareStatement("Select CATEGORY From PATIENT Where PATIENT_NAME=?");
            ps.setString(1, searchName);
            rs = ps.executeQuery();

            if (rs.next()) {
                comboPatientCat.setValue(rs.getString("CATEGORY"));
                listView.getSelectionModel().select(searchName);
            }
            ps.close();
        } catch (Exception e) {
        }
    }
    
    public abstract void displayPatientName();
    
}
