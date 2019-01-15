/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitaldoctorprofile;

import ehospitalbridge.EHospitalBridge;
import ehospitaldb.EHospitalDB;
import ehospitalprofile.EHospitalProfile;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Calendar;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;

/**
 *
 * @author Andre Kelvin
 */
public class eHospitalDoctorProfileController extends EHospitalProfile{    

    @Override
    public void displayProfileDetails() {
        try {
            ps = EHospitalDB.getCon().prepareStatement("Select * From DOCTOR Where NAME=?");
            ps.setString(1, EHospitalBridge.loginUser);
            rs = ps.executeQuery();

            while (rs.next()) {
                labelName.setText(rs.getString("NAME"));
                
                age = (byte) (calender.get(Calendar.YEAR) - rs.getDate("DATE_OF_BIRTH").toLocalDate().getYear());
                dateOfBirth=rs.getDate("DATE_OF_BIRTH");
                
                labelAge.setText(age + "");
                labelGendre.setText(rs.getString("GENDER"));
                labelAddress.setText(rs.getString("ADDRESS"));
                labelCity.setText(rs.getString("CITY"));
                labelPhone.setText(rs.getString("PHONE"));
                labelMatStatus.setText(rs.getString("MARITAL_STATUS"));
                labelSalary.setText(rs.getString("SALARY"));
                labelEmail.setText(rs.getString("EMAIL"));
                labelDepart.setText(rs.getString("DEPARTMENT"));
                labelDegree.setText(rs.getString("DEGREE"));

                imageFile = new File(rs.getString("PHOTO"));
                BufferedImage buff = ImageIO.read(imageFile);
                Image image = SwingFXUtils.toFXImage(buff, null);
                imageView.setImage(image);
            }
        } catch (Exception e) {e.printStackTrace();
        }
    }
    
}
