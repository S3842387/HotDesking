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
import main.model.userBookingModel;
import main.transferEntityDetails.Desk;
import main.transferEntityDetails.MakeBooking;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class userDeskGraphicalVisualController implements Initializable {
    @FXML
    private Label isConnected;
    @FXML
    private Label seat1, seat2, seat3, seat4, seat5, seat6, seat7, seat8, seat9, seat10, seat11, seat12, seat13, seat14, seat15, seat16;
    @FXML
    private Label Allocation1, Allocation2, Allocation3, Allocation4, Allocation5, Allocation6, Allocation7, Allocation8, Allocation9, Allocation10, Allocation11, Allocation12, Allocation13, Allocation14, Allocation15, Allocation16;
    @FXML
    private Button button1, button2, button3, button4, button5, button6, button7,
            button8, button9, button10, button11, button12, button13, button14, button15, button16;
    @FXML
    private Button buttonGoBackToBkngMgmnt, backtodesk;
    @FXML
    private Button buttonGoToLockdownSeats, buttonViewAllocation;
    @FXML
    private Button buttonGoToWhiteListSeats;

    //    private final userDeskModel userDeskModel = new userDeskModel();
    userBookingModel userBookingModel = new userBookingModel();
    String styleGreen = "-fx-background-color: green";
    String styleOrange = "-fx-background-color: orange";
    String styleRed = "-fx-background-color: red";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (userBookingModel.isDbConnected()) {
            isConnected.setText("Connected");
        } else {
            isConnected.setText("Not Connected");
        }

    }


    public void goBackToAdminBknMgmnt(ActionEvent actionEvent) {
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

    public void goToLockdownSeats(ActionEvent actionEvent) throws InterruptedException {
        Scene currentScene = buttonGoToLockdownSeats.getScene();
        Window window = currentScene.getWindow();
        Stage primaryStage = (Stage) window;
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../view/adminBookingMgmntViewDeskToLockdown.fxml"));
        } catch (IOException e) {
            e.printStackTrace();

        }
        Scene nextScene = new Scene(root, 523.0, 387.0);
        primaryStage.setScene(nextScene);
        primaryStage.setTitle("Lockdown Seats");
    }

    public void viewSeatStatus(MouseEvent mouseEvent) throws SQLException {
        //Need to enter date then get bookings of all table for the date then pass the colour depending on what the status is
        //Orange for lockdown, red for booked, green for not booked
        List<Label> seatLable = new ArrayList<>();
        List<Desk> allDesk = userBookingModel.getAllDeskStatus();

        seatLable.add(seat1);
        seatLable.add(seat2);
        seatLable.add(seat3);
        seatLable.add(seat4);
        seatLable.add(seat5);
        seatLable.add(seat6);
        seatLable.add(seat7);
        seatLable.add(seat8);
        seatLable.add(seat9);
        seatLable.add(seat10);
        seatLable.add(seat11);
        seatLable.add(seat12);
        seatLable.add(seat13);
        seatLable.add(seat14);
        seatLable.add(seat15);
        seatLable.add(seat16);

        for (int i = 0; i < allDesk.size(); i++) {
            if (allDesk.get(i).getDeskStatus().equals("AVAILABLE")) {
                seatLable.get(i).setStyle(styleGreen);
            } else if (allDesk.get(i).getDeskStatus().equals("LOCKDOWN")) {
                seatLable.get(i).setStyle(styleOrange);
            } else if (allDesk.get(i).getDeskStatus().equals("BOOKED")) {
                seatLable.get(i).setStyle(styleRed);
            } else {//There wont be any 'else' but just in case
                seatLable.get(i).setStyle(styleRed);
            }
        }

    }

    //View desk allocation

    public void ViewAllocation(ActionEvent actionEvent) {
        Scene currentScene = buttonViewAllocation.getScene();
        Window window = currentScene.getWindow();
        Stage primaryStage = (Stage) window;
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../view/adminBookingMgmntViewDeskAllocation.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene nextScene = new Scene(root, 523.0, 387.0);
        primaryStage.setScene(nextScene);
        primaryStage.setTitle("View Desk Allocation");
    }

    public void buttonViewAllocation(MouseEvent mouseEvent) throws SQLException {
        List<Label> tableAllocation = new ArrayList<>();
        List<MakeBooking> allBooking = userBookingModel.allExistingBking();

        tableAllocation.add(Allocation1);
        tableAllocation.add(Allocation2);
        tableAllocation.add(Allocation3);
        tableAllocation.add(Allocation4);
        tableAllocation.add(Allocation5);
        tableAllocation.add(Allocation6);
        tableAllocation.add(Allocation7);
        tableAllocation.add(Allocation8);
        tableAllocation.add(Allocation9);
        tableAllocation.add(Allocation10);
        tableAllocation.add(Allocation11);
        tableAllocation.add(Allocation12);
        tableAllocation.add(Allocation13);
        tableAllocation.add(Allocation14);
        tableAllocation.add(Allocation15);
        tableAllocation.add(Allocation16);


        for (int i = 0; i < allBooking.size(); i++) {
            int deskID=allBooking.get(i).getDeskID();
            String user=allBooking.get(i).getUserName();
                tableAllocation.get(deskID-1).setText(user);
        }
    }

    public void gobacktodesk(ActionEvent actionEvent) {
        Scene currentScene = backtodesk.getScene();
        Window window = currentScene.getWindow();
        Stage primaryStage = (Stage) window;
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../view/userDeskGraphicalVisual.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene nextScene = new Scene(root, 523.0, 387.0);
        primaryStage.setScene(nextScene);
        primaryStage.setTitle("Admin View Desk");
    }

}
