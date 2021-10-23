package Test.model;

import javafx.fxml.Initializable;
import main.model.adminMakeReportModel;
import main.model.userAccountHandlingModel;
import main.model.userBookingModel;
import main.model.userLoginModel;
import main.transferEntityDetails.Desk;
import main.transferEntityDetails.Employee;
import main.transferEntityDetails.GeneralAccRequirements;
import main.transferEntityDetails.MakeBooking;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

/*
 *NOTE: Please run database again before testing as they are hard coded dummy values
 *
 * Things that wont be needing any check as they are just scene changes
 * - Getting into admin home, as login leads to home
 * - Adding new admin and employee function is similar to signup which is tested in userTestAllGeneralFields
 *
 * */

public class adminTestAllFields implements Initializable {
    private final main.model.userLoginModel userLoginModel = new userLoginModel();
    private final main.model.userAccountHandlingModel userAccountHandlingModel = new userAccountHandlingModel();
    private final main.model.userBookingModel userBookingModel = new userBookingModel();
    private final main.model.adminMakeReportModel adminMakeReportModel = new adminMakeReportModel();

    String UN, FN, LN, role, SQ, ASQ, PW, US, bookingStatus;
    int EID, deskID, BkID;

    GeneralAccRequirements dummyAdminCorrectInput = new GeneralAccRequirements("s3842387", "Anjali",
            "Manoj", "ADMIN", "name?",
            "anjali", 100, "password", "ACTIVATED");
    String userSignedIn = dummyAdminCorrectInput.getUsername();


    GeneralAccRequirements dummyEmployeeForCheck = new GeneralAccRequirements("s3842387", "Anjali",
            "Manoj", "ADMIN", "name?",
            "anjali", 100, "password", "ACTIVATED");

    String dateInString = "2021-06-16";
    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateInString);
    MakeBooking newBooking = new MakeBooking(100, 3, dummyEmployeeForCheck.getUsername(),
            date, "WAITING", "WAITING");

    public adminTestAllFields() throws ParseException {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (userLoginModel.isDbConnected() && userAccountHandlingModel.isDbConnected()) {
            System.out.println("Connected");
        } else {
            System.out.println("Not Connected");
        }
    }

    //Sign in
    @Test
    public void adminSignInWithValidInfoTrue() throws SQLException {
        assertTrue(userAccountHandlingModel.insertEmployeeDetailsForSignUp(dummyAdminCorrectInput));
    }

    // Update user given that they are signed in one of admin or employee
    @Test
    public void updateUserTrue() throws SQLException, ParseException {
        userAccountHandlingModel.insertEmployeeDetailsForSignUp(dummyEmployeeForCheck);
        Employee dummyNewEmployeeDetailsForUpdate = new Employee("s3842387", "M",
                "M", "ADMIN", "name?",
                "anjali", 100, "password", "ACTIVATED");
        assertTrue(userAccountHandlingModel.setNewUsers(dummyNewEmployeeDetailsForUpdate,dummyEmployeeForCheck.getUsername()));
        assertEquals("M", userAccountHandlingModel.allUserDetails(dummyEmployeeForCheck.getUsername()).getFirstName());
    }


    //Only admins can access this feature
    @Test
    public void adminDeleteUserWithValidInfoTrue() throws SQLException {
        assertTrue(userAccountHandlingModel.deleteUser(dummyAdminCorrectInput.getUsername()));
        assertTrue(userAccountHandlingModel.deleteUser(dummyEmployeeForCheck.getUsername()));
    }

    //Confirm booking given that they exist the time constraints are done in controller, not in this method
    @Test
    public void adminConfirmBookinhWithValidInfoTrue() throws SQLException, ParseException {
        userBookingModel.setNewBooking(newBooking, dateInString);
        assertTrue(userBookingModel.setConfirmBooking(newBooking.getBookingID()));
    }

    //Cancel booking
    @Test
    public void adminCancelBookinhWithValidInfoTrue() throws SQLException, ParseException {
        userBookingModel.setNewBooking(newBooking, dateInString);
        assertTrue(userBookingModel.setDeleteBooking(newBooking.getBookingID()));
    }

    //Make booking
    @Test
    public void adminMakeNewBookingTrue() throws SQLException, ParseException {
        assertTrue(userBookingModel.setNewBooking(newBooking, dateInString));
    }

    //View Desk Details
    @Test
    public void adminViewDeskDetOf() throws SQLException, ParseException {
        userBookingModel.setNewBooking(newBooking, dateInString);
        userBookingModel.setDeskBooked(newBooking.getDeskID());
        List<Desk> allDesk = userBookingModel.getAllDeskStatus();
        for (int i = 0; i <allDesk.size() ; i++) {
            int deskID=allDesk.get(i).getDeskID();
            if (deskID==newBooking.getDeskID()){//Since the booking is made for desk 3 we
                // definitely know that the status will be "BOOKED"
                assertEquals("BOOKED", allDesk.get(i).getDeskStatus());
            }
        }
    }

    @Test
    public void adminSetLockdownandView() throws SQLException, ParseException {
        assertTrue(userBookingModel.setDeskLockdown(newBooking.getDeskID()));
        List<Desk> allDesk = userBookingModel.getAllDeskStatus();
        for (int i = 0; i <allDesk.size() ; i++) {
            int deskID=allDesk.get(i).getDeskID();
            if (deskID==newBooking.getDeskID()){
                //Since the booking with deskID of 3 is being on lockdown, it deletes
                // the booking and sets the desk status to "LOCKDOWN"
                assertEquals("LOCKDOWN", allDesk.get(i).getDeskStatus());
            }
        }
    }

    //Given that booking exists, make a report for the employee
    @Test
    public void adminMakeReport() throws SQLException, ParseException {
        userBookingModel.setNewBooking(newBooking, dateInString);
        assertTrue(adminMakeReportModel.makeReportFromDatePresentFuture(dateInString));
    }

    //Given that employee exists, make a report for the employee
    @Test
    public void adminMakeReportWithEmployeeDet() throws SQLException, ParseException {
        userAccountHandlingModel.insertEmployeeDetailsForSignUp(dummyEmployeeForCheck);
        assertTrue(adminMakeReportModel.makeReportFromEmployee(newBooking.getUserName()));
    }

    @AfterEach
    public void delete() throws SQLException, ParseException {
        userAccountHandlingModel.deleteUser(dummyAdminCorrectInput.getUsername());
        userAccountHandlingModel.deleteUser(dummyEmployeeForCheck.getUsername());
    }
}