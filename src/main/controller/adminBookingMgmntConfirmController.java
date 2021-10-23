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
import main.model.userBookingModel;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class adminBookingMgmntConfirmController implements Initializable {

    Scene currentScene;
    Calendar cal = Calendar.getInstance();
    @FXML
    private Label isConnected;
    @FXML
    private Button buttonGoBackToBkngMgmnt, buttonDoneConfimBooking;
    @FXML
    private TextField txtBookingID, txtUsernameToConfirm;
    private final userBookingModel userBookingModel = new userBookingModel();
    private final Date todaysDate = cal.getTime();

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

    //Only get emps that are not yet confirmed
    public void doneConfimBooking(ActionEvent actionEvent) {
        try {
            if (!(txtBookingID.getText().isEmpty())) {
                int bookingIDUserTyped = Integer.parseInt(txtBookingID.getText());
                if (userBookingModel.getAllExistingBkingID().contains(bookingIDUserTyped)) {
                    if (!userBookingModel.getAllExistingConfirmedBookingUser().contains(bookingIDUserTyped)) {
                        //Method for date conversion and check for fate before from https://www.java2novice.com/java-util-date-example/check-before-date/
                        String username = userBookingModel.getBookingUsernameOfID(bookingIDUserTyped);
                        Date booking = userBookingModel.getCurrentBooking(username).getDateFinalBooking();
                        long twlwHrsB4BookingDate = booking.getTime() - 1000 * 60 * 60 * 12;
                        Date twlwHrsB4BookingDateInDate = new Date(twlwHrsB4BookingDate);
                        if (todaysDate.before(twlwHrsB4BookingDateInDate)) {
                            userBookingModel.setConfirmBooking(bookingIDUserTyped);
                            isConnected.setText("Done");
                            currentScene = buttonDoneConfimBooking.getScene();
                            Window window = currentScene.getWindow();
                            Stage primaryStage = (Stage) window;
                            Parent root = null;
                            try {
                                root = FXMLLoader.load(getClass().getResource("../view/adminBookingMgmnt.fxml"));
                            } catch (IOException e) {
                            }
                            Scene nextScene = new Scene(root, 523.0, 387.0);
                            primaryStage.setScene(nextScene);
                            primaryStage.setTitle("Admin Confirm Booking");
                        } else {
                            int deskID = userBookingModel.getDeskID(username);
                            userBookingModel.setDeskUnlockdown(deskID);
                            userBookingModel.setDeleteBooking(bookingIDUserTyped);
                            isConnected.setText("DELETING BOOKING (Too late to confirm a booking of: " + booking + ")");
                        }
                    } else {
                        isConnected.setText("This booking is already confirmed");
                    }
                } else {
                    isConnected.setText("ID doesn't exists");
                }
            } else {
                isConnected.setText("Empty Fields");
            }
        } catch (Exception e) {
            isConnected.setText("Please enter a number");
        }
    }
}
