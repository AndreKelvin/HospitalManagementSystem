/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitalbloodbank;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.events.JFXDialogEvent;
import ehospitaldb.EHospitalDB;
import ehospitaldialog.EHospitalDialog;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

/**
 *
 * @author Andre Kelvin
 */
public class EHospitalBloodBankController implements Initializable {

    @FXML
    private StackPane stackPane;
    @FXML
    private JFXButton buttonGrpEdit, buttonGrpDelete, buttonDonEdit, buttonDonDelete;
    @FXML
    private TableView<BloodGroup> tableViewGroup;
    @FXML
    private TableView<BloodDonor> tableViewDonor;
    @FXML
    private TableColumn columnBlood, columnBags;
    @FXML
    private TableColumn columnID, columnName, columnAge, columnSex, columnBloodGroup, columnDonationDate, columnPhone, columnEmail, columnAddress;
    private byte age;
    private Calendar calender;
    private ObservableList obListBloodGroup, obListDonor;
    private PreparedStatement ps;
    private ResultSet rs;
    private BloodGroup selectedBlood;
    private BloodDonor selectedDonor;
    private FXMLLoader loaderGrpAdd, loaderGrpEdit, loaderGrpDelete, loaderDonAdd, loaderDonEdit, loaderDonDelete;
    private Parent rootDonAdd, rootDonEdit, rootDonDelete, rootGrpAdd, rootGrpEdit, rootGrpDelete;
    private AddDonorController addDonor;
    private EditBloodGroupController editBlood;
    private EditDonorController editDonor;
    private DeleteBloodGroupController deleteBlood;
    private DeleteDonorController deleteDonor;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            buttonDonDelete.setDisable(true);
            buttonDonEdit.setDisable(true);
            buttonGrpDelete.setDisable(true);
            buttonGrpEdit.setDisable(true);

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

            obListBloodGroup = FXCollections.observableArrayList();
            tableViewGroup.setItems(obListBloodGroup);

            obListDonor = FXCollections.observableArrayList();
            tableViewDonor.setItems(obListDonor);

            tableViewGroup.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                buttonGrpDelete.setDisable(false);
                buttonGrpEdit.setDisable(false);

                selectedBlood = newValue;
            });

            tableViewDonor.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                buttonDonDelete.setDisable(false);
                buttonDonEdit.setDisable(false);

                selectedDonor = newValue;
            });

            populateDonorTable();
            populateBloodGroupTable();

            //tableView.getFocusModel().focus(-1);
        } catch (Exception e) {
        }
    }

    public void populateBloodGroupTable() {
        try {
            obListBloodGroup.clear();

            ps = EHospitalDB.getCon().prepareStatement("Select * From BLOOD_GROUP");
            rs = ps.executeQuery();
            while (rs.next()) {
                obListBloodGroup.add(new BloodGroup(rs.getString("NAME"), Integer.parseInt(rs.getString("BAGS"))));
            }
            ps.close();
        } catch (Exception e) {
        }
    }

    public void populateDonorTable() {
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

    @FXML
    private void addBloodGroup() {
        try {
            if (loaderGrpAdd == null) {
                loaderGrpAdd = new FXMLLoader(getClass().getResource("AddBloodGroup.fxml"));
                rootGrpAdd = loaderGrpAdd.load();
                
                AddBloodGroupController addBlood=loaderGrpAdd.getController();
                addBlood.setOblist(obListBloodGroup);
            }
            EHospitalDialog.showDialog(stackPane, rootGrpAdd);
        } catch (Exception e) {
        }
    }

    @FXML
    private void editBloodGroup() {
        try {
            if (loaderGrpEdit == null) {
                loaderGrpEdit = new FXMLLoader(getClass().getResource("EditBloodGroup.fxml"));
                rootGrpEdit = loaderGrpEdit.load();
                
                editBlood=loaderGrpEdit.getController();
            }
            editBlood.setSelectedValue(selectedBlood);
            
            EHospitalDialog.showDialog(stackPane, rootGrpEdit);
            
            EHospitalDialog.dialog.setOnDialogClosed((JFXDialogEvent event) -> {
                tableViewGroup.getSelectionModel().clearSelection();
                
                buttonGrpDelete.setDisable(true);
                buttonGrpEdit.setDisable(true);
            });
        } catch (Exception e) {e.printStackTrace();
        }
    }

    @FXML
    private void deleteBloodGroup() {
        try {
            if (loaderGrpDelete == null) {
                loaderGrpDelete = new FXMLLoader(getClass().getResource("DeleteBloodGroup.fxml"));
                rootGrpDelete = loaderGrpDelete.load();
                
                deleteBlood=loaderGrpDelete.getController();
                deleteBlood.setObList(obListBloodGroup);
            }
            deleteBlood.setSelectedValue(selectedBlood);
            
            EHospitalDialog.showDialog(stackPane, rootGrpDelete);
            
            EHospitalDialog.dialog.setOnDialogClosed((JFXDialogEvent event) -> {
                tableViewGroup.getSelectionModel().clearSelection();
                
                buttonGrpDelete.setDisable(true);
                buttonGrpEdit.setDisable(true);
            });
        } catch (Exception e) {
        }
    }

    @FXML
    private void addDonor() {
        try {
            if (loaderDonAdd == null) {
                loaderDonAdd = new FXMLLoader(getClass().getResource("AddDonor.fxml"));
                rootDonAdd = loaderDonAdd.load();
                
                addDonor=loaderDonAdd.getController();
                addDonor.setObservableList(obListDonor);
            }
            EHospitalDialog.showDialog(stackPane, rootDonAdd);
        } catch (Exception e) {
        }
    }

    @FXML
    private void editDonor() {
        try {
            if (loaderDonEdit == null) {
                loaderDonEdit = new FXMLLoader(getClass().getResource("EditDonor.fxml"));
                rootDonEdit = loaderDonEdit.load();
                
                editDonor=loaderDonEdit.getController();
            }
            EHospitalDialog.showDialog(stackPane, rootDonEdit);
            
            editDonor.populateBloodGroupComboBox();
            editDonor.setSelectedDonor(selectedDonor);
            
            EHospitalDialog.dialog.setOnDialogClosed((JFXDialogEvent event) -> {
                tableViewDonor.getSelectionModel().clearSelection();
                
                buttonDonDelete.setDisable(true);
                buttonDonEdit.setDisable(true);
            });
        } catch (Exception e) {
        }
    }

    @FXML
    private void deleteDonor() {
        try {
            if (loaderDonDelete == null) {
                loaderDonDelete = new FXMLLoader(getClass().getResource("DeleteDonor.fxml"));
                rootDonDelete = loaderDonDelete.load();
                
                deleteDonor=loaderDonDelete.getController();
                deleteDonor.setObservableList(obListDonor);
            }
            deleteDonor.setSelectedDonor(selectedDonor);
            
            EHospitalDialog.showDialog(stackPane, rootDonDelete);
            
            EHospitalDialog.dialog.setOnDialogClosed((JFXDialogEvent event) -> {
                tableViewDonor.getSelectionModel().clearSelection();
                
                buttonDonDelete.setDisable(true);
                buttonDonEdit.setDisable(true);
            });
        } catch (Exception e) {
        }
    }    

}
