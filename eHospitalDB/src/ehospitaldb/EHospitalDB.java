/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitaldb;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Andre Kelvin
 */
public class EHospitalDB {

    private static Connection connect;
    
    public static void initDB(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            //connect = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/hospitaldbtest", "myhospital", "2NQVwgFDJK9K4xq");
            connect = DriverManager.getConnection("jdbc:mysql://den1.mysql6.gear.host:3306/myhospitaldb", "myhospitaldb", "Ro5h-d3_Gjm1");
        } catch (Exception e) {
        }
    }
    
    public static Connection getCon() {
        return connect;
    }
    
    public static void closeDB() {
        try {
            connect.close();
        } catch (Exception e) {
        }
    }
    
}
