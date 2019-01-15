/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitaldeathbirthcontroller;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import ehospitaldb.EHospitalDB;
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
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 *
 * @author Andre Kelvin
 */
public abstract class EHospitalDeathBirthController implements Initializable {
    
    @FXML
    public JFXListView listViewDeath, listViewBirth;
    @FXML
    private Label labelID, labelTreat, labelDeath, labelDeathTime;
    @FXML
    private Label labelDeathDate, labelDeathGender, labelMName, labelBirthGender, labelBirthTime, labelBirthDate, labelFName, labelBGroup, labelWeight;
    @FXML
    private JFXTextField textBirthSearch, textDeathSearch;
    public List searchDeathList, searchBirthList;
    public PreparedStatement ps;
    public ResultSet rs;
    public SuggestionProvider deathSuggestions, birthSuggestions;

    /**
     * Populate Search Auto Complete Textfield with Death Patient Name
     */
    public abstract void searchDeadPatient();

    public abstract void searchBabies();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            searchDeathList = new ArrayList();
            searchBirthList = new ArrayList();
            
            deathSuggestions = SuggestionProvider.create(searchDeathList);
            birthSuggestions = SuggestionProvider.create(searchBirthList);

            AutoCompletionTextFieldBinding autoCompletionTextFieldBinding = new AutoCompletionTextFieldBinding<>(textDeathSearch, deathSuggestions);
            AutoCompletionTextFieldBinding autoBinding = new AutoCompletionTextFieldBinding<>(textBirthSearch, birthSuggestions);

            listViewDeath.getSelectionModel().selectedItemProperty().addListener((ObservableValue observable, Object oldValue, Object newValue) -> {

                try {
                    ps = EHospitalDB.getCon().prepareStatement("Select * From DEATH Where NAME=?");
                    ps.setString(1, newValue.toString());
                    rs = ps.executeQuery();

                    if (rs.next()) {
                        labelID.setText(rs.getString("ID"));
                        labelDeathDate.setText(rs.getDate("DATE").toString());
                        labelDeathTime.setText(rs.getString("TIME"));
                        labelDeath.setText(rs.getString("COURSE_OF_DEATH"));
                        labelTreat.setText(rs.getString("TREATMENT"));
                        labelDeathGender.setText(rs.getString("GENDER"));
                    }
                } catch (Exception e) {
                }
                //textSearch.clear();
            });

            listViewBirth.getSelectionModel().selectedItemProperty().addListener((ObservableValue observable, Object oldValue, Object newValue) -> {

                try {
                    ps = EHospitalDB.getCon().prepareStatement("Select * From BIRTH Where BABY=?");
                    ps.setString(1, newValue.toString());
                    rs = ps.executeQuery();

                    if (rs.next()) {
                        labelMName.setText(rs.getString("MOTHER"));
                        labelBirthDate.setText(rs.getDate("DATE").toString());
                        labelBirthTime.setText(rs.getString("TIME"));
                        labelFName.setText(rs.getString("FATHER"));
                        labelBGroup.setText(rs.getString("BLOOD_GROUP"));
                        labelBirthGender.setText(rs.getString("GENDER"));
                        labelWeight.setText(rs.getString("WEIGHT"));
                    }
                } catch (Exception e) {
                }
                //textSearch.clear();
            });

            textDeathSearch.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                listViewDeath.getSelectionModel().select(newValue);
            });

            textBirthSearch.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                listViewBirth.getSelectionModel().select(newValue);
            });
            
            textDeathSearch.setOnMouseEntered((event) -> {
                searchDeadPatient();
            });
            
            textBirthSearch.setOnMouseEntered((event) -> {
                searchBabies();
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public abstract void populateDeathListView();

    public abstract void populateBabyListView();
    
}
