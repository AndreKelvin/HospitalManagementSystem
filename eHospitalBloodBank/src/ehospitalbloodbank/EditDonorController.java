/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitalbloodbank;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import ehospitaldialog.EHospitalDialog;
import ehospitaldb.EHospitalDB;
import java.awt.Toolkit;
import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
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
public class EditDonorController implements Initializable {

    @FXML
    private VBox vBox;
    @FXML
    private JFXSpinner spinner;
    @FXML
    private JFXDatePicker datePickerBirth;
    @FXML
    private JFXRadioButton radioM, radioF;
    @FXML
    private ToggleGroup tg;
    @FXML
    private JFXComboBox comboBloodGroup;
    @FXML
    private JFXDatePicker datePickerDonation;
    @FXML
    private JFXTextField textPhone, textEmail, textAddress, textName;
    private PreparedStatement ps;
    private ResultSet rs;
    private byte age;
    private Calendar calender;
    private BloodDonor selectedDonor;
    private String name, phone, email, address, selectedGender, bloodGroup, dateDonation;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            calender = new GregorianCalendar();

            populateBloodGroupComboBox();

            //Force the text field to be numeric only
            textPhone.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                if (!newValue.matches("\\d*")) {
                    textPhone.setText(newValue.replaceAll("[^\\d]", ""));
                    Toolkit.getDefaultToolkit().beep();
                }
            });
        } catch (Exception e) {
        }
    }

    public void populateBloodGroupComboBox() throws SQLException {
        ps = EHospitalDB.getCon().prepareStatement("Select NAME From BLOOD_GROUP");
        rs = ps.executeQuery();

        comboBloodGroup.getItems().clear();
        while (rs.next()) {
            comboBloodGroup.getItems().add(rs.getString("NAME"));
        }
    }

    public void setSelectedDonor(BloodDonor selectedDonor) throws SQLException {
        this.selectedDonor = selectedDonor;

        ps = EHospitalDB.getCon().prepareStatement("Select * From BLOOD_DONOR Where NAME=?");
        ps.setString(1, selectedDonor.getName());
        rs = ps.executeQuery();

        if (rs.next()) {
            textName.setText(rs.getString("NAME"));
            textAddress.setText(rs.getString("ADDRESS"));
            textEmail.setText(rs.getString("EMAIL"));
            textPhone.setText(rs.getString("PHONE"));
            datePickerBirth.setValue(rs.getDate("AGE").toLocalDate());
            datePickerDonation.setValue(rs.getDate("DONATION_DATE").toLocalDate());
            comboBloodGroup.setValue(rs.getString("BLOOD_GROUP"));
            if (rs.getString("GENDER").contentEquals("M")) {
                radioM.setSelected(true);
            } else {
                radioF.setSelected(true);
            }
        }
    }

    private class SaveEdit extends Task<Object> {

        @Override
        protected Object call() throws Exception {

            try {
                bloodGroup = comboBloodGroup.getValue().toString();
                age = (byte) (calender.get(Calendar.YEAR) - datePickerBirth.getValue().getYear());
                dateDonation = datePickerDonation.getValue().toString();

                ps = EHospitalDB.getCon().prepareStatement("Update BLOOD_DONOR Set Name=?,Age=?,Gender=?,Blood_Group=?,Donation_Date=?,Phone=?,Email=?,Address=?"
                        + "Where Name=?");
                ps.setString(1, name);
                ps.setDate(2, Date.valueOf(datePickerBirth.getValue()));

                if (radioM.isSelected()) {
                    ps.setString(3, radioM.getText());
                    selectedGender = radioM.getText();
                } else {
                    ps.setString(3, radioF.getText());
                    selectedGender = radioF.getText();
                }

                ps.setString(4, bloodGroup);
                ps.setDate(5, Date.valueOf(dateDonation));
                ps.setString(6, phone);
                ps.setString(7, email);
                ps.setString(8, address);
                ps.setString(9, selectedDonor.getName());
                ps.executeUpdate();

                selectedDonor.setName(name);
                selectedDonor.setAge(age + "");
                selectedDonor.setSex(selectedGender);
                selectedDonor.setBloodGroup(bloodGroup);
                selectedDonor.setDonationDate(dateDonation);
                selectedDonor.setPhone(phone);
                selectedDonor.setEmail(email);
                selectedDonor.setAddress(address);
            } catch (Exception e) {
                vBox.setDisable(false);
                spinner.setVisible(false);

                Platform.runLater(() -> {
                    EHospitalDialog.dialogAlert(Alert.AlertType.ERROR, "Database connection failure", textAddress.getScene().getWindow());
                });
            }

            return null;
        }

    }

    @FXML
    private void save() {
        name = textName.getText().trim();
        address = textAddress.getText().trim();
        phone = textPhone.getText().trim();
        email = textEmail.getText().trim();

        if (name.isEmpty() || address.isEmpty() || phone.isEmpty() || email.isEmpty() || comboBloodGroup.getValue() == null || datePickerBirth.getValue() == null
                || datePickerDonation.getValue() == null || tg.getSelectedToggle() == null) {
            EHospitalDialog.dialogAlert(Alert.AlertType.ERROR, "Please Fill All Required Input", textAddress.getScene().getWindow());
        } else {
            vBox.setDisable(true);
            spinner.setVisible(true);

            SaveEdit task = new SaveEdit();
            task.setOnSucceeded((event) -> {
                vBox.setDisable(false);
                spinner.setVisible(false);

                EHospitalDialog.dialog.close();
                EHospitalDialog.dialogAlert(Alert.AlertType.INFORMATION, "Save Successful", textAddress.getScene().getWindow());

                textAddress.clear();
                textEmail.clear();
                textName.clear();
                textPhone.clear();
                comboBloodGroup.setValue(null);
                datePickerBirth.setValue(null);
                datePickerDonation.setValue(null);
                tg.getSelectedToggle().setSelected(false);
            });
            new Thread(task).start();
        }
    }

}
