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
import main.model.userLoginModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class userLoginController implements Initializable {
    @FXML
    private Label isConnected;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;
    @FXML
    private Button signUp;
    @FXML
    private Button Done;
    @FXML
    private Button Reset;

    public userLoginModel userLoginModel = new userLoginModel();

    // Check database connection
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (userLoginModel.isDbConnected()) {
            isConnected.setText("Connected");
        } else {
            isConnected.setText("Not Connected");
        }
    }

    /*
     * login Action method check if user input is the same as database.
     */
    public void Login(ActionEvent event) {
        try {
            if (userLoginModel.isLogin(txtUsername.getText(), txtPassword.getText())) {
                if (userLoginModel.getUserStatus(txtUsername.getText()).equals("ACTIVATED")){
                    isConnected.setText("Logged in successfully");
                    userLoginModel.isSignedInRightNow(txtUsername.getText());//To access username everywhere, it sets the username signed in into the database
                    if (userLoginModel.UserType(txtUsername.getText()).equals("EMPLOYEE")) {
                        Scene currentScene = Done.getScene();
                        Window window = currentScene.getWindow();
                        Stage primaryStage = (Stage) window;
                        Parent root = null;
                        try {
                            root = FXMLLoader.load(getClass().getResource("../view/employeeHome.fxml"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Scene nextScene = new Scene(root, 523.0, 387.0);
                        primaryStage.setScene(nextScene);
                        primaryStage.setTitle("Employee Home");
                    }
                    if (userLoginModel.UserType(txtUsername.getText()).equals("ADMIN")) {
                        Scene currentScene = Done.getScene();
                        Window window = currentScene.getWindow();
                        Stage primaryStage = (Stage) window;
                        Parent root = FXMLLoader.load(getClass().getResource("../view/adminAaHome.fxml"));
                        try {
                            root = FXMLLoader.load(getClass().getResource("../view/adminAaHome.fxml"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Scene nextScene = new Scene(root, 523.0, 387.0);
                        primaryStage.setScene(nextScene);
                        primaryStage.setTitle("Admin Home");
                    }
                } else {
                    isConnected.setText("User Deactivated");
                }
            } else {
                isConnected.setText("Username and password is incorrect");
            }
        } catch (SQLException | IOException e) {
            isConnected.setText("Error signing in");
        }
    }

    public void goToSignUp(ActionEvent actionEvent) throws IOException {
        Scene currentScene = signUp.getScene();
        Window window = currentScene.getWindow();
        Stage primaryStage = (Stage) window;
        Parent root = FXMLLoader.load(getClass().getResource("../view/userSignUp.fxml"));
        try {
            root = FXMLLoader.load(getClass().getResource("../view/userSignUp.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene nextScene = new Scene(root, 523.0, 387.0);
        primaryStage.setScene(nextScene);
        primaryStage.setTitle("Sign Up");
    }

    public void goToResetPwd(ActionEvent actionEvent) throws IOException {
        Scene currentScene = Reset.getScene();
        Window window = currentScene.getWindow();
        Stage primaryStage = (Stage) window;
        Parent root = FXMLLoader.load(getClass().getResource("../view/userResetPassword.fxml"));
        try {
            root = FXMLLoader.load(getClass().getResource("../view/userResetPassword.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene nextScene = new Scene(root, 523.0, 387.0);
        primaryStage.setScene(nextScene);
        primaryStage.setTitle("Reset Password");
    }

}