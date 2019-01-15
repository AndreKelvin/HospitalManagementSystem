/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitallogin;

import ehospitaldb.EHospitalDB;
import java.util.Optional;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Andre Kelvin
 */
public class EHospitalLogin extends Application {

    private Parent root;
    private FXMLLoader loader;

    @Override
    public void init() throws Exception {
        loader = new FXMLLoader(getClass().getResource("eHospitalLogin.fxml"));
        root = loader.load();

        eHospitalLoginController login = loader.getController();
    }

    @Override
    public void start(Stage stage) throws Exception {
        EHospitalDB.initDB();
        //Parent root = FXMLLoader.load(getClass().getResource("eHospitalLogin.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("icon/hms.png")));
        stage.setTitle("Hospital Management System");
        stage.setOnCloseRequest((event) -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Exit");
            alert.setContentText("Are you sure you want to exit?");

            ButtonType Yes = new ButtonType("Yes");
            ButtonType No = new ButtonType("No");

            //Remove the current Buttons and Add "Yes" "No" Buttons to Alert Dialog
            alert.getButtonTypes().setAll(Yes, No);

            Optional<ButtonType> option = alert.showAndWait();
            if (option.get().equals(Yes)) {
                EHospitalDB.closeDB();
                System.exit(0);
            } else {
                event.consume();
            }
        });
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
