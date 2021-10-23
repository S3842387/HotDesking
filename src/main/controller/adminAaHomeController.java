package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class adminAaHomeController {
    @FXML
    private Label isConnected;
    @FXML
    private Button logOut;
    @FXML
    private Button buttonAdminManagement;
    @FXML
    private Button buttonAdminViewDeskDet;
    @FXML
    private Button buttonAdminBookMngmnt;
    @FXML
    private Button buttonAdminGenerateReport;
    @FXML
    private Button buttonGoToReleBooking;

    public void logOut(ActionEvent actionEvent) {
        Scene currentScene = logOut.getScene();
        Window window = currentScene.getWindow();
        Stage primaryStage = (Stage) window;
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../view/userLogin.fxml"));
            ;
        } catch (IOException e) {
        }
        Scene nextScene = new Scene(root, 523.0, 387.0);
        primaryStage.setScene(nextScene);
        primaryStage.setTitle("Login Page");
    }

    public void goToAccountManagement(ActionEvent actionEvent) {
        Scene currentScene = buttonAdminManagement.getScene();
        Window window = currentScene.getWindow();
        Stage primaryStage = (Stage) window;
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../view/adminAccountManagement.fxml"));
        } catch (IOException e) {
        }
        Scene nextScene = new Scene(root, 523.0, 387.0);
        primaryStage.setScene(nextScene);
        primaryStage.setTitle("Admin Management");
    }

    public void goToAdminBookMngmnt(ActionEvent actionEvent) {
        Scene currentScene = buttonAdminBookMngmnt.getScene();
        Window window = currentScene.getWindow();
        Stage primaryStage = (Stage) window;
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../view/adminBookingMgmnt.fxml"));
        } catch (IOException e) {
        }
        Scene nextScene = new Scene(root, 523.0, 387.0);
        primaryStage.setScene(nextScene);
        primaryStage.setTitle("Admin Booking Management");
    }

    public void goToAdminGenerateReport(ActionEvent actionEvent) {
        Scene currentScene = buttonAdminGenerateReport.getScene();
        Window window = currentScene.getWindow();
        Stage primaryStage = (Stage) window;
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../view/adminGenerateReport.fxml"));
        } catch (IOException e) {
        }
        Scene nextScene = new Scene(root, 523.0, 387.0);
        primaryStage.setScene(nextScene);
        primaryStage.setTitle("Admin Generate Report");
    }

    public void goToReleBooking(ActionEvent actionEvent) {
        Scene currentScene = buttonGoToReleBooking.getScene();
        Window window = currentScene.getWindow();
        Stage primaryStage = (Stage) window;
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../view/adminReleasBking.fxml"));
        } catch (IOException e) {
        }
        Scene nextScene = new Scene(root, 523.0, 387.0);
        primaryStage.setScene(nextScene);
        primaryStage.setTitle("Release Booking");
    }
}
