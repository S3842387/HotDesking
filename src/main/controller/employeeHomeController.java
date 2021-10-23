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
import main.model.userLoginModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class employeeHomeController implements Initializable {
    @FXML
    private Label isConnected;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;
    @FXML
    private Button buttonGoToBooking;
    @FXML
    private Button buttonGoToEmpManageAcc;
    @FXML
    private Button buttonGoToEmpManageBooking;
    @FXML
    private Button logOut;

    Calendar cal = Calendar.getInstance();
    Date todaysDate = cal.getTime();
    public userLoginModel userLoginModel = new userLoginModel();
    public userBookingModel userBookingModel = new userBookingModel();

    // Check database connection
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (userLoginModel.isDbConnected()) {
            isConnected.setText("Connected");
        } else {
            isConnected.setText("Not Connected");
        }

    }

    public void goToBook(ActionEvent actionEvent) throws IOException, SQLException {
        String username = userLoginModel.getUserSignedIn();
        Scene currentScene = buttonGoToBooking.getScene();
        Window window = currentScene.getWindow();
        Stage primaryStage = (Stage) window;
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../view/employeeMakeBooking.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene nextScene = new Scene(root, 523.0, 387.0);
        primaryStage.setScene(nextScene);
        primaryStage.setTitle("Employee Make Booking Page");
    }

    public void goToManagAcc(ActionEvent actionEvent) {
        Scene currentScene = buttonGoToEmpManageAcc.getScene();
        Window window = currentScene.getWindow();
        Stage primaryStage = (Stage) window;
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../view/employeeManageAcc.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene nextScene = new Scene(root, 726.0, 432.0);
        primaryStage.setScene(nextScene);
        primaryStage.setTitle("Employee Manage Account Page");
    }

    public void goToManagBooking(ActionEvent actionEvent) {
        Scene currentScene = buttonGoToEmpManageAcc.getScene();
        Window window = currentScene.getWindow();
        Stage primaryStage = (Stage) window;
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../view/employeeManageBooking.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene nextScene = new Scene(root, 523.0, 387.0);
        primaryStage.setScene(nextScene);
        primaryStage.setTitle("Employee Manage Booking Page");
    }

    public void logOut(ActionEvent actionEvent) {
        Scene currentScene = logOut.getScene();
        Window window = currentScene.getWindow();
        Stage primaryStage = (Stage) window;
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../view/userLogin.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene nextScene = new Scene(root, 523.0, 387.0);
        primaryStage.setScene(nextScene);
        primaryStage.setTitle("Login Page");
    }
}
