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
import main.transferEntityDetails.MakeBooking;
import main.model.userAccountHandlingModel;
import main.model.userBookingModel;
import main.model.userLoginModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class employeeManageBookingController implements Initializable {

    @FXML
    private Label isConnected;
    // Employee Manage Booking Home Section
    @FXML
    private Button buttonToDeleteBooking;
    @FXML
    private Button buttongBackToEmpHome;
    @FXML
    private Button buttonTOUpdateBooking;
    @FXML
    private Button buttonCheckIn;

    // Employee Manage Booking Delete Section
    @FXML
    private Button buttonDoneDeleteBooking;
    @FXML
    private Button buttonGoBackToManageBooking;
    @FXML
    private TextField txtBookingIdToDelete;
    @FXML
    private TextField txtEmpIdOfBkingIDToDelete;

    // Employee Manage Booking Update Section
    @FXML
    private Button buttonDoneUpdateBooking;
    @FXML
    private Button buttonGoBackToManageBookingFromUpdate;
    @FXML
    private TextField txtBookingIdToUpdate;
    @FXML
    private TextField txtEmpIdOfBkingIDToUpdate;
    @FXML
    private TextField txtDateToUpdate;

    // Employee Manage Check In Section
    @FXML
    private Button buttonDoneCheckIn;
    @FXML
    private Button buttonGoBackToManageBookingFromCheckIn;
    @FXML
    private TextField txtEmpIdOfBkingIDToCheckIn;
    @FXML
    private TextField txtBookingIdToCheckIn;

    private userBookingModel userBookingModel = new userBookingModel();
    private userAccountHandlingModel userAccountHandlingModel = new userAccountHandlingModel();
    private userLoginModel userLoginModel = new userLoginModel();
    private Scene currentScene;
    private Calendar cal = Calendar.getInstance();
    private Date todaysDate = cal.getTime();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (userBookingModel.isDbConnected() && userAccountHandlingModel.isDbConnected() && userLoginModel.isDbConnected()) {
            isConnected.setText("Connected");
        } else {
            isConnected.setText("Not Connected");
        }
    }

    // Employee Manage Booking Home Section
    public void goToDeleteBookingFromMB(ActionEvent actionEvent) {
        currentScene = buttonToDeleteBooking.getScene();
        Window window = currentScene.getWindow();
        Stage primaryStage = (Stage) window;
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../view/employeeManageBookingDeleteBooking.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene nextScene = new Scene(root, 523.0, 387.0);
        primaryStage.setScene(nextScene);
        primaryStage.setTitle("Delete Booking");
    }

    public void goBackToEmpHomeFromMB(ActionEvent actionEvent) {
        currentScene = buttongBackToEmpHome.getScene();
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

    public void goToUpdateBookingFromMB(ActionEvent actionEvent) {
        currentScene = buttonTOUpdateBooking.getScene();
        Window window = currentScene.getWindow();
        Stage primaryStage = (Stage) window;
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../view/employeeManageBookingUpdateBooking.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene nextScene = new Scene(root, 523.0, 387.0);
        primaryStage.setScene(nextScene);
        primaryStage.setTitle("Update Booking");
    }

    public void goToCheckInFromMB(ActionEvent actionEvent) {
        currentScene = buttonCheckIn.getScene();
        Window window = currentScene.getWindow();
        Stage primaryStage = (Stage) window;
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../view/employeeManageBookingCheckIn.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene nextScene = new Scene(root, 523.0, 387.0);
        primaryStage.setScene(nextScene);
        primaryStage.setTitle("Check In");
    }

    // Employee Manage Booking Delete Section
    public void goDoneBackToManageBookingFromDelete(ActionEvent actionEvent) throws ParseException, SQLException, InterruptedException {
        try {
            if (!(txtBookingIdToDelete.getText().isEmpty() && txtEmpIdOfBkingIDToDelete.getText().isEmpty())) {
                int bookingIDUserTyped = Integer.parseInt(txtBookingIdToDelete.getText());
                String usernameUserTyped = txtEmpIdOfBkingIDToDelete.getText();
                int deskID = userBookingModel.getDeskID(usernameUserTyped);
                if (userAccountHandlingModel.allExistingUserNames().contains(usernameUserTyped)) {
                    if (userLoginModel.getUserSignedIn().contains(usernameUserTyped)) {
                        if (userBookingModel.getAllExistingBkingID().contains(bookingIDUserTyped)) {
                            userBookingModel.setDeleteBooking(bookingIDUserTyped);
                            userBookingModel.setDeskUnlockdown(deskID);
                            if (userBookingModel.setDeskUnlockdown(bookingIDUserTyped)){
                                currentScene = buttonDoneDeleteBooking.getScene();
                                Window window = currentScene.getWindow();
                                Stage primaryStage = (Stage) window;
                                Parent root = null;
                                try {
                                    root = FXMLLoader.load(getClass().getResource("../view/employeeManageBooking.fxml"));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                Scene nextScene = new Scene(root, 523.0, 387.0);
                                primaryStage.setScene(nextScene);
                                primaryStage.setTitle("Manage Booking");
                            } else {
                                isConnected.setText("There was an issue unlocking booking seat. Ask admin to unlock it.");
                            }
                        } else {
                            isConnected.setText("Booking ID doesn't exists");
                        }
                    } else {
                        isConnected.setText("This username is not your username");
                    }
                } else {
                    isConnected.setText("username doesn't exists");
                }
            } else {
                isConnected.setText("Field empty!");
            }
        } catch (SQLException e) {
            isConnected.setText("Booking ID is a number and all fields are to be filled");
        } catch (Exception e) {
            isConnected.setText("Error try again after quitting or change booking ID to int if its not already");
        }
    }

    public void goBackToManageBookingWOSaveFromDelete(ActionEvent actionEvent) {
        currentScene = buttonGoBackToManageBooking.getScene();
        Window window = currentScene.getWindow();
        Stage primaryStage = (Stage) window;
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../view/employeeManageBooking.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene nextScene = new Scene(root, 523.0, 387.0);
        primaryStage.setScene(nextScene);
        primaryStage.setTitle("Manage Booking");
    }

    // Employee Manage Booking Update Section

    public void goDoneBackToManageBookingFromUpdate(ActionEvent actionEvent) throws SQLException, ParseException {// Done update
        try {
            if (!(txtDateToUpdate.getText().isEmpty() || txtBookingIdToUpdate.getText().isEmpty() || txtEmpIdOfBkingIDToUpdate.getText().isEmpty())) {
                String usernameUserTyped = txtEmpIdOfBkingIDToUpdate.getText();
                int bookingIDUserTyped = Integer.parseInt(txtBookingIdToUpdate.getText());
                Date dateFinalBookingTheyTypedd = new SimpleDateFormat("yyyy-MM-dd").parse(txtDateToUpdate.getText());
                String dateFinalBookingTheyTyped = txtDateToUpdate.getText();
                if (userAccountHandlingModel.allExistingUserNames().contains(usernameUserTyped)) {
                    if (userLoginModel.getUserSignedIn().contains(usernameUserTyped)) {
                        Date booking = userBookingModel.getCurrentBooking(usernameUserTyped).getDateFinalBooking();
                        long frtEightHrsB4BookingDate = booking.getTime() - 1000 * 60 * 60 * 48;
                        Date frtEightHrsB4BookingDateInDate = new Date(frtEightHrsB4BookingDate);
                        if (dateFinalBookingTheyTypedd.after(todaysDate)) {
                            if (todaysDate.before(frtEightHrsB4BookingDateInDate)) {
                                if (userBookingModel.getAllExistingBkingID().contains(bookingIDUserTyped)) {
                                    userBookingModel.setUpdateBooking(bookingIDUserTyped, dateFinalBookingTheyTyped);
                                    currentScene = buttonDoneUpdateBooking.getScene();
                                    Window window = currentScene.getWindow();
                                    Stage primaryStage = (Stage) window;
                                    Parent root = null;
                                    try {
                                        root = FXMLLoader.load(getClass().getResource("../view/employeeManageBooking.fxml"));
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    Scene nextScene = new Scene(root, 523.0, 387.0);
                                    primaryStage.setScene(nextScene);
                                    primaryStage.setTitle("Manage Booking");
                                } else {
                                    isConnected.setText("Booking ID doesn't exists");
                                }
                            } else {
                                isConnected.setText("Too late to edit date");
                            }
                        } else {
                            isConnected.setText("Please pick a date in the future");
                        }
                    } else {
                        isConnected.setText("This username is not your username");
                    }
                } else {
                    isConnected.setText("username doesn't exists");
                }

            } else {
                isConnected.setText("Empty Fields");
            }
        } catch (ParseException | SQLException e) {
            isConnected.setText("Date in yyyy-MM-dd format, booking ID is a number and all fields are to be filled");
        } catch (Exception e) {
            isConnected.setText("Error try again after quitting or change booking ID to int if its not already");
        }

    }

    public void goBackToManageBookingWOSaveFromUpdate(ActionEvent actionEvent) {
        currentScene = buttonGoBackToManageBookingFromUpdate.getScene();
        Window window = currentScene.getWindow();
        Stage primaryStage = (Stage) window;
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../view/employeeManageBooking.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene nextScene = new Scene(root, 523.0, 387.0);
        primaryStage.setScene(nextScene);
        primaryStage.setTitle("Manage Booking");
    }

    // Employee Manage Booking Check In Section
    public void goDoneBackToManageBookingFromCheckIn(ActionEvent actionEvent) {
        try {
            if (!(txtBookingIdToCheckIn.getText().isEmpty() && txtEmpIdOfBkingIDToCheckIn.getText().isEmpty())) {
                int bookingIDUserTyped = Integer.parseInt(txtBookingIdToCheckIn.getText());
                String usernameUserTyped = txtEmpIdOfBkingIDToCheckIn.getText();
                String dateReturn;
                if (userAccountHandlingModel.allExistingUserNames().contains(usernameUserTyped)) {
                    if (userLoginModel.getUserSignedIn().contains(usernameUserTyped)) {
                        if (userBookingModel.getAllExistingBkingID().contains(bookingIDUserTyped)) {
                            if (userBookingModel.getCurrentBooking(usernameUserTyped).getBookingStatus().equals("APPROVED")) {
                                MakeBooking madeBooking = new MakeBooking(userBookingModel.getCurrentBooking(usernameUserTyped).getBookingID(),
                                        userBookingModel.getCurrentBooking(usernameUserTyped).getDeskID(), userBookingModel.getCurrentBooking(usernameUserTyped).getUserName(),
                                        userBookingModel.getCurrentBooking(usernameUserTyped).getDateFinalBooking(),
                                        userBookingModel.getCurrentBooking(usernameUserTyped).getBookingStatus(), userBookingModel.getCurrentBooking(usernameUserTyped).getcheckInStatus());
                                dateReturn = userBookingModel.getBookingDate(usernameUserTyped);
                                //Method for date conversion from https://www.java2novice.com/java-util-date-example/check-before-date/
                                long oneDayB4BookingDate = userBookingModel.getCurrentBooking(usernameUserTyped).getDateFinalBooking().getTime() - 1000 * 60 * 60 * 24;
                                Date oneDayB4BookingDateInDate = new Date(oneDayB4BookingDate);
                                //////////
                                if (!todaysDate.after(userBookingModel.getCurrentBooking(usernameUserTyped).getDateFinalBooking())) {
                                    if (todaysDate.after(oneDayB4BookingDateInDate) && todaysDate.before(userBookingModel.getCurrentBooking(usernameUserTyped).getDateFinalBooking())) {
                                        userBookingModel.setCheckIn(madeBooking, dateReturn);
                                        userBookingModel.setDeskUnlockdown(madeBooking.getDeskID());
                                        userBookingModel.setDeleteBooking(userBookingModel.getBookingID(usernameUserTyped));//REMOVE from booking table as it only has ones that are in the future
                                        currentScene = buttonDoneCheckIn.getScene();
                                        Window window = currentScene.getWindow();
                                        Stage primaryStage = (Stage) window;
                                        Parent root = null;
                                        try {
                                            root = FXMLLoader.load(getClass().getResource("../view/employeeManageBooking.fxml"));
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        Scene nextScene = new Scene(root, 523.0, 387.0);
                                        primaryStage.setScene(nextScene);
                                        primaryStage.setTitle("Manage Booking");
                                    } else {
                                        isConnected.setText("Check in only 1 day before booking date");
                                    }
                                } else {
                                    int deskID = userBookingModel.getDeskID(usernameUserTyped);
                                    userBookingModel.setDeskUnlockdown(deskID);
                                    userBookingModel.setDeleteBooking(bookingIDUserTyped);
                                    isConnected.setText("Too late to check in, deleting booking");
                                }
                            } else {
                                isConnected.setText("This booking is not yet approved by admin");
                            }
                        } else {
                            isConnected.setText("Booking ID doesn't exists");
                        }
                    } else {
                        isConnected.setText("This username is not your username");
                    }
                } else {
                    isConnected.setText("username doesn't exists");
                }
            } else {
                isConnected.setText("Field empty!");
            }
        } catch (Exception e) {
            isConnected.setText("Booking id is a number from 1-16");
        }

    }

    public void goBackToManageBookingWOSaveFromCheckIn(ActionEvent actionEvent) {
        currentScene = buttonGoBackToManageBookingFromCheckIn.getScene();
        Window window = currentScene.getWindow();
        Stage primaryStage = (Stage) window;
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../view/employeeManageBooking.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene nextScene = new Scene(root, 523.0, 387.0);
        primaryStage.setScene(nextScene);
        primaryStage.setTitle("Manage Booking");
    }
}
