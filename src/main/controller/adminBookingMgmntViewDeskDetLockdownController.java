package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import main.model.userBookingModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class adminBookingMgmntViewDeskDetLockdownController implements Initializable {
    @FXML
    private Label isConnected;
    @FXML
    private Button buttonGoBackToBkngMgmntViewDesk;
    //Lock
    @FXML
    private Button buttonDoneLockdown, buttonGoToUnlock;
    @FXML
    private TextField deskIDToLock;
    //Unlock
    @FXML
    private Button buttongoBackToLockdown, buttonDoneUnlock;
    @FXML
    private TextField deskIDToUnlock;

    private final userBookingModel userBookingModel = new userBookingModel();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (userBookingModel.isDbConnected()) {
            isConnected.setText("Connected");
        } else {
            isConnected.setText("Not Connected");
        }

    }

    public void goBackToBkngMgmntViewDesk(ActionEvent actionEvent) {
        Scene currentScene = buttonGoBackToBkngMgmntViewDesk.getScene();
        Window window = currentScene.getWindow();
        Stage primaryStage = (Stage) window;
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../view/userDeskGraphicalVisual.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene nextScene = new Scene(root, 523.0, 387.0);
        primaryStage.setScene(nextScene);
        primaryStage.setTitle("Admin View Desk");
    }

    // Lockdown actions
    public void goToUnlock(ActionEvent actionEvent) {
        Scene currentScene = buttonGoToUnlock.getScene();
        Window window = currentScene.getWindow();
        Stage primaryStage = (Stage) window;
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../view/adminBookingMgmntViewDeskToUnlock.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene nextScene = new Scene(root, 523.0, 387.0);
        primaryStage.setScene(nextScene);
        primaryStage.setTitle("Unlock Desk");
    }

    public void ButtonSetLock(ActionEvent actionEvent) {
        try {
            if (!deskIDToLock.getText().isEmpty()) {
                int deskID = Integer.parseInt(deskIDToLock.getText());
                if (deskID < 17 && deskID > 0) {
                    if (userBookingModel.getDeskStatus(deskID).equals("BOOKED")) {
                        userBookingModel.setDeleteBooking(deskID);
                    }
                    userBookingModel.setDeskLockdown(deskID);
                    Scene currentScene = buttonDoneLockdown.getScene();
                    Window window = currentScene.getWindow();
                    Stage primaryStage = (Stage) window;
                    Parent root = null;
                    try {
                        root = FXMLLoader.load(getClass().getResource("../view/userDeskGraphicalVisual.fxml"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Scene nextScene = new Scene(root, 523.0, 387.0);
                    primaryStage.setScene(nextScene);
                    primaryStage.setTitle("Admin View Desk");
                } else {
                    isConnected.setText("DeskID has to be from 1-16");
                }
            } else {
                isConnected.setText("Field empty");
            }
        } catch (Exception e) {
            isConnected.setText("Error, try again after quitting");
        }
    }

    //Unlock
    public void goBackToLockdown(ActionEvent actionEvent) {
        Scene currentScene = buttongoBackToLockdown.getScene();
        Window window = currentScene.getWindow();
        Stage primaryStage = (Stage) window;
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../view/adminBookingMgmntViewDeskToLockdown.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene nextScene = new Scene(root, 523.0, 387.0);
        primaryStage.setScene(nextScene);
        primaryStage.setTitle("Lockdown Seats");
    }

    public void ButtonSetUnlock(ActionEvent actionEvent) {
        try {
            if (!deskIDToUnlock.getText().isEmpty()) {
                int deskID = Integer.parseInt(deskIDToUnlock.getText());
                if (deskID < 17 && deskID > 0) {
                    if (!userBookingModel.getDeskStatus(deskID).equals("BOOKED")) {
                        userBookingModel.setDeskUnlockdown(deskID);
                        Scene currentScene = buttonDoneUnlock.getScene();
                        Window window = currentScene.getWindow();
                        Stage primaryStage = (Stage) window;
                        Parent root = null;
                        try {
                            root = FXMLLoader.load(getClass().getResource("../view/adminBookingMgmntViewDeskToLockdown.fxml"));
                        }catch (IOException e) {
                            e.printStackTrace();
                        }
                        Scene nextScene = new Scene(root, 523.0, 387.0);
                        primaryStage.setScene(nextScene);
                        primaryStage.setTitle("Lockdown Seats");
                    } else {
                        isConnected.setText("This desk is booked");
                    }
                } else {
                    isConnected.setText("DeskID has to be from 1-16");
                }
            } else {
                isConnected.setText("Field empty");
            }
        } catch (Exception e) {
            isConnected.setText("Error, try again after quitting");
        }
    }

}
