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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Window;
import main.model.adminMakeReportModel;
import main.model.userAccountHandlingModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class adminReportController implements Initializable {
    // Make report from date or from emp det
    @FXML
    private Label isConnected;

    @FXML
    private Button buttonGoToReportFrmDate;
    @FXML
    private Button buttonBack;
    @FXML
    private Button buttonGoToReportFrmEmpDet;

    // For report from date
    @FXML
    private Button buttonDoneEntering;
    @FXML
    private Button buttonGoBack;
    @FXML
    private TextField txtDateToReport;

    // For report from Employee
    @FXML
    private Button buttonGenerateReport;
    @FXML
    private Button buttonGoBackToAdmReprt;
    @FXML
    private TextField txtUsernameToReport;

    Calendar cal = Calendar.getInstance();
    Date todaysDate = cal.getTime();

    private adminMakeReportModel adminMakeReportModel = new adminMakeReportModel();
    private userAccountHandlingModel userAccountHandlingModel = new userAccountHandlingModel();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (adminMakeReportModel.isDbConnected()) {
            isConnected.setText("Connected");
        } else {
            isConnected.setText("Not Connected");
        }
    }

    public void goBackToAdmHome(ActionEvent actionEvent) {
        Scene currentScene = buttonBack.getScene();
        Window window = currentScene.getWindow();
        Stage primaryStage = (Stage) window;
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../view/adminAaHome.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene nextScene = new Scene(root, 523.0, 387.0);
        primaryStage.setScene(nextScene);
        primaryStage.setTitle("Admin Home");
    }

    public void goToReportFrmDate(ActionEvent actionEvent) {
        Scene currentScene = buttonGoToReportFrmDate.getScene();
        Window window = currentScene.getWindow();
        Stage primaryStage = (Stage) window;
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../view/adminGenerateReportFromDate.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene nextScene = new Scene(root, 523.0, 387.0);
        primaryStage.setScene(nextScene);
        primaryStage.setTitle("Admin Generate Report From Date");

    }

    public void goToReportFrmEmpDet(ActionEvent actionEvent) {
        Scene currentScene = buttonGoToReportFrmEmpDet.getScene();
        Window window = currentScene.getWindow();
        Stage primaryStage = (Stage) window;
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../view/adminGenerateReportFromEmployeeDet.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene nextScene = new Scene(root, 523.0, 387.0);
        primaryStage.setScene(nextScene);
        primaryStage.setTitle("Admin Generate Report From Employee Username");
    }

    public void goBackToAdmReport(ActionEvent actionEvent) {
        Scene currentScene = buttonBack.getScene();
        Window window = currentScene.getWindow();
        Stage primaryStage = (Stage) window;
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../view/adminGenerateReport.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene nextScene = new Scene(root, 523.0, 387.0);
        primaryStage.setScene(nextScene);
        primaryStage.setTitle("Admin Generate Report");
    }

    // For report from date
    public void getDateAndGenerateReport(MouseEvent mouseEvent) {
        try {
            if (!txtDateToReport.getText().isEmpty()) {
                Date dateFinalBookingTheyTyped = new SimpleDateFormat("yyyy-MM-dd").parse(txtDateToReport.getText());//Just to initialise when being entered into MakeBooking
                String dateInString = txtDateToReport.getText();//If not inserted in string format, then it results in error when retrieving from database

                if (dateFinalBookingTheyTyped.after(todaysDate) || dateFinalBookingTheyTyped.equals(todaysDate)) {
                    if (adminMakeReportModel.makeReportFromDatePresentFuture(dateInString)) {
                        isConnected.setText("File Made");
                    } else {
                        isConnected.setText("File couldn't be made, date might not exist");
                    }
                }
                if (dateFinalBookingTheyTyped.before(todaysDate)) {
                    if (adminMakeReportModel.makeReportFromDatePast(dateInString)) {
                        isConnected.setText("File Made");
                    } else {
                        isConnected.setText("File couldn't be made, date might not exist");
                    }
                }
            } else {
                isConnected.setText("all fields are to be filled");
            }
        } catch (SQLException | ParseException e) {
            isConnected.setText("Date in yyyy-MM-dd format");
        }

    }

    // For report from Employee
    public void getEmpAndGenerateReport(MouseEvent mouseEvent) {
        //        txtUsernameToReport
        try {
            if (!txtUsernameToReport.getText().isEmpty()) {
                String username = txtUsernameToReport.getText();
                if (userAccountHandlingModel.allExistingUserNames().contains(username)) {//makes sure username exists
                    adminMakeReportModel.makeReportFromEmployee(username);
                    isConnected.setText("File Made");
                } else {
                    isConnected.setText("username doesn't exists");
                }
            } else {
                isConnected.setText("all fields are to be filled");
            }
        } catch (SQLException e) {
            isConnected.setText("Employee Might not exist");
        }
    }
}