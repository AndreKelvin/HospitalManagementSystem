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
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
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
public class AddTreatmentController implements Initializable {

    @FXML
    private VBox vBox;
    @FXML
    private JFXSpinner spinner;
    @FXML
    private JFXTextField textTreat, textPrice;
    private PreparedStatement ps;
    private ObservableList obList;
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

    public void setObList(ObservableList obList) {
        this.obList = obList;
    }

    private class SaveTask extends Task<Object> {

        @Override
        protected Object call() throws Exception {

            try {
                ps = EHospitalDB.getCon().prepareStatement("Insert Into TREATMENT Values(?,?)");
                ps.setString(1, treatment);
                ps.setInt(2, Integer.parseInt(price));
                ps.executeUpdate();

                obList.add(new Treatment(treatment, Integer.parseInt(price)));
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

            SaveTask task = new SaveTask();
            task.setOnSucceeded((event) -> {
                vBox.setDisable(false);
                spinner.setVisible(false);

                EHospitalDialog.dialogAlert(Alert.AlertType.INFORMATION, "Save Successful", textPrice.getScene().getWindow());

                textTreat.clear();
                textPrice.clear();
            });
            new Thread(task).start();
        }
    }

}
