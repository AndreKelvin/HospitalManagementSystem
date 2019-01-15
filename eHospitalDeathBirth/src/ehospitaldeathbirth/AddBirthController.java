/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitaldeathbirth;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import ehospitaldb.EHospitalDB;
import ehospitaldialog.EHospitalDialog;
import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Andre Kelvin
 */
public class AddBirthController implements Initializable {

    @FXML
    private VBox vBox;
    @FXML
    private JFXSpinner spinner;
    @FXML
    private JFXTextField textName, textMName, textFName, textWeight;
    @FXML
    private JFXRadioButton radioM;
    @FXML
    private ToggleGroup tg1;
    @FXML
    private JFXDatePicker datePicker;
    @FXML
    private JFXTimePicker TimePicker;
    @FXML
    private JFXComboBox comboDoc, comboBGroup;
    private PreparedStatement ps;
    private ResultSet rs;
    private String baby, mother, father, weight;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            populateComboBox();

            TimePicker.setEditable(false);
        } catch (Exception e) {
        }
    }

    public void populateComboBox() throws SQLException {
        ps = EHospitalDB.getCon().prepareStatement("Select Name From DOCTOR");
        rs = ps.executeQuery();

        comboDoc.getItems().clear();
        while (rs.next()) {
            comboDoc.getItems().add(rs.getString("Name"));
        }

        ps = EHospitalDB.getCon().prepareStatement("Select Name From BLOOD_GROUP");
        rs = ps.executeQuery();

        comboBGroup.getItems().clear();
        while (rs.next()) {
            comboBGroup.getItems().add(rs.getString("Name"));
        }
    }

    private class SaveTask extends Task<Object> {

        @Override
        protected Object call() throws Exception {

            try {
                ps = EHospitalDB.getCon().prepareStatement("Insert Into BIRTH Values(?,?,?,?,?,?,?,?,?)");
                ps.setString(1, baby);
                ps.setString(2, mother);
                ps.setString(3, father);
                ps.setDate(4, Date.valueOf(datePicker.getValue()));
                ps.setString(5, TimePicker.getValue().format(DateTimeFormatter.ofPattern("hh:mm a")));
                ps.setString(6, comboDoc.getValue().toString());

                if (radioM.isSelected()) {
                    ps.setString(7, "M");
                } else {
                    ps.setString(7, "F");
                }

                ps.setString(8, weight);
                ps.setString(9, comboBGroup.getValue().toString());
                ps.executeUpdate();
            } catch (Exception e) {
                vBox.setDisable(false);
                spinner.setVisible(false);

                Platform.runLater(() -> {
                    EHospitalDialog.dialogAlert(Alert.AlertType.ERROR, "Database connection failure", textFName.getScene().getWindow());
                });
            }

            return null;
        }

    }

    @FXML
    private void save() {
        baby = textName.getText().trim();
        mother = textMName.getText().trim();
        father = textFName.getText().trim();
        weight = textWeight.getText().trim();

        vBox.setDisable(true);
        spinner.setVisible(true);

        if (baby.isEmpty() || mother.isEmpty() || father.isEmpty() || weight.isEmpty() || comboBGroup.getValue() == null || comboDoc.getValue() == null
                || datePicker.getValue() == null || TimePicker.getValue() == null || tg1.getSelectedToggle() == null) {
            EHospitalDialog.dialogAlert(Alert.AlertType.ERROR, "Please Fill All Required Input", textFName.getScene().getWindow());
        } else {
            SaveTask task = new SaveTask();
            task.setOnSucceeded((event) -> {
                vBox.setDisable(false);
                spinner.setVisible(false);

                EHospitalDialog.dialogAlert(Alert.AlertType.INFORMATION, "Save Successfull", textFName.getScene().getWindow());

                textFName.clear();
                textMName.clear();
                textName.clear();
                textName.clear();
                textWeight.clear();
                comboBGroup.setValue(null);
                comboDoc.setValue(null);
                datePicker.setValue(null);
                TimePicker.setValue(null);
                tg1.getSelectedToggle().setSelected(false);
            });
            new Thread(task).start();

        }
    }

}
