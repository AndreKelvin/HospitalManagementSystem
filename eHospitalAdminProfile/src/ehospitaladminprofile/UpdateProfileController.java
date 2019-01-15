/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitaladminprofile;

import ehospitalbridge.EHospitalBridge;
import ehospitaldb.EHospitalDB;
import ehospitalprofile.UpdateProfile;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Date;

/**
 * FXML Controller class
 *
 * @author Andre Kelvin
 */
public class UpdateProfileController extends UpdateProfile {

    @Override
    public void saveUpdate() {
        try {
            ps = EHospitalDB.getCon().prepareStatement("Truncate ADMIN");
            ps.executeUpdate();

            ps = EHospitalDB.getCon().prepareStatement("Insert Into ADMIN Values(?,?,?,?,?,?,?,?,?,?)");
            ps.setString(1, name);
            ps.setDate(2, Date.valueOf(date));

            if (radioM.isSelected()) {
                ps.setString(3, radioM.getText());
            } else {
                ps.setString(3, radioF.getText());
            }

            ps.setString(4, address);
            ps.setString(5, city);
            ps.setString(6, phone);

            if (radioSingle.isSelected()) {
                ps.setString(7, radioSingle.getText());
            } else if (radioMar.isSelected()) {
                ps.setString(7, radioMar.getText());
            } else if (radioDiv.isSelected()) {
                ps.setString(7, radioDiv.getText());
            }

            ps.setString(8, email);
            ps.setString(9, degree);

            /*
                 Save File Chooser Image to DoctorImage File
                 Than Save file Path to Database
                 if image is not Choosen 
                 Save the same Available Image path to DataBase
             */
            if (chooserImageFile != null) {
                /*
                     Delete the recent image
                     This will avoid Unwanted/Duplicate image in DoctorImage File
                 */
                //recentImage.delete();

                //Save the Actual Image to DoctorImage File
                InputStream input = new FileInputStream(chooserImageFile);
                try (OutputStream output = new FileOutputStream(System.getProperty("user.home") + File.separator + "AdminImage" + File.separator + chooserImageFile.getName())) {
                    int size;
                    byte[] data = new byte[1024];
                    while ((size = input.read(data)) != -1) {//-1 means if the end of the stream is reached
                        output.write(data, 0, size);
                    }
                }

                //Save Path to database
                ps.setString(10, System.getProperty("user.home") + File.separator + "AdminImage" + File.separator + chooserImageFile.getName());
                chooserImageFile = null;
            } else {
                ps.setString(10, System.getProperty("user.home") + File.separator + "AdminImage" + File.separator + recentImage.getName());
            }
            ps.executeUpdate();
            ps.close();

            //Update Admin Name in Login Table
            ps = EHospitalDB.getCon().prepareStatement("Update HOSPITALLOGIN Set Name=? Where Name=?");
            ps.setString(1, name);
            ps.setString(2, EHospitalBridge.loginUser);
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
        }
    }

}
