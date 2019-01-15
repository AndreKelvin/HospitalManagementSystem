/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospitalwardroomtabpane;

import com.jfoenix.controls.JFXButton;
import ehospitaldb.EHospitalDB;
import ehospitaldialog.EHospitalDialog;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;

/**
 *
 * @author Andre Kelvin
 */
public class EHospitalWardRoomTabPaneController implements Initializable {
    
    @FXML
    private StackPane stackPane;
    @FXML
    private TableView<Ward> tableView;
    @FXML
    private TableView<Room> tableViewRoom;
    @FXML
    private TableColumn columnWard,columnBeds,columnFloor,columnRoom;
    @FXML
    private JFXButton buttonEdit,buttonDelete,buttonEditRoom,buttonDeleteRoom;
    private FXMLLoader loaderAdd,loaderEdit,loaderDelete,loaderAddRoom,loaderEditRoom,loaderDeleteRoom;
    private Parent rootAdd,rootEdit,rootDelete,rootAddRoom,rootEditRoom,rootDeleteRoom;
    private ObservableList obList,obListRoom;
    private PreparedStatement ps;
    private ResultSet rs;
    private Ward selectedValue;
    private Room selectedRoomValue;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            buttonDelete.setDisable(true);
            buttonEdit.setDisable(true);
            buttonDeleteRoom.setDisable(true);
            buttonEditRoom.setDisable(true);
            
            columnWard.setCellValueFactory(new PropertyValueFactory("ward"));
            columnBeds.setCellValueFactory(new PropertyValueFactory("bed"));
            columnFloor.setCellValueFactory(new PropertyValueFactory("floor"));
            columnRoom.setCellValueFactory(new PropertyValueFactory("room"));

            obList=FXCollections.observableArrayList();
            tableView.setItems(obList);
            obListRoom=FXCollections.observableArrayList();
            tableViewRoom.setItems(obListRoom);

            //populate table
            ps=EHospitalDB.getCon().prepareStatement("Select * From WARD");
            rs=ps.executeQuery();
            
            while(rs.next()){
                obList.add(new Ward(rs.getString("Ward"), Integer.parseInt(rs.getString("Beds"))));
            }
            ps.close();
            
            ps=EHospitalDB.getCon().prepareStatement("Select * From ROOM");
            rs=ps.executeQuery();
            
            while(rs.next()){
                obListRoom.add(new Room(rs.getString("Floor"), Integer.parseInt(rs.getString("Room"))));
            }
            ps.close();
            
            tableView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Ward> observable, Ward oldValue, Ward newValue) -> {
                selectedValue=newValue;
                buttonDelete.setDisable(false);
                buttonEdit.setDisable(false);
            });
            
            tableViewRoom.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Room> observable, Room oldValue, Room newValue) -> {
                selectedRoomValue=newValue;
                buttonDeleteRoom.setDisable(false);
                buttonEditRoom.setDisable(false);
            });
//            tableViewRoom.getFocusModel().focus(-1);
//            tableView.getFocusModel().focus(-1);
        } catch (Exception e) {
        }
    }
    
    @FXML
    private void addAction() {
        try {
            if (loaderAdd==null) {
                loaderAdd=new FXMLLoader(getClass().getResource("AddWard.fxml"));
                rootAdd=loaderAdd.load();
                
                AddWardController add=loaderAdd.getController();
                add.setOblist(obList);
            }
            EHospitalDialog.showDialog(stackPane,rootAdd);
        } catch (IOException ex) {
        }
    }
    
    @FXML
    private void addRoom() {
        try {
            if (loaderAddRoom==null) {
                loaderAddRoom=new FXMLLoader(getClass().getResource("AddRoom.fxml"));
                rootAddRoom=loaderAddRoom.load();
                
                AddRoomController add=loaderAddRoom.getController();
                add.setOblist(obListRoom);
            }
            EHospitalDialog.showDialog(stackPane, rootAddRoom);
        } catch (Exception e) {
        }
    }
    
    @FXML
    private void editRoom() {
        try {
            if (loaderEditRoom==null) {
                loaderEditRoom=new FXMLLoader(getClass().getResource("EditRoom.fxml"));
                rootEditRoom=loaderEditRoom.load();
            }
            EditRoomController edit=loaderEditRoom.getController();
            edit.setSelectedValue(selectedRoomValue);
            
            EHospitalDialog.showDialog(stackPane, rootEditRoom);
            
            //tableView.getSelectionModel().clearSelection();
            
            buttonDeleteRoom.setDisable(true);
            buttonEditRoom.setDisable(true);
        } catch (Exception e) {
        }
    }
    
    @FXML
    private void deleteRoom() {
        try {
            if (loaderDeleteRoom==null) {
                loaderDeleteRoom=new FXMLLoader(getClass().getResource("DeleteRoom.fxml"));
                rootDeleteRoom=loaderDeleteRoom.load();
            }
            DeleteRoomController del=loaderDeleteRoom.getController();
            del.setObList(obListRoom);
            del.setSelectedValue(selectedRoomValue);
            
            EHospitalDialog.showDialog(stackPane, rootDeleteRoom);
            
            //tableView.getSelectionModel().clearSelection();
            
            buttonDeleteRoom.setDisable(true);
            buttonEditRoom.setDisable(true);
        } catch (Exception e) {
        }
    }

    @FXML
    private void editAction() {
        try {
            if (loaderEdit==null) {
                loaderEdit=new FXMLLoader(getClass().getResource("EditWard.fxml"));
                rootEdit=loaderEdit.load();
            }
            EditWardController edit=loaderEdit.getController();
            edit.setSelectedValue(selectedValue);
            
            EHospitalDialog.showDialog(stackPane,rootEdit);
            
            //tableView.getSelectionModel().clearSelection();
            
            buttonDelete.setDisable(true);
            buttonEdit.setDisable(true);
        } catch (Exception e) {
        }
    }
    
    @FXML
    private void deleteAction() {
        try {
            if (loaderDelete==null) {
                loaderDelete=new FXMLLoader(getClass().getResource("DeleteWard.fxml"));
                rootDelete=loaderDelete.load();
            }
            DeleteWardController del=loaderDelete.getController();
            del.setSelectedValue(selectedValue);
            del.setObList(obList);
            
            EHospitalDialog.showDialog(stackPane, rootDelete);
            
            //tableView.getSelectionModel().clearSelection();
            
            buttonDelete.setDisable(true);
            buttonEdit.setDisable(true);
        } catch (Exception e) {
        }
    }
    
}
