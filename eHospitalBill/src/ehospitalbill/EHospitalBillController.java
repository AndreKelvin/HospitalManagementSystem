/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitalbill;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSpinner;
import ehospitalbridge.EHospitalBridge;
import ehospitaldb.EHospitalDB;
import ehospitaldialog.EHospitalDialog;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.PrintResolution;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Scale;
import javafx.util.converter.IntegerStringConverter;
import javax.imageio.ImageIO;

/**
 *
 * @author Andre Kelvin
 */
public class EHospitalBillController implements Initializable {

    @FXML
    private StackPane stackPane;
    @FXML
    private VBox vBox;
    @FXML
    private JFXSpinner spinner;
    @FXML
    private ImageView imageView;
    @FXML
    private JFXComboBox comboPatientName;
    @FXML
    private Label labelHospitalName, labelHospitalAddress, labelHospitalPhone, labelDiagnosis, labelTreatment, labelAdmit, labelDischarged, labelAge, labelTotalAmount;
    @FXML
    private TableView<Bill> tableView;
    @FXML
    private TableColumn columnDescription, columnAmount;
    private PreparedStatement ps;
    private ResultSet rs;
    private ObservableList obList;
    private byte age;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            obList = FXCollections.observableArrayList();
            tableView.setItems(obList);

            //Pass Combo Box,Image View,Hospital(Name,Address,Phone)
            EHospitalBridge.comboBoxBillPatientName = comboPatientName;
            EHospitalBridge.imageViewBillLogo = imageView;
            EHospitalBridge.billHospitalName = labelHospitalName;
            EHospitalBridge.billHospitalAddress = labelHospitalAddress;
            EHospitalBridge.billHospitalPhone = labelHospitalPhone;

            columnDescription.setCellValueFactory(new PropertyValueFactory("descrip"));
            columnAmount.setCellValueFactory(new PropertyValueFactory("amount"));

            columnDescription.setCellFactory(TextFieldTableCell.forTableColumn());
            columnAmount.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

            ps = EHospitalDB.getCon().prepareStatement("Select Patient_Name From PATIENT Where Category=?");
            ps.setString(1, "Discharge Patient");
            rs = ps.executeQuery();

            while (rs.next()) {
                comboPatientName.getItems().add(rs.getString("Patient_Name"));
            }

            ps = EHospitalDB.getCon().prepareStatement("Select Hospital_Name,Address,Phone,Logo From INFO");
            rs = ps.executeQuery();

            if (rs.next()) {
                labelHospitalName.setText(rs.getString("Hospital_Name"));
                labelHospitalAddress.setText(rs.getString("Address"));
                labelHospitalPhone.setText(rs.getString("Phone"));

                File file = new File(rs.getString("Logo"));
                BufferedImage buff = ImageIO.read(file);
                Image image = SwingFXUtils.toFXImage(buff, null);
                imageView.setImage(image);
            }
            ps.close();

            Calendar calendar = new GregorianCalendar();

            //Display Details
            comboPatientName.getSelectionModel().selectedItemProperty().addListener((ObservableValue observable, Object oldValue, Object newValue) -> {
                try {
                    ps = EHospitalDB.getCon().prepareStatement("Select PATIENT.Diagnosis,Admited_Date,Date_Of_Birth,Treatment_Name,DISCHARGED_DATE.Date "
                            + "From PATIENT "
                            + "Left Join PATIENT_TREATMENT "
                            + "on PATIENT.Patient_Name=PATIENT_TREATMENT.Patient_Name "
                            + "Left Join DISCHARGED_DATE "
                            + "on DISCHARGED_DATE.Patient_Name=PATIENT.Patient_Name "
                            + "Where PATIENT.Patient_Name=?");
                    ps.setString(1, newValue.toString());
                    rs = ps.executeQuery();

                    if (rs.next()) {
                        labelDiagnosis.setText(rs.getString("Diagnosis"));
                        labelAdmit.setText(rs.getDate("Admited_Date").toString());

                        age = (byte) (calendar.get(Calendar.YEAR) - rs.getDate("Date_Of_Birth").toLocalDate().getYear());
                        labelAge.setText(age + "");

                        labelTreatment.setText(rs.getString("Treatment_Name"));
                        labelDischarged.setText(rs.getString("Date"));
                    }

                    ps = EHospitalDB.getCon().prepareStatement("Select Description,Amount,Total From BILL Where Patient_Name=?");
                    ps.setString(1, newValue.toString());
                    rs = ps.executeQuery();

                    tableView.getItems().clear();
                    labelTotalAmount.setText("");

                    while (rs.next()) {
                        obList.add(new Bill(rs.getString("Description"), rs.getInt("Amount")));
                        labelTotalAmount.setText(rs.getString("Total"));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
        }
    }

    @FXML
    private void generateTotal() {
        int total = 0;
        for (Bill item : tableView.getItems()) {
            total += (int) columnAmount.getCellData(item);
        }
        labelTotalAmount.setText(new DecimalFormat(",000").format(total));
    }

    @FXML
    private void print() {
        try {

            PrinterJob job = PrinterJob.createPrinterJob();

            if (job != null && job.showPrintDialog(stackPane.getScene().getWindow())) {
                labelDiagnosis.getScene().setCursor(Cursor.WAIT);
                Printer printer = job.getPrinter();
                PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.LANDSCAPE, Printer.MarginType.HARDWARE_MINIMUM);

                double width = stackPane.getWidth();
                double height = stackPane.getHeight();

                PrintResolution resolution = job.getJobSettings().getPrintResolution();

                width /= resolution.getFeedResolution();

                height /= resolution.getCrossFeedResolution();

                double scaleX = pageLayout.getPrintableWidth() / width / 600;
                double scaleY = pageLayout.getPrintableHeight() / height / 600;

                Scale scale = new Scale(scaleX, scaleY);

                stackPane.getTransforms().add(scale);

                boolean success = job.printPage(pageLayout, stackPane);
                if (success) {
                    job.endJob();
                    labelDiagnosis.getScene().setCursor(Cursor.DEFAULT);
                }
                stackPane.getTransforms().remove(scale);
            }
        } catch (Exception e) {
        }
    }

    private class SaveTask extends Task<Object> {

        @Override
        protected Object call() throws Exception {

            try {
                ps = EHospitalDB.getCon().prepareStatement("Insert Into BILL Values(?,?,?,?)");
                for (Bill item : tableView.getItems()) {
                    ps.setString(1, comboPatientName.getValue().toString());
                    ps.setString(2, columnDescription.getCellData(item).toString());
                    ps.setInt(3, (int) columnAmount.getCellData(item));
                    ps.setString(4, labelTotalAmount.getText());
                    ps.addBatch();
                }
                ps.executeBatch();
            } catch (Exception e) {
                vBox.setDisable(false);
                spinner.setVisible(false);
                
                Platform.runLater(() -> {
                    EHospitalDialog.dialogAlert(Alert.AlertType.ERROR, "Database connection failure", labelDiagnosis.getScene().getWindow());
                });
            }

            return null;
        }

    }

    @FXML
    private void save() {
        if (tableView.getItems().isEmpty() || comboPatientName.getValue() == null || labelTotalAmount.getText().isEmpty()) {
            EHospitalDialog.dialogAlert(Alert.AlertType.ERROR, "Invalid", labelDiagnosis.getScene().getWindow());
        } else {
            vBox.setDisable(true);
            spinner.setVisible(true);

            SaveTask task = new SaveTask();
            task.setOnSucceeded((event) -> {
                vBox.setDisable(false);
                spinner.setVisible(false);

                EHospitalDialog.dialogAlert(Alert.AlertType.INFORMATION, "Save Successful", labelDiagnosis.getScene().getWindow());
            });
            new Thread(task).start();
        }
    }

    @FXML
    private void addMoreCell() {
        obList.add(new Bill("null", 0));
    }

}
