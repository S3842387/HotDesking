package Test.model;

import javafx.fxml.Initializable;
import main.model.userAccountHandlingModel;
import main.model.userBookingModel;
import main.model.userLoginModel;
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

public class employeeTestAllFields implements Initializable {
    /*
     *NOTE: Please run database again before testing as they are hard coded dummy values
     *
     * Some features are not tested as they dont need any check since they are just scene changes
     *
     * */
    private final userLoginModel userLoginModel = new userLoginModel();
    private final userAccountHandlingModel userAccountHandlingModel = new userAccountHandlingModel();
    private final userBookingModel userBookingModel = new userBookingModel();
    private String UN, FN, LN, role, SQ, ASQ, PW, US, bookingStatus;
    private int EID, deskID, BkID;

    GeneralAccRequirements dummyEmployeeCorrectInput = new GeneralAccRequirements("Dummy", "Dummy",
            "Dummy", "EMPLOYEE", "Dummy?",
            "Dummy", 100, "Dummy", "ACTIVATED");
    String userSignedIn = dummyEmployeeCorrectInput.getUsername();
    String dateInString = "2021-06-15";
    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateInString);
    MakeBooking newBooking = new MakeBooking(100, 3, dummyEmployeeCorrectInput.getUsername(),
            date, "WAITING", "WAITING");

    public employeeTestAllFields() throws ParseException {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (userLoginModel.isDbConnected() && userAccountHandlingModel.isDbConnected()) {
            System.out.println("Connected");
        } else {
            System.out.println("Not Connected");
        }
    }

    //Make Booking
    @Test
    public void employeeMakeNewBookingTrue() throws SQLException, ParseException {
        userAccountHandlingModel.insertEmployeeDetailsForSignUp(dummyEmployeeCorrectInput);
        assertTrue(userBookingModel.setNewBooking(newBooking, dateInString));
    }
    //Making same booking twice
    @Test
    public void employeeMakeNewBookingTwiceFalse() throws SQLException, ParseException {
        userAccountHandlingModel.insertEmployeeDetailsForSignUp(dummyEmployeeCorrectInput);
        userBookingModel.setNewBooking(newBooking, dateInString);
        assertFalse(userBookingModel.setNewBooking(newBooking, dateInString));
    }

    //Just for test two of the same value has been inserted in all booking records. In actual code, with out passing
    // the requirements of the 'if' statements it wouldn't get inserted
    //RUN SEPERATLY
    @Test
    public void employeeBlackList() throws SQLException, ParseException {
        userBookingModel.setNewBooking(newBooking, dateInString);
        userBookingModel.setConfirmBooking(userBookingModel.getBookingID(dummyEmployeeCorrectInput.getUsername()));
        userBookingModel.setCheckIn(newBooking, dateInString);
        List<Integer> blackList = userBookingModel.getBlackList(dummyEmployeeCorrectInput.getUsername());
        List<Integer> expected = new ArrayList<>();
        expected.add(3);
        assertTrue(blackList.contains(3));
    }

    //All acc details
    @Test
    public void employeeShowAllDetailsTrue() throws SQLException, ParseException {
        userAccountHandlingModel.deleteUser(dummyEmployeeCorrectInput.getUsername());
        userAccountHandlingModel.insertEmployeeDetailsForSignUp(dummyEmployeeCorrectInput);
        userBookingModel.setNewBooking(newBooking, dateInString);
        assertEquals(dummyEmployeeCorrectInput.getUsername(), userAccountHandlingModel.allUserDetails(userSignedIn).getUsername());
        UN = userAccountHandlingModel.allUserDetails(userSignedIn).getUsername();
        FN = userAccountHandlingModel.allUserDetails(userSignedIn).getFirstName();
        LN = userAccountHandlingModel.allUserDetails(userSignedIn).getLastName();
        role = userAccountHandlingModel.allUserDetails(userSignedIn).getRole();
        SQ = userAccountHandlingModel.allUserDetails(userSignedIn).getSecretQuestion();
        ASQ = userAccountHandlingModel.allUserDetails(userSignedIn).getAnswerToSecretQuestion();
        EID = userAccountHandlingModel.allUserDetails(userSignedIn).getEmpID();
        PW = userAccountHandlingModel.allUserDetails(userSignedIn).getPassword();
        US = userAccountHandlingModel.allUserDetails(userSignedIn).getUserStatus();
        Date dateInBooking = userBookingModel.getCurrentBooking(userSignedIn).getDateFinalBooking();
        bookingStatus = userBookingModel.getCurrentBooking(userSignedIn).getBookingStatus();
        assertAll(
                () -> assertEquals("Dummy", UN),
                () -> assertEquals("Dummy", FN),
                () -> assertEquals("Dummy", LN),
                () -> assertEquals("EMPLOYEE", role),
                () -> assertEquals("Dummy?", SQ),
                () -> assertEquals("Dummy", ASQ),
                () -> assertEquals(100, EID),
                () -> assertEquals("Dummy", PW),
                () -> assertEquals("ACTIVATED", US),
                () -> assertEquals(date, dateInBooking),
                () -> assertEquals("WAITING", bookingStatus));
    }


//    Cancel/Delete booking given that booking exists
    //RUN SEPARATELY
    @Test
    public void employeeCancelWithValidInfoTrue() throws SQLException, ParseException {
        userAccountHandlingModel.insertEmployeeDetailsForSignUp(dummyEmployeeCorrectInput);
        userBookingModel.setNewBooking(newBooking, dateInString);
        assertTrue(userBookingModel.setDeleteBooking(newBooking.getBookingID()));
    }

    //Check In given that booking is approved
    //RUN SEPARATELY
    @Test
    public void employeeCheckInWithValidInfoEmpTrue() throws SQLException, ParseException {
        userAccountHandlingModel.insertEmployeeDetailsForSignUp(dummyEmployeeCorrectInput);
        userBookingModel.setNewBooking(newBooking, dateInString);
        userBookingModel.setConfirmBooking(userBookingModel.getBookingID(dummyEmployeeCorrectInput.getUsername()));
        assertTrue(userBookingModel.setCheckIn(newBooking, dateInString));
    }

//    Update booking given that booking exists
    @Test
    public void employeeUpdateWithValidInfoTrue() throws SQLException, ParseException {
        String dateNew = "2021-06-15";
        userAccountHandlingModel.insertEmployeeDetailsForSignUp(dummyEmployeeCorrectInput);
        userBookingModel.setNewBooking(newBooking, dateInString);
        userBookingModel.setUpdateBooking(newBooking.getBookingID(), "2022-00-00");
        assertTrue(userBookingModel.setUpdateBooking(newBooking.getBookingID(), dateNew));//Updates booking
        assertEquals(dateNew, userBookingModel.getBookingDate(newBooking.getUserName()));//Check if the new date is right
    }

    @AfterEach
    public void delete() throws SQLException, ParseException {
        userAccountHandlingModel.deleteUser(dummyEmployeeCorrectInput.getUsername());
    }
}