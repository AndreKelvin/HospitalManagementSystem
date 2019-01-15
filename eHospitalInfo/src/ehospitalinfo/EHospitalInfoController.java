/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitalinfo;

import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import ehospitalbridge.EHospitalBridge;
import ehospitaldb.EHospitalDB;
import ehospitaldialog.EHospitalDialog;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;

/**
 *
 * @author Andre Kelvin
 */
public class EHospitalInfoController implements Initializable {

    @FXML
    private VBox vBox;
    @FXML
    private JFXSpinner spinner;
    @FXML
    private JFXTextField textName, textAddress, textCountry, textPhone;
    @FXML
    private ImageView imageView;
    private PreparedStatement ps;
    private ResultSet rs;
    private FileChooser chooser;
    private FileChooser.ExtensionFilter imageFilter;
    private File logoFile, imageFile;
    private String name, address, country, phone;
    private Image defaultImage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            new File(System.getProperty("user.home") + File.separator + "HospitalLogo").mkdir();
            chooser = new FileChooser();
            imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg");

            defaultImage = imageView.getImage();

            ps = EHospitalDB.getCon().prepareStatement("Select * From INFO");
            rs = ps.executeQuery();

            if (rs.next()) {
                textName.setText(rs.getString("Hospital_Name"));
                textAddress.setText(rs.getString("Address"));
                textCountry.setText(rs.getString("Country"));
                textPhone.setText(rs.getString("Phone"));

                imageFile = new File(rs.getString("Logo"));
                BufferedImage buff = ImageIO.read(imageFile);
                Image image = SwingFXUtils.toFXImage(buff, null);
                imageView.setImage(image);
            }
            ps.close();

            //Force the Textfield to be Numeric only
            textPhone.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                if (!newValue.matches("\\d*")) {
                    textPhone.setText(newValue.replaceAll("[^\\d]", ""));
                    Toolkit.getDefaultToolkit().beep();
                }
            });
        } catch (Exception e) {
        }
    }

    @FXML
    private void uploadLogo() {
        try {
            chooser.getExtensionFilters().add(imageFilter);
            chooser.setTitle("Choose Image");

            logoFile = chooser.showOpenDialog(imageView.getScene().getWindow());
            if (logoFile != null) {
                Image chooserImage = new Image(logoFile.toURI().toURL().toString());
                imageView.setImage(chooserImage);
            }
        } catch (Exception e) {
        }
    }

    private class SaveTask extends Task<Object> {

        @Override
        protected Object call() throws Exception {

            try {
                ps = EHospitalDB.getCon().prepareStatement("Truncate Table INFO");
                ps.executeUpdate();

                ps = EHospitalDB.getCon().prepareStatement("Insert Into INFO Values(?,?,?,?,?)");
                ps.setString(1, name);
                ps.setString(2, address);
                ps.setString(3, country);
                ps.setInt(4, Integer.parseInt(phone));

                if (logoFile != null) {//if image is choosen

                    //Delete The recent image if it exist
                    //This will avoid multiple image in the file
                    if (imageFile == null) {

                    } else if (imageFile.exists()) {
                        imageFile.delete();
                    }

                    InputStream in = new FileInputStream(logoFile);
                    try (OutputStream out = new FileOutputStream(System.getProperty("user.home") + File.separator + "HospitalLogo" + File.separator + logoFile.getName())) {
                        byte[] data = new byte[1024];
                        int size;
                        while ((size = in.read(data)) != -1) {
                            out.write(data, 0, size);
                        }
                    }

                    ps.setString(5, System.getProperty("user.home") + File.separator + "HospitalLogo" + File.separator + logoFile.getName());
                } else {//if image is not choosen. save the same Available Image path to DataBase
                    ps.setString(5, System.getProperty("user.home") + File.separator + "HospitalLogo" + File.separator + imageFile.getName());
                }
                ps.executeUpdate();

                //The Saved Hospital Name,Address,Phone and Logo will Display in Bill Module
                EHospitalBridge.billHospitalName.setText(name);
                EHospitalBridge.billHospitalAddress.setText(address);
                EHospitalBridge.billHospitalPhone.setText(phone);
                EHospitalBridge.imageViewBillLogo.setImage(imageView.getImage());
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
        try {
            name = textName.getText();
            address = textAddress.getText();
            country = textCountry.getText();
            phone = textPhone.getText();

            if (name.isEmpty() || address.isEmpty() || country.isEmpty() || phone.isEmpty() || imageView.getImage() == defaultImage) {
                EHospitalDialog.dialogAlert(Alert.AlertType.ERROR, "Invalid Input", textAddress.getScene().getWindow());
            } else {
                vBox.setDisable(true);
                spinner.setVisible(true);

                SaveTask task = new SaveTask();
                task.setOnSucceeded((event) -> {
                    vBox.setDisable(false);
                    spinner.setVisible(false);
                    
                    EHospitalDialog.dialogAlert(Alert.AlertType.INFORMATION, "Save Successful", textAddress.getScene().getWindow());
                });
                new Thread(task).start();
            }
        } catch (Exception e) {
        }
    }

}
