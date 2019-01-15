/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitaltreatment;

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
public class EditTreatmentController implements Initializable {

    @FXML
    private VBox vBox;
    @FXML
    private JFXSpinner spinner;
    @FXML
    private JFXTextField textTreat, textPrice;
    private Treatment selectedTreatment;
    private PreparedStatement ps;
    private ResultSet rs;
    private String treatment, price;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Force the Textfield to be Numeric only
        textPrice.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                textPrice.setText(newValue.replaceAll("[^\\d]", ""));
                Toolkit.getDefaultToolkit().beep();
            }
        });
    }

    public void setselectedTreatment(Treatment selectedTreatment) throws SQLException {
        this.selectedTreatment = selectedTreatment;

        ps = EHospitalDB.getCon().prepareStatement("Select * From TREATMENT Where Treatment_Name=?");
        ps.setString(1, selectedTreatment.getTreatment());
        rs = ps.executeQuery();

        if (rs.next()) {
            textTreat.setText(rs.getString("Treatment_Name"));
            textPrice.setText(rs.getInt("Price") + "");
        }
    }

    private class EditTask extends Task<Object> {

        @Override
        protected Object call() throws Exception {

            try {
                ps = EHospitalDB.getCon().prepareStatement("Update TREATMENT Set Treatment_Name=?,Price=? Where Treatment_Name=?");
                ps.setString(1, treatment);
                ps.setInt(2, Integer.parseInt(price));
                ps.setString(3, selectedTreatment.getTreatment());
                ps.executeUpdate();

                selectedTreatment.setTreatment(treatment);
                selectedTreatment.setPrice(Integer.parseInt(price));
            } catch (Exception e) {
                vBox.setDisable(false);
                spinner.setVisible(false);

                Platform.runLater(() -> {
                    EHospitalDialog.dialogAlert(Alert.AlertType.ERROR, "Database connection failure", textPrice.getScene().getWindow());
                });
            }

            return null;
        }

    }

    @FXML
    private void saveAction() {
        treatment = textTreat.getText().trim();
        price = textPrice.getText().trim();

        if (treatment.isEmpty() || price.isEmpty()) {
            EHospitalDialog.dialogAlert(Alert.AlertType.ERROR, "Please Fill All Required Input", textPrice.getScene().getWindow());
        } else {
            vBox.setDisable(true);
            spinner.setVisible(true);

            EditTask task = new EditTask();
            task.setOnSucceeded((event) -> {
                vBox.setDisable(false);
                spinner.setVisible(false);

                EHospitalDialog.dialog.close();
                EHospitalDialog.dialogAlert(Alert.AlertType.INFORMATION, "Save Successful", textPrice.getScene().getWindow());

                textTreat.clear();
                textPrice.clear();
            });
            new Thread(task).start();
        }
    }

}
