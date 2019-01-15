/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitalnursebloodbank;

import ehospitaldb.EHospitalDB;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
public class EHospitalNurseBloodBankController implements Initializable {
    
    @FXML
    private TableView<BloodGroup> tableView;
    @FXML
    private TableView<BloodDonor> tableViewDonor;
    @FXML
    private TableColumn columnBlood,columnBags;
    @FXML
    private TableColumn columnID, columnName, columnAge, columnSex, columnBloodGroup, columnDonationDate, columnPhone, columnEmail, columnAddress;
    private byte age;
    private Calendar calender;
    private ObservableList obListBloodGroup,obListDonor;
    private PreparedStatement ps;
    private ResultSet rs;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            calender = new GregorianCalendar();
            
            columnBlood.setCellValueFactory(new PropertyValueFactory("blood"));
            columnBags.setCellValueFactory(new PropertyValueFactory("bags"));
            
            columnID.setCellValueFactory(new PropertyValueFactory("id"));
            columnAddress.setCellValueFactory(new PropertyValueFactory("address"));
            columnAge.setCellValueFactory(new PropertyValueFactory("age"));
            columnBloodGroup.setCellValueFactory(new PropertyValueFactory("bloodGroup"));
            columnDonationDate.setCellValueFactory(new PropertyValueFactory("donationDate"));
            columnEmail.setCellValueFactory(new PropertyValueFactory("email"));
            columnName.setCellValueFactory(new PropertyValueFactory("name"));
            columnPhone.setCellValueFactory(new PropertyValueFactory("phone"));
            columnSex.setCellValueFactory(new PropertyValueFactory("sex"));

            obListBloodGroup=FXCollections.observableArrayList();
            tableView.setItems(obListBloodGroup);

            obListDonor = FXCollections.observableArrayList();
            tableViewDonor.setItems(obListDonor);
            
            populateDonorTable();
            populateBloodGroupTable();
            
            //tableView.getFocusModel().focus(-1);
        } catch (Exception e) {
        }
    }
    
    public void populateBloodGroupTable(){
        try {
            obListBloodGroup.clear();
            
            ps=EHospitalDB.getCon().prepareStatement("Select * From BLOOD_GROUP");
            rs=ps.executeQuery();
            while(rs.next()){
                obListBloodGroup.add(new BloodGroup(rs.getString("NAME"), Integer.parseInt(rs.getString("BAGS"))));
            }
            ps.close();
        } catch (Exception e) {
        }
    }
    
    public void populateDonorTable(){
        try {
            obListDonor.clear();
            
            ps = EHospitalDB.getCon().prepareStatement("Select * From BLOOD_DONOR");
            rs = ps.executeQuery();

            while (rs.next()) {
                age = (byte) (calender.get(Calendar.YEAR) - rs.getDate("AGE").toLocalDate().getYear());
                obListDonor.add(new BloodDonor(rs.getInt("ID"), rs.getString("NAME"), age + "", rs.getString("GENDER"), rs.getString("BLOOD_GROUP"),
                        rs.getString("DONATION_DATE"), rs.getString("PHONE"), rs.getString("EMAIL"), rs.getString("ADDRESS")));
            }
        } catch (Exception e) {
        }
    }
    
}
