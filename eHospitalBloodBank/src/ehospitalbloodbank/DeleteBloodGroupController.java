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
public class DeleteBloodGroupController {

    @FXML
    private VBox vBox;
    @FXML
    private JFXSpinner spinner;
    @FXML
    private JFXButton buttonYes;
    private PreparedStatement ps;
    private ObservableList obList;
    private ResultSet rs;
    private BloodGroup selectedValue;

    public void setSelectedValue(BloodGroup selectedValue) throws SQLException {
        this.selectedValue = selectedValue;
    }

    public void setObList(ObservableList obList) {
        this.obList = obList;
    }

    private class Delete extends Task<Object> {

        @Override
        protected Object call() throws Exception {

            try {
                ps = EHospitalDB.getCon().prepareStatement("Delete From BLOOD_GROUP Where NAME=?");
                ps.setString(1, selectedValue.getBlood());
                ps.executeUpdate();

                //refresh table
                obList.clear();
                ps = EHospitalDB.getCon().prepareStatement("Select * From BLOOD_GROUP");
                rs = ps.executeQuery();

                while (rs.next()) {
                    obList.add(new BloodGroup(rs.getString("NAME"), Integer.parseInt(rs.getString("BAGS"))));
                }
                ps.close();
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
    }

    @FXML
    private void noAction() {
        EHospitalDialog.dialog.close();
    }
}
