/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitaldrugs;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import ehospitaldialog.EHospitalDialog;
import ehospitaldb.EHospitalDB;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author Andre Kelvin
 */
public class AddDrugController implements Initializable {

    @FXML
    private JFXSpinner spinner;
    @FXML
    private VBox vBox;
    @FXML
    private JFXComboBox comboCat;
    @FXML
    private JFXTextField textName, textQty, textManufact, textPrice;
    @FXML
    private JFXTextArea textAreaDescrip;
    @FXML
    private ImageView imageView;
    private PreparedStatement ps;
    private ResultSet rs;
    private ObservableList obList;
    private FileChooser chooser;
    private FileChooser.ExtensionFilter imageFilter;
    private Image defaultImage, chooserImage;
    private File imageFile;
    private String name, descrip, qty, price, manufact;
    private InputStream input;
    private OutputStream output;
    private int size;
    private byte[] data;
    private DecimalFormat format;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        chooser = new FileChooser();
        imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg");
        defaultImage = imageView.getImage();
        data = new byte[1024];
        format = new DecimalFormat();
        format.applyPattern(",000");

        //Force the Textfield to be Numeric only
        textQty.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                textQty.setText(newValue.replaceAll("[^\\d]", ""));
                Toolkit.getDefaultToolkit().beep();
            }
        });
    }

    public void populateCategoryCombo() throws SQLException {
        comboCat.getItems().clear();
        ps = EHospitalDB.getCon().prepareStatement("Select * From DRUG_CATEGORY");
        rs = ps.executeQuery();

        while (rs.next()) {
            comboCat.getItems().add(rs.getString("CATEGORY_NAME"));
        }
    }

    public void setObList(ObservableList obList) {
        this.obList = obList;
    }

    @FXML
    private void imageClick() {
        try {
            chooser.getExtensionFilters().add(imageFilter);
            chooser.setTitle("Choose Image");

            imageFile = chooser.showOpenDialog(textName.getScene().getWindow());
            if (imageFile != null) {
                chooserImage = new Image(imageFile.toURI().toURL().toString());
                imageView.setImage(chooserImage);
            }
        } catch (Exception e) {
        }
    }

    private class AddDrug extends Task<Object> {

        @Override
        protected Object call() throws Exception {

            try {
                ps = EHospitalDB.getCon().prepareStatement("Insert Into DRUG (NAME,DESCRIPTION,CATEGORY,QUANTITY,PRICE,MANUFACTURER,PHOTO) "
                        + "Values(?,?,?,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setString(1, name);
                ps.setString(2, descrip);
                ps.setString(3, comboCat.getValue().toString());
                ps.setInt(4, Integer.parseInt(qty));
                ps.setInt(5, Integer.parseInt(price));
                ps.setString(6, manufact);
                ps.setString(7, System.getProperty("user.home") + File.separator + "DrugImage" + File.separator + imageFile.getName());

                //Save the Actual Image to DrugImage File
                input = new FileInputStream(imageFile);
                output = new FileOutputStream(System.getProperty("user.home") + File.separator + "DrugImage" + File.separator + imageFile.getName());

                while ((size = input.read(data)) != -1) {
                    output.write(data, 0, size);
                }
                output.close();

                ps.executeUpdate();

                //Get Auto Generated ID and Display it with the newly inserted data in the table view 
                rs = ps.getGeneratedKeys();
                rs.next();

                obList.add(new Drug(rs.getInt(1), name, descrip, comboCat.getValue().toString(), Integer.parseInt(qty), Integer.parseInt(price), manufact));
            } catch (Exception e) {
                vBox.setDisable(false);
                spinner.setVisible(false);

                Platform.runLater(() -> {
                    EHospitalDialog.dialogAlert(Alert.AlertType.ERROR, "Database connection failure", textName.getScene().getWindow());
                });
            }

            return null;
        }

    }

    @FXML
    private void saveAction() {
        try {
            name = textName.getText().trim();
            descrip = textAreaDescrip.getText().trim();
            qty = textQty.getText().trim();
            price = textPrice.getText().trim();
            manufact = textManufact.getText().trim();

            if (name.isEmpty() || descrip.isEmpty() || qty.isEmpty() || price.isEmpty() || manufact.isEmpty() || comboCat.getValue() == null || imageView.getImage() == defaultImage) {
                EHospitalDialog.dialogAlert(Alert.AlertType.ERROR, "Please Fill The Required Input", textName.getScene().getWindow());
            } else {
                vBox.setDisable(true);
                spinner.setVisible(true);

                AddDrug task = new AddDrug();
                task.setOnSucceeded((event) -> {
                    vBox.setDisable(false);
                    spinner.setVisible(false);

                    EHospitalDialog.dialogAlert(Alert.AlertType.INFORMATION, "Save Successful", textName.getScene().getWindow());

                    textAreaDescrip.clear();
                    textManufact.clear();
                    textName.clear();
                    textPrice.clear();
                    textQty.clear();
                    comboCat.setValue(null);
                    imageView.setImage(defaultImage);
                });
                new Thread(task).start();
            }
        } catch (Exception e) {
        }
    }

}
