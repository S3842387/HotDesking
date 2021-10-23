package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import main.model.userAccountHandlingModel;
import main.model.userBookingModel;
import main.model.userLoginModel;
import main.transferEntityDetails.Employee;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class adminAccMgmntUpdateController {
    /*
    Update account for both ADMIN AND EMP FROM ADMIN ACCOUNT
     */
    @FXML
    private Label isConnected;
    @FXML
    private TextField txtOldUsername, txtUsername, txtFirstName, txtLastName, txtRole, txtPassword, txtSecretQ, txtAnsSecQ, txtStatus;

    private String oldUsernameUserTyped;
    private String newUsernameUserTyped;
    private String newFNUserTyped;
    private String newLNUserTyped;
    private String newRoleUserTyped;
    private String newPasswordUserTyped;
    private String newSecQUserTyped;
    private String newASQUserTyped;
    private String newStatusUserTyped;
    boolean fieldNotEmpty;

    private userLoginModel userLoginModel = new userLoginModel();
    private userAccountHandlingModel userAccountHandlingModel = new userAccountHandlingModel();
    private userBookingModel userBookingModel = new userBookingModel();

    //Update section from Acc Management for Other Admin
    @FXML
    private Button buttonDoneUpdateNewAdmin;
    @FXML
    private Button buttonGoBackToAccManAdm;

    //Update section from Acc Management for Employee
    @FXML
    private Button buttonDoneUpdateNewEmployee;
    @FXML
    private Button buttonGoBackToAccManEmployee;

    public void initialize(URL location, ResourceBundle resources) {
        if (userBookingModel.isDbConnected() && userAccountHandlingModel.isDbConnected() && userLoginModel.isDbConnected()) {
            isConnected.setText("Connected");
        } else {
            isConnected.setText("Not Connected");
        }

    }

    public void addAdminAndSwitchBack() throws SQLException {
        Scene currentScene = buttonDoneUpdateNewAdmin.getScene();
        Window window = currentScene.getWindow();
        Stage primaryStage = (Stage) window;
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../view/adminAccountManagementOtherAdmin.fxml"));
        } catch (IOException e) {
        }
        Scene nextScene = new Scene(root, 523.0, 387.0);
        primaryStage.setScene(nextScene);
        primaryStage.setTitle("Admin Account Management");
    }
    //Update section from Acc Management for Other Admin
    public void doneUpdateNewAdmin(ActionEvent actionEvent) {
        try {
            fieldNotEmpty = (!(txtOldUsername.getText().isEmpty() || txtUsername.getText().isEmpty() || txtFirstName.getText().isEmpty() || txtLastName.getText().isEmpty() || txtPassword.getText().isEmpty() || txtSecretQ.getText().isEmpty() || txtAnsSecQ.getText().isEmpty()));
            if (fieldNotEmpty) {
                oldUsernameUserTyped = txtOldUsername.getText();
                newUsernameUserTyped = txtUsername.getText();
                newFNUserTyped = txtFirstName.getText();
                newLNUserTyped = txtLastName.getText();
                newRoleUserTyped = txtRole.getText().toUpperCase(Locale.ROOT);
                newPasswordUserTyped = txtPassword.getText();
                newSecQUserTyped = txtSecretQ.getText();
                newASQUserTyped = txtAnsSecQ.getText();
                newStatusUserTyped = txtStatus.getText().toUpperCase(Locale.ROOT);
                if (userAccountHandlingModel.allExistingUserNames().contains(oldUsernameUserTyped) && (!txtOldUsername.getText().isEmpty())) {
                    if (userLoginModel.UserType(txtOldUsername.getText()).equals("ADMIN")) {
                        if (newStatusUserTyped.equals("ACTIVATED") || newStatusUserTyped.equals("DEACTIVATED")) {
                            if (newRoleUserTyped.equals("EMPLOYEE") || newRoleUserTyped.equals("ADMIN")) {
                                if (!userAccountHandlingModel.allExistingUserNames().contains(newUsernameUserTyped)){
                                    if (userLoginModel.getUserSignedIn().contains(oldUsernameUserTyped)){
                                        isConnected.setText("Note, since this was your acc you cannot DEACTIVATE or change role");
                                        newStatusUserTyped = "ACTIVATED";
                                        newRoleUserTyped = "ADMIN";
                                        Employee newEmployee = new Employee(newUsernameUserTyped, newFNUserTyped, newLNUserTyped, newRoleUserTyped.toUpperCase(Locale.ROOT), newSecQUserTyped, newASQUserTyped, 0, newPasswordUserTyped, newStatusUserTyped);
                                        userAccountHandlingModel.setNewUsers(newEmployee, oldUsernameUserTyped);
                                        addAdminAndSwitchBack();
                                    }else{
                                    Employee newEmployee = new Employee(newUsernameUserTyped, newFNUserTyped, newLNUserTyped, newRoleUserTyped.toUpperCase(Locale.ROOT), newSecQUserTyped, newASQUserTyped, 0, newPasswordUserTyped, newStatusUserTyped);
                                        isConnected.setText("Done adding info");
                                        userAccountHandlingModel.setNewUsers(newEmployee, oldUsernameUserTyped);
                                    addAdminAndSwitchBack();
                                    }
                                } else {
                                    isConnected.setText("New username already exists, if you want to keep same, update twice");
                                }
                            } else {
                                isConnected.setText("Role can only be EMPLOYEE or ADMIN");
                            }
                        } else {
                            isConnected.setText("Status has to be ACTIVATED or DEACTIVATED");
                        }
                    } else {
                        isConnected.setText("This user is not an admin");
                    }
                } else {
                    isConnected.setText("Username Wrong or empty!");
                }
            } else {
                isConnected.setText("Field empty!");
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
        }
        Scene nextScene = new Scene(root, 523.0, 387.0);
        primaryStage.setScene(nextScene);
        primaryStage.setTitle("Admin Manage Other Admins");
    }

    //Update section from Acc Management for Employee
    public void doneUpdateNewEmployee(ActionEvent actionEvent) {
        try {
            fieldNotEmpty = (!(txtOldUsername.getText().isEmpty() || txtUsername.getText().isEmpty() || txtFirstName.getText().isEmpty() || txtLastName.getText().isEmpty() || txtPassword.getText().isEmpty() || txtSecretQ.getText().isEmpty() || txtAnsSecQ.getText().isEmpty()));
            if (fieldNotEmpty) {
                oldUsernameUserTyped = txtOldUsername.getText();
                newUsernameUserTyped = txtUsername.getText();
                newFNUserTyped = txtFirstName.getText();
                newLNUserTyped = txtLastName.getText();
                newRoleUserTyped = txtRole.getText().toUpperCase(Locale.ROOT);
                newPasswordUserTyped = txtPassword.getText();
                newSecQUserTyped = txtSecretQ.getText();
                newASQUserTyped = txtAnsSecQ.getText();
                newStatusUserTyped = "ACTIVATED";//As per requirement Admin user can add/delete/update/deactivate Employee accounts but it doesn't say so for admin accounts

                if (userAccountHandlingModel.allExistingUserNames().contains(oldUsernameUserTyped)) {
                    if (userLoginModel.UserType(txtOldUsername.getText()).equals("EMPLOYEE")) {
                            if (newRoleUserTyped.equals("EMPLOYEE") || newRoleUserTyped.equals("ADMIN")) {
                                if (!userAccountHandlingModel.allExistingUserNames().contains(newUsernameUserTyped)){
                                    Employee newEmployee = new Employee(newUsernameUserTyped, newFNUserTyped, newLNUserTyped, newRoleUserTyped.toUpperCase(Locale.ROOT), newSecQUserTyped, newASQUserTyped, 0, newPasswordUserTyped, newStatusUserTyped);
                                    //As it is an update the employee id doesnt change so passing empId as 0 does not change anything
                                    userAccountHandlingModel.setNewUsers(newEmployee, oldUsernameUserTyped);
                                    if (newRoleUserTyped.equals("ADMIN")){
                                        int bkid=userBookingModel.getBookingID(oldUsernameUserTyped);
                                        int deskID = userBookingModel.getDeskID(oldUsernameUserTyped);
                                        userBookingModel.setDeleteBooking(bkid);
                                        userBookingModel.setDeskUnlockdown(deskID);
                                    }else{
                                        userAccountHandlingModel.setNewUsersBooking(oldUsernameUserTyped, newUsernameUserTyped);
                                        userAccountHandlingModel.setNewUserAllooking(oldUsernameUserTyped, newUsernameUserTyped);
                                    }
                                    isConnected.setText("Done adding info");
                                    Scene currentScene = buttonDoneUpdateNewEmployee.getScene();
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
                                    isConnected.setText("New username already exists, if you want to keep same, update twice");
                                }
                            } else {
                                isConnected.setText("Role can only be EMPLOYEE or ADMIN");
                            }
                    } else {
                        isConnected.setText("This user is not an employee");
                    }
                } else {
                    isConnected.setText("Username Wrong!");
                }
            } else {
                isConnected.setText("Field empty!");
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
        }
        Scene nextScene = new Scene(root, 523.0, 387.0);
        primaryStage.setScene(nextScene);
        primaryStage.setTitle("Admin Manage Employee");
    }
}
