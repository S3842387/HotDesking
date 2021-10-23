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
import main.model.userAccountHandlingModel;
import main.transferEntityDetails.Employee;
import main.transferEntityDetails.GeneralAccRequirements;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class adminAccMgmntAddController implements Initializable {
    //    Add account for both ADMIN AND EMP FROM ADMIN ACCOUNT
    @FXML
    private Label isConnected;
    @FXML
    private TextField txtUsername, txtFirstName, txtLastName, txtPassword, txtSecretQ, txtAnsSecQ;

    //Add section from Acc Management for Other Admin
    @FXML
    private Button buttonDoneAddingNewAdmin, buttonGoBackToAccManAdm;

    //Add section from Acc Management for Employee
    @FXML
    private Button buttonDoneAddingNewEmployee, buttonGoBackToAccManEmployee;

    private userAccountHandlingModel userAccountHandlingModel = new userAccountHandlingModel();
    private boolean fieldNotEmpty;
    private Employee newEmployee;
    private GeneralAccRequirements newAdmin;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (userAccountHandlingModel.isDbConnected()) {
            isConnected.setText("Connected");
        } else {
            isConnected.setText("Not Connected");
        }
    }

    //Add section from Acc Management for Other Admin
    public void doneAddingNewAdmin(ActionEvent actionEvent) {
        try {
            fieldNotEmpty = (!(txtUsername.getText().isEmpty() ||
                    txtFirstName.getText().isEmpty() ||
                    txtLastName.getText().isEmpty() ||
                    txtPassword.getText().isEmpty() ||
                    txtSecretQ.getText().isEmpty() ||
                    txtAnsSecQ.getText().isEmpty()));
            if (fieldNotEmpty) {
                int indexForAlreadyExistingEmp = userAccountHandlingModel.allExistingUserNames().size();
                int NewEmpID = indexForAlreadyExistingEmp + 1;
                String usernameTheyTyped = txtUsername.getText();
                newAdmin = new GeneralAccRequirements(txtUsername.getText(), txtFirstName.getText(), txtLastName.getText(), "ADMIN", txtSecretQ.getText(),
                        txtAnsSecQ.getText(), NewEmpID, txtPassword.getText(), "ACTIVATED");
                if (!userAccountHandlingModel.allExistingUserNames().contains(usernameTheyTyped)) {
                    userAccountHandlingModel.insertEmployeeDetailsForSignUp(newAdmin);
                    isConnected.setText("Done adding info");
                    Scene currentScene = buttonDoneAddingNewAdmin.getScene();
                    Window window = currentScene.getWindow();
                    Stage primaryStage = (Stage) window;
                    Parent root = null;
                    try {
                        root = FXMLLoader.load(getClass().getResource("../view/adminAccountManagementOtherAdmin.fxml"));
                    } catch (IOException e) {
                    }
                    Scene nextScene = new Scene(root, 523.0, 387.0);
                    primaryStage.setScene(nextScene);
                    primaryStage.setTitle("Admin Manage Other Admins");
                } else {
                    isConnected.setText("username already exists");
                }
            } else {
                isConnected.setText("Empty Fields");
            }
        } catch (Exception e) {
            isConnected.setText("Error try again");
        }
    }

    public void goBackToAccManAdm(ActionEvent actionEvent) {
        Scene currentScene = buttonGoBackToAccManAdm.getScene();
        Window window = currentScene.getWindow();
        Stage primaryStage = (Stage) window;
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../view/adminAccountManagementOtherAdmin.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene nextScene = new Scene(root, 523.0, 387.0);
        primaryStage.setScene(nextScene);
        primaryStage.setTitle("Admin Manage Other Admins");
    }

    //Add section from Acc Management for Employee
    public void doneAddingNewEmployee(ActionEvent actionEvent) {
        try {
            fieldNotEmpty = (!(txtUsername.getText().isEmpty() || txtFirstName.getText().isEmpty() || txtLastName.getText().isEmpty() || txtPassword.getText().isEmpty() || txtSecretQ.getText().isEmpty() || txtAnsSecQ.getText().isEmpty()));
            if (fieldNotEmpty) {
                int indexForAlreadyExistingEmp = userAccountHandlingModel.allExistingUserNames().size();
                int NewEmpID = indexForAlreadyExistingEmp + 1;
                String usernameTheyTyped = txtUsername.getText();
                newEmployee = new Employee(txtUsername.getText(), txtFirstName.getText(),
                        txtLastName.getText(), "EMPLOYEE", txtSecretQ.getText(),
                        txtAnsSecQ.getText(), NewEmpID, txtPassword.getText(), "ACTIVATED");
                if (!userAccountHandlingModel.allExistingUserNames().contains(usernameTheyTyped)) {
                    userAccountHandlingModel.insertEmployeeDetailsForSignUp(newEmployee);
                    isConnected.setText("Done adding info");
                    Scene currentScene = buttonDoneAddingNewEmployee.getScene();
                    Window window = currentScene.getWindow();
                    Stage primaryStage = (Stage) window;
                    Parent root = null;
                    try {
                        root = FXMLLoader.load(getClass().getResource("../view/adminAccountManagementEmployee.fxml"));
                    } catch (IOException e) {
                    }
                    Scene nextScene = new Scene(root, 523.0, 387.0);
                    primaryStage.setScene(nextScene);
                    primaryStage.setTitle("Admin Manage Employee");
                } else {
                    isConnected.setText("username already exists");
                }
            } else {
                isConnected.setText("Empty Fields");
            }
        } catch (Exception e) {
            isConnected.setText("Error try again");
        }
    }

    public void goBackToAccManEmployee(ActionEvent actionEvent) {
        Scene currentScene = buttonGoBackToAccManEmployee.getScene();
        Window window = currentScene.getWindow();
        Stage primaryStage = (Stage) window;
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../view/adminAccountManagementEmployee.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene nextScene = new Scene(root, 523.0, 387.0);
        primaryStage.setScene(nextScene);
        primaryStage.setTitle("Admin Manage Employee");
    }


}
