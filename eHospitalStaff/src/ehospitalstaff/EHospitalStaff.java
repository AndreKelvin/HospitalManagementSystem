/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitalstaff;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.events.JFXDialogEvent;
import ehospitalbridge.EHospitalBridge;
import ehospitaldb.EHospitalDB;
import ehospitaldialog.EHospitalDialog;
import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javax.imageio.ImageIO;

/**
 *
 * @author Andre Kelvin
 */
public abstract class EHospitalStaff {

    @FXML
    private StackPane stackPane;
    @FXML
    public JFXComboBox comboDeptLabTech,comboDeptDoc,comboDeptNurse,comboDeptPhar;
    @FXML
    public JFXListView listView;
    @FXML
    public JFXButton buttonEdit, buttonDelete;
    @FXML
    public Label labelName, labelPhone, labelCity, labelAddress, labelMatStatus, labelGender, labelAge, labelEmail, labelID, labelDateJoined, labelSalary, labelDegree;
    @FXML
    public ImageView imageView;
    @FXML
    public JFXTextField textSearch;
    private FXMLLoader loaderAdd, loaderEdit, loaderDelete;
    private Parent rootAdd, rootEdit, rootDelete;
    public ObservableList obList;
    public PreparedStatement ps;
    public ResultSet rs;
    private Calendar calendar;
    private byte age;
    private BufferedImage buff;
    private Image image; 
    public Image defaultImage;
    private List searchList;
    private SuggestionProvider suggestions;
    private String selectedStaffName, tableName, staffImageFolder, addFxml, editFxml, deleteFxml;
    public String searchName;
    private File staffImageFile;
    private AddStaffController add;
    private DeleteStaffController del;
    private EditStaffController edit;
    private JFXComboBox comboBox;

    public void initialize() {
        try {
            createStaffImageFolder();

            //Pass Department Combo Box
            EHospitalBridge.comboBoxDepartmentLabTech = comboDeptLabTech;
            EHospitalBridge.comboBoxDepartmentDoc = comboDeptDoc;
            EHospitalBridge.comboBoxDepartmentNurse = comboDeptNurse;
            EHospitalBridge.comboBoxDepartmentPhar = comboDeptPhar;

            calendar = new GregorianCalendar();
            defaultImage = imageView.getImage();
            searchList = new ArrayList();
            suggestions = SuggestionProvider.create(searchList);
            obList = FXCollections.observableArrayList();
            listView.setItems(obList);

            buttonEdit.setDisable(true);
            buttonDelete.setDisable(true);

            searchStaff();
            //Bind Search Textfield with Suggestions
            AutoCompletionTextFieldBinding autoCompletionTextFieldBinding = new AutoCompletionTextFieldBinding<>(textSearch, suggestions);

            //When Pharmacist Name is Selected thats when the Search Begins
            textSearch.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                searchAction();
            });

//            //Populate Department Combo Box
//            ps = EHospitalDB.getCon().prepareStatement("Select Name From DEPARTMENT");
//            rs = ps.executeQuery();
//
//            while (rs.next()) {
//                comboDeptDoc.getItems().add(rs.getString("Name"));
//                comboDeptNurse.getItems().add(rs.getString("Name"));
//                comboDeptLabTech.getItems().add(rs.getString("Name"));
//                comboDeptPhar.getItems().add(rs.getString("Name"));
//            }
//            ps.close();

            //Display Staff Name on List View When Department is been Selected
//            comboDeptLabTech.getSelectionModel().selectedItemProperty().addListener((ObservableValue observable, Object oldValue, Object newValue) -> {
//                try {
//                    displayStaffName();
//
//                    //Empty All Info
//                    labelID.setText("");
//                    labelName.setText("");
//                    labelAge.setText("");
//                    labelGender.setText("");
//                    labelAddress.setText("");
//                    labelCity.setText("");
//                    labelPhone.setText("");
//                    labelMatStatus.setText("");
//                    labelDateJoined.setText("");
//                    labelSalary.setText("");
//                    labelEmail.setText("");
//                    labelDegree.setText("");
//                    imageView.setImage(defaultImage);
//
//                    buttonEdit.setDisable(true);
//                    buttonDelete.setDisable(true);
//
//                } catch (Exception e) {
//                }
//            });

//            comboDeptDoc.getSelectionModel().selectedItemProperty().addListener((ObservableValue observable, Object oldValue, Object newValue) -> {
//                try {
//                    displayStaffName();
//
//                    //Empty All Info
//                    labelID.setText("");
//                    labelName.setText("");
//                    labelAge.setText("");
//                    labelGender.setText("");
//                    labelAddress.setText("");
//                    labelCity.setText("");
//                    labelPhone.setText("");
//                    labelMatStatus.setText("");
//                    labelDateJoined.setText("");
//                    labelSalary.setText("");
//                    labelEmail.setText("");
//                    labelDegree.setText("");
//                    imageView.setImage(defaultImage);
//
//                    buttonEdit.setDisable(true);
//                    buttonDelete.setDisable(true);
//
//                } catch (Exception e) {
//                }
//            });

//            comboDeptNurse.getSelectionModel().selectedItemProperty().addListener((ObservableValue observable, Object oldValue, Object newValue) -> {
//                try {
//                    displayStaffName();
//
//                    //Empty All Info
//                    labelID.setText("");
//                    labelName.setText("");
//                    labelAge.setText("");
//                    labelGender.setText("");
//                    labelAddress.setText("");
//                    labelCity.setText("");
//                    labelPhone.setText("");
//                    labelMatStatus.setText("");
//                    labelDateJoined.setText("");
//                    labelSalary.setText("");
//                    labelEmail.setText("");
//                    labelDegree.setText("");
//                    imageView.setImage(defaultImage);
//
//                    buttonEdit.setDisable(true);
//                    buttonDelete.setDisable(true);
//
//                } catch (Exception e) {
//                }
//            });

//            comboDeptPhar.getSelectionModel().selectedItemProperty().addListener((ObservableValue observable, Object oldValue, Object newValue) -> {
//                try {
//                    displayStaffName();
//
//                    //Empty All Info
//                    labelID.setText("");
//                    labelName.setText("");
//                    labelAge.setText("");
//                    labelGender.setText("");
//                    labelAddress.setText("");
//                    labelCity.setText("");
//                    labelPhone.setText("");
//                    labelMatStatus.setText("");
//                    labelDateJoined.setText("");
//                    labelSalary.setText("");
//                    labelEmail.setText("");
//                    labelDegree.setText("");
//                    imageView.setImage(defaultImage);
//
//                    buttonEdit.setDisable(true);
//                    buttonDelete.setDisable(true);
//
//                } catch (Exception e) {
//                }
//            });

            //Display Info When Staff Name is been Selected
            listView.getSelectionModel().selectedItemProperty().addListener((ObservableValue observable, Object oldValue, Object newValue) -> {
                try {
                    if (newValue == null) {

                    } else {
                        selectedStaffName = newValue.toString();

                        ps = EHospitalDB.getCon().prepareStatement("Select * From " + tableName + " Where Name=?");
                        ps.setString(1, selectedStaffName);
                        rs = ps.executeQuery();

                        if (rs.next()) {
                            labelID.setText(rs.getInt("ID") + "");
                            labelName.setText(rs.getString("Name"));

                            //Subtract System Date Year with Date_Of_Birth Year to get the LabTechnician Age 
                            age = (byte) (calendar.get(Calendar.YEAR) - rs.getDate("Date_Of_Birth").toLocalDate().getYear());
                            labelAge.setText(age + "");
                            labelGender.setText(rs.getString("Gender"));
                            labelAddress.setText(rs.getString("Address"));
                            labelCity.setText(rs.getString("City"));
                            labelPhone.setText(rs.getString("Phone"));
                            labelMatStatus.setText(rs.getString("Marital_Status"));
                            labelDateJoined.setText(rs.getString("Date_Joined"));
                            labelSalary.setText(rs.getString("Salary"));
                            labelEmail.setText(rs.getString("Email"));
                            labelDegree.setText(rs.getString("Degree"));

                            staffImageFile = new File(rs.getString("Photo"));
                            buff = ImageIO.read(staffImageFile);
                            image = SwingFXUtils.toFXImage(buff, null);
                            imageView.setImage(image);
                        }
                        ps.close();

                        textSearch.clear();

                        buttonEdit.setDisable(false);
                        buttonDelete.setDisable(false);
                    }
                } catch (Exception e) {
                }
            });
        } catch (Exception e) {e.printStackTrace();
        }
    }

    /**
     * Creates a File Folder to save Staff's image
     */
    private void createStaffImageFolder(){
        new File(System.getProperty("user.home") + File.separator + staffImageFolder).mkdir();
    };
    
    /**
     * The Name of the Database Table which is going to display the selected Staff Details
     * @param tableName 
     */
    public void setTableName(String tableName){
        this.tableName=tableName;
    }
    
    public void setImageFolder(String staffImageFolder){
        this.staffImageFolder=staffImageFolder;
    }

    /**
     * Set Department Combo Box Value To The Department Which The Staff Name is
     * been Searched for This Will Automatically Populate the List View with
     * Staff Names in that Department And Select The Actual Name That is been
     * Searched for and Display the Info
     */
    public abstract void searchAction();
    
    /**
     * Display the selected Staff Names 
     */
    public abstract void displayStaffName();
    
    /**
     * Populate Auto Complete Search Textfield with Staff Names
     */
    private void searchStaff(){
        try {
            searchList.clear();
            ps = EHospitalDB.getCon().prepareStatement("Select Name From "+tableName);
            rs = ps.executeQuery();

            while (rs.next()) {
                searchList.add(rs.getString("Name"));
            }
            ps.close();

            suggestions.clearSuggestions();
            suggestions.addPossibleSuggestions(searchList);
        } catch (Exception e) {
        }
    };
    
    public void setFxml(String addFxml,String editFxml,String deleteFxml){
        this.addFxml=addFxml;
        this.editFxml=editFxml;
        this.deleteFxml=deleteFxml;
    }
    
    public void setComboBoxItems(JFXComboBox comboBox){
        this.comboBox=comboBox;
    }
    
    @FXML
    private void addAction() {
        try {
            if (loaderAdd == null) {
                loaderAdd = new FXMLLoader(getClass().getResource(addFxml));
                rootAdd = loaderAdd.load();
                
                add=loaderAdd.getController();
            }
            add.setComboBoxItems(comboBox.getItems());
            EHospitalDialog.showDialog(stackPane, rootAdd);

            //Refresh List View and Search Suguestion
            EHospitalDialog.dialog.setOnDialogClosed((JFXDialogEvent event) -> {
                displayStaffName();
                searchStaff();
            });
        } catch (Exception e) {e.printStackTrace();
        }
    }
    
    @FXML
    private void editAction() {
        try {
            if (loaderEdit == null) {
                loaderEdit = new FXMLLoader(getClass().getResource(editFxml));
                rootEdit = loaderEdit.load();
                
                edit=loaderEdit.getController();
            }
            edit.setComboBoxItems(comboBox.getItems());
            edit.setSelectedNameAndID(selectedStaffName,Integer.parseInt(labelID.getText()));
            
            EHospitalDialog.showDialog(stackPane, rootEdit);
            
            //Refresh List View and Search Suguestion
            EHospitalDialog.dialog.setOnDialogClosed((JFXDialogEvent event) -> {
                displayStaffName();
                searchStaff();
                
                listView.getSelectionModel().select(selectedStaffName);
                listView.getSelectionModel().clearSelection();
                
                buttonEdit.setDisable(true);
                buttonDelete.setDisable(true);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void deleteAction() {
        try {
            if (loaderDelete == null) {
                loaderDelete = new FXMLLoader(getClass().getResource(deleteFxml));
                rootDelete = loaderDelete.load();
                
                del=loaderDelete.getController();
                
                del.setLabelID(labelID);
                del.setLabelName(labelName);
                del.setLabelAge(labelAge);
                del.setLabelGender(labelGender);
                del.setLabelAddress(labelAddress);
                del.setLabelCity(labelCity);
                del.setLabelPhone(labelPhone);
                del.setLabelMatStatus(labelMatStatus);
                del.setLabelDateJoined(labelDateJoined);
                del.setLabelSalary(labelSalary);
                del.setLabelEmail(labelEmail);
                del.setLabelDegree(labelDegree);
                del.setImageView(imageView);
                del.setDefaultImage(defaultImage);
            }
            del.setSelectedStaffName(selectedStaffName);
            del.setStaffImageFile(staffImageFile);
            del.setStaffID(Integer.parseInt(labelID.getText()));
            
            EHospitalDialog.showDialog(stackPane, rootDelete);
            
            //Refresh List View and Search Suguestion
            EHospitalDialog.dialog.setOnDialogClosed((JFXDialogEvent event) -> {
                displayStaffName();
                searchStaff();
                
                buttonEdit.setDisable(true);
                buttonDelete.setDisable(true);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public StackPane getStackPane() {
        return stackPane;
    }

}
