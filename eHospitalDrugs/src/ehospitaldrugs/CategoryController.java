/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitaldrugs;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import ehospitaldialog.EHospitalDialog;
import ehospitaldb.EHospitalDB;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
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
public class CategoryController implements Initializable {

    @FXML
    private JFXSpinner spinner;
    @FXML
    private VBox vBox;
    @FXML
    private JFXListView listView;
    @FXML
    private JFXTextField textCate;
    @FXML
    private JFXButton buttonEdit, buttonRemove;
    private ObservableList obList;
    private PreparedStatement ps;
    private ResultSet rs;
    private String name, selectedValue;
    private int index;
    private Task task;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            obList = FXCollections.observableArrayList();
            listView.setItems(obList);

            ps = EHospitalDB.getCon().prepareStatement("Select * From DRUG_CATEGORY");
            rs = ps.executeQuery();

            while (rs.next()) {
                obList.add(rs.getString("CATEGORY_NAME"));
            }

            buttonEdit.setDisable(true);
            buttonRemove.setDisable(true);

            listView.getSelectionModel().selectedItemProperty().addListener((ObservableValue observable, Object oldValue, Object newValue) -> {
                if (newValue == null) {

                } else {
                    buttonEdit.setDisable(false);
                    buttonRemove.setDisable(false);

                    selectedValue = newValue.toString();

                    textCate.setText(selectedValue);
                }
            });

        } catch (Exception e) {
        }
    }

    @FXML
    private void addAction() {
        name = textCate.getText();

        if (name.isEmpty()) {
            EHospitalDialog.dialogAlert(Alert.AlertType.ERROR, "Please Fill The Required Input", textCate.getScene().getWindow());
        } else {
            vBox.setDisable(true);
            spinner.setVisible(true);

            task = new Task() {
                @Override
                protected Object call() throws Exception {

                    try {
                        ps = EHospitalDB.getCon().prepareStatement("Insert Into DRUG_CATEGORY Values(?)");
                        ps.setString(1, name);
                        ps.executeUpdate();

                    } catch (Exception e) {
                        vBox.setDisable(false);
                        spinner.setVisible(false);

                        Platform.runLater(() -> {
                            EHospitalDialog.dialogAlert(Alert.AlertType.ERROR, "Database connection failure", textCate.getScene().getWindow());
                        });
                    }

                    return null;
                }
            };

            task.setOnSucceeded((event) -> {
                vBox.setDisable(false);
                spinner.setVisible(false);

                obList.add(name);

                EHospitalDialog.dialogAlert(Alert.AlertType.INFORMATION, "Add Successfull", textCate.getScene().getWindow());
                textCate.clear();
            });

            new Thread(task).start();
        }
    }

    @FXML
    private void editAction() {
        name = textCate.getText();

        if (name.isEmpty()) {
            EHospitalDialog.dialogAlert(Alert.AlertType.ERROR, "Please Fill The Required Input", textCate.getScene().getWindow());
        } else {
            vBox.setDisable(true);
            spinner.setVisible(true);

            task = new Task() {
                @Override
                protected Object call() throws Exception {

                    try {
                        ps = EHospitalDB.getCon().prepareStatement("Update DRUG_CATEGORY Set CATEGORY_NAME=? Where CATEGORY_NAME=?");
                        ps.setString(1, name);
                        ps.setString(2, selectedValue);
                        ps.executeUpdate();

                    } catch (Exception e) {
                        vBox.setDisable(false);
                        spinner.setVisible(false);

                        Platform.runLater(() -> {
                            EHospitalDialog.dialogAlert(Alert.AlertType.ERROR, "Database connection failure", textCate.getScene().getWindow());
                        });
                    }

                    return null;
                }
            };

            task.setOnSucceeded((event) -> {
                vBox.setDisable(false);
                spinner.setVisible(false);

                index = obList.indexOf(selectedValue);
                obList.remove(selectedValue);
                obList.add(index, name);

                textCate.clear();
                listView.getSelectionModel().clearSelection();
                EHospitalDialog.dialogAlert(Alert.AlertType.INFORMATION, "Edit Successfull", textCate.getScene().getWindow());

                buttonEdit.setDisable(true);
                buttonRemove.setDisable(true);
            });

            new Thread(task).start();
        }
    }

    @FXML
    private void removeAction() {
        vBox.setDisable(true);
        spinner.setVisible(true);

        task = new Task() {
            @Override
            protected Object call() throws Exception {

                try {
                    ps = EHospitalDB.getCon().prepareStatement("Delete From DRUG_CATEGORY Where CATEGORY_NAME=?");
                    ps.setString(1, selectedValue);
                    ps.executeUpdate();

                } catch (Exception e) {
                    vBox.setDisable(false);
                    spinner.setVisible(false);

                    Platform.runLater(() -> {
                        EHospitalDialog.dialogAlert(Alert.AlertType.ERROR, "Database connection failure", textCate.getScene().getWindow());
                    });
                }

                return null;
            }
        };

        task.setOnSucceeded((event) -> {
            vBox.setDisable(false);
            spinner.setVisible(false);

            obList.remove(selectedValue);
            textCate.clear();
            listView.getSelectionModel().clearSelection();
            EHospitalDialog.dialogAlert(Alert.AlertType.INFORMATION, "Remove Successfull", textCate.getScene().getWindow());

            buttonEdit.setDisable(true);
            buttonRemove.setDisable(true);
        });

        new Thread(task).start();
    }

}
