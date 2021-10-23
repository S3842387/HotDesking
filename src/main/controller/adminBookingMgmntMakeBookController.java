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
import main.model.userAccountHandlingModel;
import main.model.userBookingModel;
import main.model.userLoginModel;
import main.transferEntityDetails.MakeBooking;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class adminBookingMgmntMakeBookController implements Initializable {
    @FXML
    private Label isConnected, LableViewBookingID;
    @FXML
    private Button buttonGoBackToBkngMgmnt, DoneMakeBooking;
    @FXML
    private TextField txtUsernameMakeBooking, txtDateOfBooking, txtSeatForBooking;

    private int NewBookingID;
    private String userNameTheyTyped;
    private String bookingStatus = "APPROVED";// Always approved for making a booking as no other value would be wrong input,
    // if any inout able was wrong then it would get set to approve
    private String dateInString;
    private Date dateFinalBookingTheyTyped;
    private int deskID;
    private MakeBooking newBooking;
    private boolean notNull;
    private int bookingid;
    private Calendar cal = Calendar.getInstance();
    private Date todaysDate = cal.getTime();

    public userLoginModel userLoginModel = new userLoginModel();
    public userBookingModel userBookingModel = new userBookingModel();
    public userAccountHandlingModel userAccountHandlingModel = new userAccountHandlingModel();
//    public userDeskModel userDeskModel = new userDeskModel();
//    Due to some issues that I cannot figure out why userDeskModel i had made is
//    making the make new booking method throw error, so i have transferred the methods to userBookingmodel instead uder desk section

    // Check database connection
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (userBookingModel.isDbConnected() && userLoginModel.isDbConnected() && userAccountHandlingModel.isDbConnected()) {
            //if (userDeskModel.isDbConnected()){
            isConnected.setText("Connected");
            //}
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

    public void Done(MouseEvent mouseEvent) throws SQLException, ParseException, NoSuchElementException {
        try {
            notNull = (!(txtDateOfBooking.getText().isEmpty() && txtSeatForBooking.getText().isEmpty() && txtUsernameMakeBooking.getText().isEmpty()));
            if (notNull) {
                NewBookingID = 0;//This value doesn't get inserted into database as it is autoincremented, it is there to just initialise
                userNameTheyTyped = txtUsernameMakeBooking.getText();
                if (userAccountHandlingModel.allExistingUserNames().contains(userNameTheyTyped)) {//makes sure username exists
                    if (userLoginModel.UserType(txtUsernameMakeBooking.getText()).equals("EMPLOYEE")) {//Makes sure it's an employee
                        bookingStatus = "WAITING";//This value doesn't get inserted into database as it has a default value of WAITING, it is there to just initialise
                        dateFinalBookingTheyTyped = new SimpleDateFormat("yyyy-MM-dd").parse(txtDateOfBooking.getText());//Just to initialise when being entered into MakeBooking
                        dateInString = txtDateOfBooking.getText();//If not inserted in string format, then it results in error when retrieving from database
                        deskID = Integer.parseInt(txtSeatForBooking.getText());
                        String deskStatus = userBookingModel.getDeskStatus(deskID);
                        newBooking = new MakeBooking(NewBookingID, deskID, userNameTheyTyped, dateFinalBookingTheyTyped, bookingStatus, "PENDING");
                        if (!(0 > deskID || deskID > 17)) {//There are only desks 1-16
                            if (!userBookingModel.getAllExistingBookingUser().contains(newBooking.getUserName())) {//Employee can only make one booking at a time
                                if (dateFinalBookingTheyTyped.after(todaysDate)) {
                                    if (deskStatus.equals("AVAILABLE")) {
                                        if (!userBookingModel.getBlackList(userNameTheyTyped).contains(deskID)) {//not in blacklist
                                            userBookingModel.setNewBooking(newBooking, dateInString);
                                            bookingid = userBookingModel.getBookingID(userNameTheyTyped);
                                            userBookingModel.setDeskBooked(deskID);
                                            isConnected.setText("");
                                            LableViewBookingID.setText(("Done adding booking, your new booking ID is" + bookingid + "\nIf booking id =0 please end app and try again."));
                                        } else {
                                            isConnected.setText("User has already sat on this seat");
                                        }
                                    } else {
                                        isConnected.setText("This seat is either locked or booked");
                                    }
                                } else {
                                    isConnected.setText("Please pick a date in the future");
                                }
                            } else {
                                LableViewBookingID.setText("");
                                isConnected.setText("This user already has booking with booking id of: " + userBookingModel.getBookingID(userNameTheyTyped) + ". Please delete before booking.");
                            }
                        } else {
                            isConnected.setText("DeskID has to be from 1-16");
                        }
                    } else {
                        isConnected.setText("This user is not an employee");
                    }
                } else {
                    isConnected.setText("username doesn't exists");
                }
            } else {
                isConnected.setText("Empty Fields");
            }
        } catch (ParseException e) {
            isConnected.setText("Date in yyyy-MM-dd format, booking ID is a number and all fields are to be filled");
        } catch (Exception e) {
            isConnected.setText("Error try again after quitting or change booking ID int if its not already");
        }
    }
}
