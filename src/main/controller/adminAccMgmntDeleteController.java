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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class adminAccMgmntDeleteController {
    /*
    Delete account for both ADMIN AND EMP FROM ADMIN ACCOUNT
     */
    @FXML
    private Label isConnected;
    //Delete section from Acc Management for Other Admin
    @FXML
    private Button buttonDoneDeletingNewAdmin;
    @FXML
    private Button buttonGoBackToAccManAdmin;
    @FXML
    private TextField txtUsernameAdminToDelete;

    //Delete section from Acc Management for Employee
    @FXML
    private Button buttonDoneDeletingNewEmployee;
    @FXML
    private Button buttonGoBackToAccManEmployee;
    @FXML
    private TextField txtUsernameEmpToDelete;

    private userAccountHandlingModel userAccountHandlingModel = new userAccountHandlingModel();
    private userLoginModel userLoginModel = new userLoginModel();
    private userBookingModel userBookingModel = new userBookingModel();

    String usernameUserTyped;
    boolean passFieldNotEmpty;

    public void initialize(URL location, ResourceBundle resources) {
        if (userAccountHandlingModel.isDbConnected() && userLoginModel.isDbConnected()) {
            isConnected.setText("Connected");
        } else {
            isConnected.setText("Not Connected");
        }
    }

    //Delete section from Acc Management for Other Admin
    public void doneDeletingAdmin(ActionEvent actionEvent) {
        try {
            passFieldNotEmpty = (!(txtUsernameAdminToDelete.getText().isEmpty()));

            if (passFieldNotEmpty) {
                usernameUserTyped = txtUsernameAdminToDelete.getText();
                if (userAccountHandlingModel.allExistingUserNames().contains(usernameUserTyped) && (!userLoginModel.getUserSignedIn().equals(usernameUserTyped))) {
                    if (userLoginModel.UserType(txtUsernameAdminToDelete.getText()).equals("ADMIN")) {
                        userAccountHandlingModel.deleteUser(usernameUserTyped);
                        Scene currentScene = buttonDoneDeletingNewAdmin.getScene();
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
                    } else {
                        isConnected.setText("This user is not an admin");
                    }
                } else {
                    isConnected.setText("Username Wrong or this might me your username!");
                }
            } else {
                isConnected.setText("Field empty!");
            }
        } catch (Exception e) {
            isConnected.setText("Error try again");
        }
    }

    public void goBackToAccManAdm(ActionEvent actionEvent) {
        Scene currentScene = buttonGoBackToAccManAdmin.getScene();
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

    //Delete section from Acc Management for Employee
    public void doneDeletingEmployee(ActionEvent actionEvent) {
        try {
            if (!txtUsernameEmpToDelete.getText().isEmpty()) {
                usernameUserTyped = txtUsernameEmpToDelete.getText();
                if (userAccountHandlingModel.allExistingUserNames().contains(usernameUserTyped) && (!userLoginModel.getUserSignedIn().equals(usernameUserTyped))) {
                    if (userLoginModel.UserType(txtUsernameEmpToDelete.getText()).equals("EMPLOYEE")) {
                        int deskID = userBookingModel.getDeskID(usernameUserTyped);
                        userBookingModel.setDeskUnlockdown(deskID);
                        userAccountHandlingModel.deleteUser(usernameUserTyped);
                        Scene currentScene = buttonDoneDeletingNewEmployee.getScene();
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
                        isConnected.setText("This user is not an employee");
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
