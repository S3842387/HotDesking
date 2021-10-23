package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class adminBookingMgmntaController {

    @FXML
    private Button buttonBackToAdmin;
    @FXML
    private Button buttonGoToBookingMngConfirm;
    @FXML
    private Button buttonGoToBookingMngMakeBking;
    @FXML
    private Button buttonGoToBookingMngCancel;
    @FXML
    private Button buttonGoToBookingMngViewDeskDet;

    public void GoBackToAdmin(ActionEvent actionEvent) {
        Scene currentScene = buttonBackToAdmin.getScene();
        Window window = currentScene.getWindow();
        Stage primaryStage = (Stage) window;
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../view/adminAaHome.fxml"));
        } catch (IOException e) {
        }
        Scene nextScene = new Scene(root, 523.0, 387.0);
        primaryStage.setScene(nextScene);
        primaryStage.setTitle("Admin Home");
    }

    public void goToBookingMngConfirm(ActionEvent actionEvent) {
        Scene currentScene = buttonGoToBookingMngConfirm.getScene();
        Window window = currentScene.getWindow();
        Stage primaryStage = (Stage) window;
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../view/adminBookingMgmntConfirm.fxml"));
        } catch (IOException e) {
        }
        Scene nextScene = new Scene(root, 523.0, 387.0);
        primaryStage.setScene(nextScene);
        primaryStage.setTitle("Admin Confirm Booking");
    }

    public void goToBookingMngMakeBking(ActionEvent actionEvent) {
        Scene currentScene = buttonGoToBookingMngMakeBking.getScene();
        Window window = currentScene.getWindow();
        Stage primaryStage = (Stage) window;
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../view/adminBookingMgmntMakeBooking.fxml"));
        } catch (IOException e) {
        }
        Scene nextScene = new Scene(root, 523.0, 387.0);
        primaryStage.setScene(nextScene);
        primaryStage.setTitle("Admin Make Booking");
    }

    public void goToBookingMngCancel(ActionEvent actionEvent) {
        Scene currentScene = buttonGoToBookingMngCancel.getScene();
        Window window = currentScene.getWindow();
        Stage primaryStage = (Stage) window;
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../view/adminBookingMgmntDelete.fxml"));
        } catch (IOException e) {
        }
        Scene nextScene = new Scene(root, 523.0, 387.0);
        primaryStage.setScene(nextScene);
        primaryStage.setTitle("Admin Delete Booking");
    }

    public void goToBookingMngViewDeskDet(ActionEvent actionEvent) {
        Scene currentScene = buttonGoToBookingMngViewDeskDet.getScene();
        Window window = currentScene.getWindow();
        Stage primaryStage = (Stage) window;
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../view/userDeskGraphicalVisual.fxml"));
        } catch (IOException e) {
        }
        Scene nextScene = new Scene(root, 523.0, 387.0);
        primaryStage.setScene(nextScene);
        primaryStage.setTitle("Admin View Desk");
    }
}
