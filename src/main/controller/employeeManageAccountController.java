package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Window;
import main.model.userAccountHandlingModel;
import main.model.userBookingModel;
import main.model.userLoginModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

public class employeeManageAccountController implements Initializable {
    //Show just the details of the person signed in
    userAccountHandlingModel userAccountHandlingModel = new userAccountHandlingModel();
    userLoginModel userLoginModel = new userLoginModel();
    userBookingModel userBookingModel = new userBookingModel();
    String UN, FN, LN, role, SQ, ASQ, PW, US, bookingStatus;
    int EID, deskID, BkID;
    Date dateFinalBooking;

    @FXML
    private Label isConnected;
    @FXML
    private Button buttonToGoBack;
    @FXML
    private Button buttonToView;
    @FXML
    private Label showFN;
    @FXML
    private Label showLN;
    @FXML
    private Label showRL;
    @FXML
    private Label showUN;
    @FXML
    private Label showSQ;
    @FXML
    private Label showASQ;
    @FXML
    private Label showPSWD;
    @FXML
    private Label showEmpId;
    @FXML
    private Label showBkID;
    @FXML
    private Label showBkDate;
    @FXML
    private Label showBkStatus;


    // Check database connection
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (userBookingModel.isDbConnected() && userAccountHandlingModel.isDbConnected() && userLoginModel.isDbConnected()) {
            isConnected.setText("Connected");
        } else {
            isConnected.setText("Not Connected");
        }

    }

    private void getUserInfo(String userSignedIn) throws SQLException {
        UN = userAccountHandlingModel.allUserDetails(userSignedIn).getUsername();
        FN = userAccountHandlingModel.allUserDetails(userSignedIn).getFirstName();
        LN = userAccountHandlingModel.allUserDetails(userSignedIn).getLastName();
        role = userAccountHandlingModel.allUserDetails(userSignedIn).getRole();
        SQ = userAccountHandlingModel.allUserDetails(userSignedIn).getSecretQuestion();
        ASQ = userAccountHandlingModel.allUserDetails(userSignedIn).getAnswerToSecretQuestion();
        EID = userAccountHandlingModel.allUserDetails(userSignedIn).getEmpID();
        PW = userAccountHandlingModel.allUserDetails(userSignedIn).getPassword();
        US = userAccountHandlingModel.allUserDetails(userSignedIn).getUserStatus();
    }

    private void showUserDet() {
        showFN.setText((FN));
        showLN.setText((LN));
        showRL.setText((role));
        showUN.setText((UN));
        showSQ.setText((SQ));
        showASQ.setText((ASQ));
        showPSWD.setText((PW));
        showEmpId.setText(String.valueOf((EID)));
    }

    public void goToshowEmpDetails(MouseEvent mouseEvent) throws SQLException {
        String userSignedIn = userLoginModel.getUserSignedIn();
        getUserInfo(userSignedIn);
        try {
            BkID = userBookingModel.getCurrentBooking(userSignedIn).getBookingID();
            dateFinalBooking = userBookingModel.getCurrentBooking(userSignedIn).getDateFinalBooking();
            bookingStatus = userBookingModel.getCurrentBooking(userSignedIn).getBookingStatus();

            showUserDet();
            showBkID.setText(String.valueOf((BkID)));
            showBkDate.setText(String.valueOf((dateFinalBooking)));
            showBkStatus.setText((bookingStatus));

        } catch (Exception e) {
            showUserDet();
            showBkID.setText("N/A");
            showBkDate.setText("N/A");
            showBkStatus.setText("N/A");
        }
    }

    public void goBackToEmpHome(ActionEvent actionEvent) {
        Scene currentScene = buttonToGoBack.getScene();
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
}
