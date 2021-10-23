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
import main.transferEntityDetails.MakeBooking;
import main.model.userBookingModel;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class adminReleaseAndBookingMgmntDeleteController implements Initializable {
    @FXML
    private Label isConnected;
    @FXML
    private Button buttonGoBackToBkngMgmnt;
    @FXML
    private Button buttonDoneCancelBooking;
    @FXML
    private TextField txtBookingID;
    @FXML
    private Button goBackToAdminHome;

    userBookingModel userBookingModel = new userBookingModel();
    Scene currentScene;
    Calendar cal = Calendar.getInstance();
    Date todaysDate = cal.getTime();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (userBookingModel.isDbConnected()) {
            isConnected.setText("Connected");
        } else {
            isConnected.setText("Not Connected");
        }
    }

    public void goBackToBkngMgmnt(ActionEvent actionEvent) {
        Scene currentScene = buttonGoBackToBkngMgmnt.getScene();
        Window window = currentScene.getWindow();
        Stage primaryStage = (Stage) window;
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../view/adminBookingMgmnt.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene nextScene = new Scene(root, 523.0, 387.0);
        primaryStage.setScene(nextScene);
        primaryStage.setTitle("Admin Booking Management");
    }

    public void doneCancelBooking(ActionEvent actionEvent) {
        try {
            if (!(txtBookingID.getText().isEmpty())) {
                int bookingIDUserTyped = Integer.parseInt(txtBookingID.getText());
                if (userBookingModel.getAllExistingBkingID().contains(bookingIDUserTyped)) {
                    String username = userBookingModel.getBookingUsernameOfID(bookingIDUserTyped);
                    Date booking = userBookingModel.getCurrentBooking(username).getDateFinalBooking();
                    long frtEightHrsB4BookingDate = booking.getTime() - 1000 * 60 * 60 * 48;
                    Date frtEightHrsB4BookingDateInDate = new Date(frtEightHrsB4BookingDate);
                    if (todaysDate.before(frtEightHrsB4BookingDateInDate)) {
                        int deskID = userBookingModel.getDeskID(username);
                        userBookingModel.setDeskUnlockdown(deskID);
                        userBookingModel.setDeleteBooking(bookingIDUserTyped);
                        currentScene = buttonDoneCancelBooking.getScene();
                        Window window = currentScene.getWindow();
                        Stage primaryStage = (Stage) window;
                        Parent root = null;
                        try {
                            root = FXMLLoader.load(getClass().getResource("../view/adminBookingMgmnt.fxml"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Scene nextScene = new Scene(root, 523.0, 387.0);
                        primaryStage.setScene(nextScene);
                        primaryStage.setTitle("Admin Booking Management");
                    } else {
                        isConnected.setText("Too late to delete, \n if it is outdated then delete from release booking");
                    }
                } else {
                    isConnected.setText("ID doesn't exists");
                }
            } else {
                isConnected.setText("Field empty!");
            }
        } catch (Exception e) {
            isConnected.setText("Booking id has to be a number\nThis might be system error, please quit and try again");
        }
    }

    //Release booking
    public void doneDeleteBkingOutOfdate(MouseEvent mouseEvent) {
        try {
            List<MakeBooking> allExisting = new ArrayList<>();
            allExisting = userBookingModel.allExistingBking();
            for (int i = 0; i < allExisting.size(); i++) {
                Date dateFinalBooking = allExisting.get(i).getDateFinalBooking();
                if (dateFinalBooking.before(todaysDate)) {
                    if (!(allExisting.get(i).getBookingStatus().equals("APPROVED") && allExisting.get(i).getcheckInStatus().equals("CHECKEDIN"))) {
                        userBookingModel.setDeskUnlockdown(allExisting.get(i).getDeskID());
                        userBookingModel.setDeleteBooking(allExisting.get(i).getBookingID());
                        isConnected.setText("All outdated booking have been deleted");
                    } else {
                        isConnected.setText("Nothing to check");
                    }
                } else {
                    isConnected.setText("Nothing to check");
                }
            }
        } catch (Exception e) {
            isConnected.setText("Error try again after quit");
        }
    }

    public void gotBackToAdminHome(ActionEvent actionEvent) {
        Scene currentScene = goBackToAdminHome.getScene();
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
}
