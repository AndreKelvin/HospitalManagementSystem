/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitaldeathbirth;

import com.jfoenix.controls.JFXButton;
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
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
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
public class EHospitalDeathBirthController implements Initializable {

    @FXML
    private StackPane stackPane;
    @FXML
    private JFXListView listView, listViewBirth;
    @FXML
    private JFXButton buttonEdit, buttonDelete, buttonCerti, buttonEditBirth, buttonDeleteBirth, buttonCertiBirth;
    @FXML
    private Label labelID, labelTreat, labelDeath, labelTime, labelDate, labelGender;
    @FXML
    private Label labelMName, labelBirthGender, labelDoc, labelBirthTime, labelBirthDate, labelFName, labelBGroup, labelWeight;
    @FXML
    private JFXTextField textSearch, textBirthSearch;
    private PreparedStatement ps;
    private ResultSet rs;
    private List searchList, searchBirthList;
    private SuggestionProvider suggestions, suggestionsBirth;
    private FXMLLoader loaderAdd, loaderEdit, loaderDelete, loaderCerti, loaderAddBirth, loaderEditBirth, loaderDeleteBirth, loaderCertiBirth;
    private Parent rootAdd, rootEdit, rootDelete, rootCerti, rootAddBirth, rootEditBirth, rootDeleteBirth, rootCertiBirth;
    private JFXDialog dialogCerti, dialogCertiBirth;
    private AddDeathController add;
    private EditDeathController edit;
    private DeleteDeathController del;
    private DeathCeritificateController death;
    private String selectedDeadPatient, selectedBaby;
    private AddBirthController addBirth;
    private EditBirthController editBirth;
    private DeleteBirthController delBirth;
    private BirthCertificateController birthCerti;

    /**
     * Populate Search Auto Complete Textfield with Death Patient Name
     */
    public void searchDeadPatient() {
        try {
            searchList.clear();
            ps = EHospitalDB.getCon().prepareStatement("Select Name From DEATH");
            rs = ps.executeQuery();

            while (rs.next()) {
                searchList.add(rs.getString("Name"));
            }
            ps.close();

            suggestions.clearSuggestions();
            suggestions.addPossibleSuggestions(searchList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Populate Search Auto Complete Textfield with baby Names
     */
    public void searchBabies() {
        try {
            searchBirthList.clear();
            ps = EHospitalDB.getCon().prepareStatement("Select Baby From BIRTH");
            rs = ps.executeQuery();

            while (rs.next()) {
                searchBirthList.add(rs.getString("Baby"));
            }
            ps.close();

            suggestionsBirth.clearSuggestions();
            suggestionsBirth.addPossibleSuggestions(searchBirthList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            searchList = new ArrayList();
            suggestions = SuggestionProvider.create(searchList);
            searchBirthList = new ArrayList();
            suggestionsBirth = SuggestionProvider.create(searchBirthList);
            
            AutoCompletionTextFieldBinding autoCompletionTextFieldBinding = new AutoCompletionTextFieldBinding<>(textSearch, suggestions);
            AutoCompletionTextFieldBinding autoCompletionTextFieldBind = new AutoCompletionTextFieldBinding<>(textBirthSearch, suggestionsBirth);

            buttonCerti.setDisable(true);
            buttonDelete.setDisable(true);
            buttonEdit.setDisable(true);
            buttonCertiBirth.setDisable(true);
            buttonDeleteBirth.setDisable(true);
            buttonEditBirth.setDisable(true);

            listView.getSelectionModel().selectedItemProperty().addListener((ObservableValue observable, Object oldValue, Object newValue) -> {
                buttonCerti.setDisable(false);
                buttonDelete.setDisable(false);
                buttonEdit.setDisable(false);

                try {
                    selectedDeadPatient = newValue.toString();

                    ps = EHospitalDB.getCon().prepareStatement("Select * From DEATH Where Name=?");
                    ps.setString(1, selectedDeadPatient);
                    rs = ps.executeQuery();

                    if (rs.next()) {
                        labelID.setText(rs.getString("ID"));
                        labelDate.setText(rs.getDate("Date").toString());
                        labelTime.setText(rs.getString("Time"));
                        labelDeath.setText(rs.getString("Course_Of_Death"));
                        labelTreat.setText(rs.getString("Treatment"));
                        labelGender.setText(rs.getString("Gender"));
                    }
                } catch (Exception e) {
                }
                //textSearch.clear();
            });
            
            listViewBirth.getSelectionModel().selectedItemProperty().addListener((ObservableValue observable, Object oldValue, Object newValue) -> {
                buttonCertiBirth.setDisable(false);
                buttonDeleteBirth.setDisable(false);
                buttonEditBirth.setDisable(false);

                try {
                    selectedBaby=newValue.toString();
                    
                    ps = EHospitalDB.getCon().prepareStatement("Select * From BIRTH Where Baby=?");
                    ps.setString(1, selectedBaby);
                    rs = ps.executeQuery();

                    if (rs.next()) {
                        labelMName.setText(rs.getString("Mother"));
                        labelBirthDate.setText(rs.getDate("Date").toString());
                        labelBirthTime.setText(rs.getString("Time"));
                        labelFName.setText(rs.getString("Father"));
                        labelBGroup.setText(rs.getString("Blood_Group"));
                        labelBirthGender.setText(rs.getString("Gender"));
                        labelDoc.setText(rs.getString("Doctor"));
                        labelWeight.setText(rs.getString("Weight"));
                    }
                } catch (Exception e) {
                }
                //textBirthSearch.clear();
            });

            textSearch.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                listView.getSelectionModel().select(newValue);
            });
            
            textBirthSearch.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                listViewBirth.getSelectionModel().select(newValue);
            });

        } catch (Exception e) {
        }
    }

    public void populateListView() {
        try {
            listView.getItems().clear();
            ps = EHospitalDB.getCon().prepareStatement("Select Name From DEATH");
            rs = ps.executeQuery();

            while (rs.next()) {
                listView.getItems().add(rs.getString("Name"));
            }
        } catch (Exception e) {
        }
    }
    
    /**
     * This Method Refresh the List View With Baby Names
     */
    public void populateListViewBirth() {
        try {
            listViewBirth.getItems().clear();
            ps = EHospitalDB.getCon().prepareStatement("Select Baby From BIRTH");
            rs = ps.executeQuery();

            while (rs.next()) {
                listViewBirth.getItems().add(rs.getString("Baby"));
            }
        } catch (Exception e) {
        }
    }

    @FXML
    private void openAddDialog() {
        try {
            if (loaderAdd == null) {
                loaderAdd = new FXMLLoader(getClass().getResource("AddDeath.fxml"));
                rootAdd = loaderAdd.load();
                add = loaderAdd.getController();
            }
            add.populateComboBox();
            EHospitalDialog.showDialog(stackPane, rootAdd);

            EHospitalDialog.dialog.setOnDialogClosed((JFXDialogEvent event) -> {
                populateListView();
                searchDeadPatient();
            });
        } catch (Exception e) {
        }
    }

    @FXML
    private void openAddBirthDialog() {
        try {
            if (loaderAddBirth == null) {
                loaderAddBirth = new FXMLLoader(getClass().getResource("AddBirth.fxml"));
                rootAddBirth = loaderAddBirth.load();
                
                addBirth=loaderAddBirth.getController();
            }
            addBirth.populateComboBox();
            EHospitalDialog.showDialog(stackPane, rootAddBirth);

            EHospitalDialog.dialog.setOnDialogClosed((JFXDialogEvent event) -> {
                populateListViewBirth();
                searchBabies();
            });
        } catch (Exception e) {
        }
    }

    @FXML
    private void openEditDialog() {
        try {
            if (loaderEdit == null) {
                loaderEdit = new FXMLLoader(getClass().getResource("EditDeath.fxml"));
                rootEdit = loaderEdit.load();
                edit = loaderEdit.getController();
            }
            edit.getSelectedDeadPatient(selectedDeadPatient);

            EHospitalDialog.showDialog(stackPane, rootEdit);

            EHospitalDialog.dialog.setOnDialogClosed((JFXDialogEvent event) -> {
                populateListView();
                searchDeadPatient();

                listView.getSelectionModel().select(selectedDeadPatient);

                buttonCerti.setDisable(true);
                buttonDelete.setDisable(true);
                buttonEdit.setDisable(true);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openEditBirthDialog() {
        try {
            if (loaderEditBirth == null) {
                loaderEditBirth = new FXMLLoader(getClass().getResource("EditBirth.fxml"));
                rootEditBirth = loaderEditBirth.load();
                editBirth=loaderEditBirth.getController();
            }
            editBirth.populateComboBox();
            editBirth.setSelectedBaby(selectedBaby);
            
            EHospitalDialog.showDialog(stackPane, rootEditBirth);
            
            EHospitalDialog.dialog.setOnDialogClosed((JFXDialogEvent event) -> {
                populateListViewBirth();
                searchBabies();
                
                listView.getSelectionModel().select(selectedBaby);
                
                buttonCertiBirth.setDisable(true);
                buttonDeleteBirth.setDisable(true);
                buttonEditBirth.setDisable(true);
            });
        } catch (Exception e) {
        }
    }

    @FXML
    private void openDeleteDialog() {
        try {
            if (loaderDelete == null) {
                loaderDelete = new FXMLLoader(getClass().getResource("DeleteDeath.fxml"));
                rootDelete = loaderDelete.load();

                del = loaderDelete.getController();
            }
            del.getSelectedDeadPatient(selectedDeadPatient);

            EHospitalDialog.showDialog(stackPane, rootDelete);

            EHospitalDialog.dialog.setOnDialogClosed((JFXDialogEvent event) -> {
                populateListView();
                searchDeadPatient();

                buttonCerti.setDisable(true);
                buttonDelete.setDisable(true);
                buttonEdit.setDisable(true);
            });
        } catch (Exception e) {
        }
    }

    @FXML
    private void openDeleteBirthDialog() {
        try {
            if (loaderDeleteBirth == null) {
                loaderDeleteBirth = new FXMLLoader(getClass().getResource("DeleteBirth.fxml"));
                rootDeleteBirth = loaderDeleteBirth.load();
                
                delBirth=loaderDeleteBirth.getController();
            }
            delBirth.setSelectedBaby(selectedBaby);
            
            EHospitalDialog.showDialog(stackPane, rootDeleteBirth);
            
            EHospitalDialog.dialog.setOnDialogClosed((JFXDialogEvent event) -> {
                populateListViewBirth();
                searchBabies();
                
                buttonCertiBirth.setDisable(true);
                buttonDeleteBirth.setDisable(true);
                buttonEditBirth.setDisable(true);
            });
        } catch (Exception e) {
        }
    }

    @FXML
    private void openCartificateDialog() {
        try {
            if (loaderCerti == null) {
                loaderCerti = new FXMLLoader(getClass().getResource("DeathCeritificate.fxml"));
                rootCerti = loaderCerti.load();

                dialogCerti = new JFXDialog(stackPane, (Region) rootCerti, JFXDialog.DialogTransition.TOP);

                death = loaderCerti.getController();
            }
            death.getSelectedDeadPatient(selectedDeadPatient);

            dialogCerti.show();

            dialogCerti.setOnDialogClosed((JFXDialogEvent event) -> {
                buttonCerti.setDisable(true);
                buttonDelete.setDisable(true);
                buttonEdit.setDisable(true);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openBirthCartificateDialog() {
        try {
            if (loaderCertiBirth == null) {
                loaderCertiBirth = new FXMLLoader(getClass().getResource("BirthCertificate.fxml"));
                rootCertiBirth = loaderCertiBirth.load();

                dialogCertiBirth = new JFXDialog(stackPane, (Region) rootCertiBirth, JFXDialog.DialogTransition.TOP);
                
                birthCerti=loaderCertiBirth.getController();
            }
            birthCerti.setSelectedBaby(selectedBaby);
            
            dialogCertiBirth.show();
            
            dialogCertiBirth.setOnDialogClosed((JFXDialogEvent event) -> {
                buttonCertiBirth.setDisable(true);
                buttonDeleteBirth.setDisable(true);
                buttonEditBirth.setDisable(true);
            });
        } catch (Exception e) {
        }
    }
    
}
