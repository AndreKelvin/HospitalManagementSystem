/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitalbloodbank;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSpinner;
import ehospitaldialog.EHospitalDialog;
import ehospitaldb.EHospitalDB;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Andre Kelvin
 */
public class DeleteDonorController {

    @FXML
    private VBox vBox;
    @FXML
    private JFXSpinner spinner;
    @FXML
    private JFXButton buttonYes;
    private BloodDonor selectedDonor;
    private PreparedStatement ps;
    private ResultSet rs;
    private ObservableList obList;
    private byte age;
    private Calendar calender;

    public void setSelectedDonor(BloodDonor selectedDonor) throws SQLException {
        this.selectedDonor = selectedDonor;
    }

    public void setObservableList(ObservableList obList) {
        this.obList = obList;
        calender = new GregorianCalendar();
    }

    private class Delete extends Task<Object> {

        @Override
        protected Object call() throws Exception {

            try {
                ps = EHospitalDB.getCon().prepareStatement("Delete From BLOOD_DONOR Where Name=?");
                ps.setString(1, selectedDonor.getName());
                ps.executeUpdate();

                obList.clear();

                ps = EHospitalDB.getCon().prepareStatement("Select * From BLOOD_DONOR");
                rs = ps.executeQuery();

                while (rs.next()) {
                    age = (byte) (calender.get(Calendar.YEAR) - rs.getDate("Age").toLocalDate().getYear());
                    obList.add(new BloodDonor(rs.getInt("Id"), rs.getString("Name"), age + "", rs.getString("Gender"), rs.getString("Blood_Group"), rs.getString("Donation_Date"),
                            rs.getString("Phone"), rs.getString("Email"), rs.getString("Address")));
                }
            } catch (Exception e) {
                vBox.setDisable(false);
                spinner.setVisible(false);

                Platform.runLater(() -> {
                    EHospitalDialog.dialogAlert(Alert.AlertType.ERROR, "Database connection failure", buttonYes.getScene().getWindow());
                });
            }

            return null;
        }

    }

    @FXML
    private void yesAction() {
        try {
            vBox.setDisable(true);
            spinner.setVisible(true);
            
            Delete task = new Delete();
            task.setOnSucceeded((event) -> {
                vBox.setDisable(false);
                spinner.setVisible(false);

                EHospitalDialog.dialog.close();
                EHospitalDialog.dialogAlert(Alert.AlertType.INFORMATION, "Delete Successful", buttonYes.getScene().getWindow());
            });
            new Thread(task).start();
        } catch (Exception e) {
        }
    }

    @FXML
    private void noAction() {
        EHospitalDialog.dialog.close();
    }

}
