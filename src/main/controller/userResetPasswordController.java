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
import main.model.userResetPassModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class userResetPasswordController implements Initializable {
    @FXML
    private Label isConnected;
    @FXML
    private Label PlsAnsSQ;
    @FXML
    private Label LableSecQuestHere;
    @FXML
    private Button Done;
    @FXML
    private Button buttonGoBack;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtSQAnswer;
    @FXML
    private TextField txtNewPassword;

    private userResetPassModel userResetPassModel = new userResetPassModel();

    String usernameUserTyped;
    String passUserTyped;

    // Check database connection
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (userResetPassModel.isDbConnected()) {
            isConnected.setText("Connected");
        } else {
            isConnected.setText("Not Connected");
        }

    }

    public void goBackToLogIn(ActionEvent actionEvent) throws SQLException {// After saving
        usernameUserTyped = txtUsername.getText();
        try {
            if (userResetPassModel.isUsernameCorrect(usernameUserTyped) && (!usernameUserTyped.isEmpty())) {
                String answer = (String) userResetPassModel.SecQuest(usernameUserTyped).get(2);
                if (!txtSQAnswer.getText().isEmpty()) {
                    String ansUserTyped = txtSQAnswer.getText();
                    if (answer.equals(ansUserTyped)) {
                        if (!txtNewPassword.getText().isEmpty()) {
                            passUserTyped = txtNewPassword.getText();
                            userResetPassModel.setNewPassword(usernameUserTyped, passUserTyped);
                            switchSceneToLogin();
                        } else {
                            isConnected.setText("Please enter new password!");
                        }
                    } else {
                        isConnected.setText("Answer Wrong!");
                    }
                } else {
                    isConnected.setText("Field empty!");
                }
            } else {
                isConnected.setText("Username Wrong or empty!");
            }
        } catch (Exception e) {
            isConnected.setText("Error try again");
        }
    }


    public void goBackToLogInWOSave(ActionEvent actionEvent) {// Without saving
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

    public void ShowQuest(MouseEvent mouseEvent) throws SQLException {//To show question of the user
        String username = txtUsername.getText();
        try {
            if (userResetPassModel.isUsernameCorrect(username)) {
                String question = (String) userResetPassModel.SecQuest(username).get(1);
                LableSecQuestHere.setText(question + "?");
            } else {
                isConnected.setText("Username Wrong or empty!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void switchSceneToLogin() {
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
