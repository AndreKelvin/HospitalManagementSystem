/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitallabtechpatient;

import ehospitaldb.EHospitalDB;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Andre Kelvin
 */
public class EHospitalLabTechPatientController implements Initializable {
    
    @FXML
    private TableView<Patient> tableView;
    @FXML
    private TableColumn columnPatient,columnBloodGroup,columnDiagnosis;
    private ObservableList obList;
    private PreparedStatement ps;
    private ResultSet rs;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        columnBloodGroup.setCellValueFactory(new PropertyValueFactory("bloodGroup"));
        columnDiagnosis.setCellValueFactory(new PropertyValueFactory("diagnosis"));
        columnPatient.setCellValueFactory(new PropertyValueFactory("patient"));
        
        obList=FXCollections.observableArrayList();
        tableView.setItems(obList);
    }    
    
    public void populateTable(){
        try {
            ps=EHospitalDB.getCon().prepareCall("Select PATIENT_NAME,DIAGNOSIS,BLOOD_GROUP From PATIENT");
            rs=ps.executeQuery();
            obList.clear();
            while(rs.next()){
                obList.add(new Patient(rs.getString("PATIENT_NAME"), rs.getString("BLOOD_GROUP"), rs.getString("DIAGNOSIS")));
            }
        } catch (Exception e) {
        }
    }
}
