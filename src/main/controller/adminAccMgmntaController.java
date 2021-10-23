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

public class adminAccMgmntaController {

    Scene currentScene;
    // General Acc Management Sec
    @FXML
    private Button buttonBackToAdmin;
    @FXML
    private Button buttonGoToManageOtherAcc;
    @FXML
    private Button buttonGoToManageOtherEmployee;
    @FXML
    private Button buttonBackToAccManage;

    // Employee Management Sec
    @FXML
    private Button buttonGoToCretEmployee;
    @FXML
    private Button buttonGoToDelEmployee;
    @FXML
    private Button buttonGoToUpdateEmployee;

    // Admin Management Sec
    @FXML
    private Button buttonGoToNewAdmin;
    @FXML
    private Button buttonGoToDelAdmin;
    @FXML
    private Button buttonGoToUpdateAdmin;

    // General Acc Management Sec
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

    public void goToManageOtherAcc(ActionEvent actionEvent) {
        Scene currentScene = buttonGoToManageOtherAcc.getScene();
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

    public void goToManageOtherEmployee(ActionEvent actionEvent) {
        Scene currentScene = buttonGoToManageOtherEmployee.getScene();
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

    public void GoBackToAccManageMent(ActionEvent actionEvent) {
        Scene currentScene = buttonBackToAccManage.getScene();
        Window window = currentScene.getWindow();
        Stage primaryStage = (Stage) window;
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../view/adminAccountManagement.fxml"));
        } catch (IOException e) {
        }
        Scene nextScene = new Scene(root, 523.0, 387.0);
        primaryStage.setScene(nextScene);
        primaryStage.setTitle("Admin Account Management");
    }

    // Employee Management Sec

    public void goToCretEmployee(ActionEvent actionEvent) {
        Scene currentScene = buttonGoToCretEmployee.getScene();
        Window window = currentScene.getWindow();
        Stage primaryStage = (Stage) window;
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../view/adminAccountMgmntEmployeeAdd.fxml"));
        } catch (IOException e) {
        }
        Scene nextScene = new Scene(root, 523.0, 387.0);
        primaryStage.setScene(nextScene);
        primaryStage.setTitle("Admin Manage Employee - Add Employee");
    }

    public void goToDelEmployee(ActionEvent actionEvent) {
        Scene currentScene = buttonGoToDelEmployee.getScene();
        Window window = currentScene.getWindow();
        Stage primaryStage = (Stage) window;
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../view/adminAccountMgmntEmployeeDelete.fxml"));
        } catch (IOException e) {
        }
        Scene nextScene = new Scene(root, 523.0, 387.0);
        primaryStage.setScene(nextScene);
        primaryStage.setTitle("Admin Manage Employee - Delete Employee");
    }

    public void goToUpdateEmployee(ActionEvent actionEvent) {
        currentScene = buttonGoToUpdateEmployee.getScene();
        Window window = currentScene.getWindow();
        Stage primaryStage = (Stage) window;
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../view/adminAccountMgmntEmployeeUpdate.fxml"));
        } catch (IOException e) {
        }
        Scene nextScene = new Scene(root, 523.0, 387.0);
        primaryStage.setScene(nextScene);
        primaryStage.setTitle("Admin Manage Employee - Update Employee");
    }


    // Admin Management Sec

    public void goToCreatAdmin(ActionEvent actionEvent) {
        currentScene = buttonGoToNewAdmin.getScene();
        Window window = currentScene.getWindow();
        Stage primaryStage = (Stage) window;
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../view/adminAccountMgmntAdminAdd.fxml"));
        } catch (IOException e) {
        }
        Scene nextScene = new Scene(root, 523.0, 387.0);
        primaryStage.setScene(nextScene);
        primaryStage.setTitle("Admin Manage Admin - Add Admin");

    }

    public void goToDelAdmin(ActionEvent actionEvent) {
        currentScene = buttonGoToDelAdmin.getScene();
        Window window = currentScene.getWindow();
        Stage primaryStage = (Stage) window;
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../view/adminAccountMgmntAdminDelete.fxml"));
        } catch (IOException e) {
        }
        Scene nextScene = new Scene(root, 523.0, 387.0);
        primaryStage.setScene(nextScene);
        primaryStage.setTitle("Admin Manage Admin - Delete Admin");
    }

    public void goToUpdateAdmin(ActionEvent actionEvent) {
        currentScene = buttonGoToUpdateAdmin.getScene();
        Window window = currentScene.getWindow();
        Stage primaryStage = (Stage) window;
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../view/adminAccountMgmntAdminUpdate.fxml"));
        } catch (IOException e) {
        }
        Scene nextScene = new Scene(root, 523.0, 387.0);
        primaryStage.setScene(nextScene);
        primaryStage.setTitle("Admin Manage Admin - Update Admin");

    }
}
