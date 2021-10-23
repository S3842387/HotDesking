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
import main.transferEntityDetails.GeneralAccRequirements;
import main.model.userAccountHandlingModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class userSignupController implements Initializable {
    @FXML
    private Button Done;
    @FXML
    private Label isConnected;
    @FXML
    private TextField txtUsernameSingUp;
    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtLastName;
    @FXML
    private TextField txtRole;
    @FXML
    private TextField txtPasswordSignUp;
    @FXML
    private TextField txtSecretQSignUp;
    @FXML
    private TextField txtAnsSecQSignUp;
    @FXML
    private Button buttonGoBack;

    public userAccountHandlingModel userAccountHandlingModel = new userAccountHandlingModel();
    boolean notNullField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (userAccountHandlingModel.isDbConnected()) {
            isConnected.setText("Connected");
        } else {
            isConnected.setText("Not Connected");
        }
    }

    public void Done(ActionEvent actionEvent) throws SQLException {
        try {
            notNullField = (!(txtUsernameSingUp.getText().isEmpty() ||
                    txtFirstName.getText().isEmpty() ||
                    txtLastName.getText().isEmpty() ||
                    txtRole.getText().isEmpty() ||
                    txtPasswordSignUp.getText().isEmpty() ||
                    txtSecretQSignUp.getText().isEmpty() ||
                    txtAnsSecQSignUp.getText().isEmpty()));

            if (notNullField) {//Makes sure all fields have values
                int indexForAlreadyExistingEmp = userAccountHandlingModel.allExistingUserNames().size();
                int NewEmpID = indexForAlreadyExistingEmp + 1;//To assign new index
                String roleTheyTyped = txtRole.getText().toUpperCase(Locale.ROOT);
                String usernameTheyTyped = txtUsernameSingUp.getText();
                GeneralAccRequirements newUser = new GeneralAccRequirements(txtUsernameSingUp.getText(), txtFirstName.getText(), txtLastName.getText(), txtRole.getText().toUpperCase(Locale.ROOT), txtSecretQSignUp.getText(),
                        txtAnsSecQSignUp.getText(), NewEmpID, txtPasswordSignUp.getText(), "ACTIVATED");//Till admin deactivates account it active
                if ((roleTheyTyped.equals("ADMIN") || roleTheyTyped.equals("EMPLOYEE"))) {//Makes sure its only one of two roles
                    if (!userAccountHandlingModel.allExistingUserNames().contains(usernameTheyTyped)) {//Makes sure that new username doesn't already exist(case sensitive)
                        userAccountHandlingModel.insertEmployeeDetailsForSignUp(newUser);//Inserts user into database through model
                        switchSceneToLogin();
                    } else {
                        isConnected.setText("username already exists");
                    }
                } else {
                    isConnected.setText("Role can only be either EMPLOYEE or ADMIN");
                }
            } else {
                isConnected.setText("Empty Fields");
            }
        } catch (InterruptedException e) {
            isConnected.setText("Done");
        } catch (SQLException e) {
            isConnected.setText("Error try again");
        }
    }

    //Go back without saving/entering new user
    public void goBack(ActionEvent actionEvent) {
        Scene currentScene = buttonGoBack.getScene();
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

    private void switchSceneToLogin() throws InterruptedException {
        isConnected.setText("Done adding info");
        Scene currentScene = Done.getScene();
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