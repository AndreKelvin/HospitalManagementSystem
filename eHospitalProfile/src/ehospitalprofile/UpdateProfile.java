/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitalprofile;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import ehospitalbridge.EHospitalBridge;
import ehospitaldialog.EHospitalDialog;
import java.awt.Toolkit;
import java.io.File;
import java.net.URL;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author Andre Kelvin
 */
public abstract class UpdateProfile implements Initializable {

    @FXML
    private JFXDatePicker datePicker;
    @FXML
    private BorderPane bPane;
    @FXML
    private JFXSpinner spinner;
    @FXML
    private JFXTextField textName, textAddress, textCity, textPhone, textEmail, textDegree;
    @FXML
    private ToggleGroup tg, tgGender;
    @FXML
    public JFXRadioButton radioM, radioF, radioSingle, radioMar, radioDiv;
    @FXML
    private ImageView imageView;
    public PreparedStatement ps;
    public String name, address, city, degree, email, phone, selectedName, selectedEmail;
    public LocalDate date;
    private FileChooser chooser;
    private FileChooser.ExtensionFilter imageFilter;
    public File chooserImageFile, recentImage;
    private Image defaultImage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        chooser = new FileChooser();
        imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg");

        defaultImage = imageView.getImage();

        textPhone.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                textPhone.setText(newValue.replaceAll("[^\\d]", ""));
                Toolkit.getDefaultToolkit().beep();
            }
        });
    }

    public void setDatePicker(LocalDate datePicker) {
        this.datePicker.setValue(datePicker);
    }

    public void setTextName(String textName) {
        this.textName.setText(textName);
        selectedName = textName;
    }

    public void setTextAddress(String textAddress) {
        this.textAddress.setText(textAddress);
    }

    public void setTextCity(String textCity) {
        this.textCity.setText(textCity);
    }

    public void setTextPhone(String textPhone) {
        this.textPhone.setText(textPhone);
    }

    public void setTextEmail(String textEmail) {
        this.textEmail.setText(textEmail);
        selectedEmail = textEmail;
    }

    public void setTextDegree(String textDegree) {
        this.textDegree.setText(textDegree);
    }

    public void setGender(String gender) {
        if (gender.contentEquals("M")) {
            radioM.setSelected(true);
        } else {
            radioF.setSelected(true);
        }
    }

    public void setMaritalStatus(String marStatus) {
        switch (marStatus) {
            case "Single":
                radioSingle.setSelected(true);
                break;
            case "Married":
                radioMar.setSelected(true);
                break;
            case "Divorce":
                radioDiv.setSelected(true);
                break;
            default:
                break;
        }
    }

    public void setImage(Image image) {
        imageView.setImage(image);
    }

    public void setRecentImage(File recentImage) {
        this.recentImage = recentImage;
    }

    @FXML
    private void openFileChooserDialog() {
        try {
            chooser.getExtensionFilters().add(imageFilter);
            chooser.setTitle("Choose Image");

            chooserImageFile = chooser.showOpenDialog(imageView.getScene().getWindow());
            if (chooserImageFile != null) {
                Image image = new Image(chooserImageFile.toURI().toURL().toString());
                imageView.setImage(image);
            }
        } catch (Exception e) {
        }
    }

    public abstract void saveUpdate();

    private class UpdateProfileTask extends Task<Object> {

        @Override
        protected Object call() throws Exception {

            try {
                saveUpdate();
            } catch (Exception e) {
                bPane.setDisable(false);
                spinner.setVisible(false);

                Platform.runLater(() -> {
                    EHospitalDialog.dialogAlert(Alert.AlertType.ERROR, "Database connection failure", textAddress.getScene().getWindow());
                });
            }

            return null;
        }

    }

    @FXML
    private void updateProfile() {
        name = textName.getText().trim();
        address = textAddress.getText().trim();
        city = textCity.getText().trim();
        degree = textDegree.getText().trim();
        email = textEmail.getText().trim();
        phone = textPhone.getText().trim();
        date = datePicker.getValue();

        if (name.isEmpty() || address.isEmpty() || city.isEmpty() || degree.isEmpty() || email.isEmpty() || phone.isEmpty() || date == null
                || tg.getSelectedToggle() == null || tgGender.getSelectedToggle() == null || imageView.getImage() == defaultImage) {
            EHospitalDialog.dialogAlert(Alert.AlertType.ERROR, "Please Fill All Required Input", textAddress.getScene().getWindow());
        } else {
            bPane.setDisable(true);
            spinner.setVisible(true);

            UpdateProfileTask task = new UpdateProfileTask();
            task.setOnSucceeded((event) -> {
                bPane.setDisable(false);
                spinner.setVisible(false);

                //Asign the updated Loggedin User name to the Bridge class variable
                //so it can change where ever Loggedin User name is displayed
                EHospitalBridge.loginUser = name;

                EHospitalDialog.dialogAlert(Alert.AlertType.CONFIRMATION, "Update Successfull", textAddress.getScene().getWindow());
            });
            new Thread(task).start();
        }
    }

}
