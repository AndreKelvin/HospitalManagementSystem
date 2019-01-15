/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitalprofile;

import ehospitaldialog.EHospitalDialog;
import java.io.File;
import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

/**
 *
 * @author Andre Kelvin
 */
public abstract class EHospitalProfile implements Initializable {
    
    @FXML
    private StackPane stackPane;
    @FXML
    public Label labelName, labelDegree, labelEmail, labelMatStatus, labelPhone, labelCity, labelAddress, labelGendre,labelDepart,labelSalary,labelAge;
    @FXML
    public ImageView imageView;
    public PreparedStatement ps;
    public ResultSet rs;
    public byte age;
    public Calendar calender;
    public File imageFile;
    private Parent rootEditAcct,rootUpdateProfile;
    private FXMLLoader loaderEditAcct,loaderUpdateProfile;
    private UpdateProfile update;
    private EditAccount edit;
    public Date dateOfBirth;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        calender = new GregorianCalendar();
    }

    public abstract void displayProfileDetails();

    @FXML
    private void openEditAccountDialog() {
        try {
            if (loaderEditAcct==null) {
                loaderEditAcct=new FXMLLoader(getClass().getResource("EditAccount.fxml"));
                rootEditAcct=loaderEditAcct.load();
                
                edit=loaderEditAcct.getController();
            }
            EHospitalDialog.showDialog(stackPane, rootEditAcct);
            edit.getUsernameAndPassword();
        } catch (Exception e) {e.printStackTrace();
        }
    }

    @FXML
    private void openUpdateProfile() {
        try {
            if (loaderUpdateProfile==null) {
                loaderUpdateProfile=new FXMLLoader(getClass().getResource("UpdateProfile.fxml"));
                rootUpdateProfile=loaderUpdateProfile.load();
                
                update=loaderUpdateProfile.getController();
            }
            EHospitalDialog.showDialog(stackPane, rootUpdateProfile);
            
            update.setDatePicker(dateOfBirth.toLocalDate());
            update.setGender(labelGendre.getText());
            update.setImage(imageView.getImage());
            update.setMaritalStatus(labelMatStatus.getText());
            update.setTextAddress(labelAddress.getText());
            update.setTextCity(labelCity.getText());
            update.setTextDegree(labelDegree.getText());
            update.setTextEmail(labelEmail.getText());
            update.setTextName(labelName.getText());
            update.setTextPhone(labelPhone.getText());
            update.setRecentImage(imageFile);
            
            EHospitalDialog.dialog.setOnDialogClosed((event) -> {
               displayProfileDetails();
            });
        } catch (Exception e) {
        }
    }
    
}
