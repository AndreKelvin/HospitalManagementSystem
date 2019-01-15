/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitalwardroomtabpane;

import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import ehospitaldialog.EHospitalDialog;
import ehospitaldb.EHospitalDB;
import java.awt.Toolkit;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Andre Kelvin
 */
public class EditRoomController implements Initializable {

    @FXML
    private VBox vBox;
    @FXML
    private JFXSpinner spinner;
    @FXML
    private JFXTextField textFloor, textRoom;
    private Room selectedValue;
    private PreparedStatement ps;
    private ResultSet rs;
    private String floor, room;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Force the text field to be numeric only
        textRoom.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                textRoom.setText(newValue.replaceAll("[^\\d]", ""));
                Toolkit.getDefaultToolkit().beep();
            }
        });
    }

    public void setSelectedValue(Room selectedValue) throws SQLException {
        this.selectedValue = selectedValue;

        ps = EHospitalDB.getCon().prepareStatement("Select * From ROOM Where Floor=?");
        ps.setString(1, selectedValue.getFloor());
        rs = ps.executeQuery();

        while (rs.next()) {
            textFloor.setText(rs.getString("Floor"));
            textRoom.setText(rs.getString("Room"));
        }
        ps.close();
    }

    private class EditTask extends Task<Object> {

        @Override
        protected Object call() throws Exception {

            try {
                ps = EHospitalDB.getCon().prepareStatement("Update ROOM Set Floor=?,Room=? Where Floor=?");
                ps.setString(1, floor);
                ps.setInt(2, Integer.parseInt(room));
                ps.setString(3, selectedValue.getFloor());
                ps.executeUpdate();
                ps.close();

                selectedValue.setFloor(floor);
                selectedValue.setRoom(Integer.parseInt(room));
            } catch (Exception e) {
                vBox.setDisable(false);
                spinner.setVisible(false);

                Platform.runLater(() -> {
                    EHospitalDialog.dialogAlert(Alert.AlertType.ERROR, "Database connection failure", textFloor.getScene().getWindow());
                });
            }

            return null;
        }

    }

    @FXML
    private void saveAction() {
        floor = textFloor.getText().trim();
        room = textRoom.getText().trim();

        if (floor.isEmpty() || room.isEmpty()) {
            EHospitalDialog.dialogAlert(Alert.AlertType.ERROR, "Please Fill All Required Input", textRoom.getScene().getWindow());
        } else {
            vBox.setDisable(true);
            spinner.setVisible(true);

            EditTask task = new EditTask();
            task.setOnSucceeded((event) -> {
                vBox.setDisable(false);
                spinner.setVisible(false);

                EHospitalDialog.dialog.close();
                EHospitalDialog.dialogAlert(Alert.AlertType.INFORMATION, "Save Successful", textRoom.getScene().getWindow());
            });
            new Thread(task).start();
        }
    }

}
