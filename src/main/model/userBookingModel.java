package main.model;

import main.SQLConnection;
import main.transferEntityDetails.Desk;
import main.transferEntityDetails.MakeBooking;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class userBookingModel implements modelInterface {
    // Can be used for all users to make, delete, update booking
    Connection connection;
    String SQLbookingID = "Booking_id";
    String SQLbookinUsername = "Booking_UserName";
    String SQLbookingDeskID = "Booking_DeskId";
    String SQLbookingDate = "Booking_Date";
    String SQLbookingStatus = "Booking_Status";
    String SQLbookingCheckInStatus = "BookingCheckIn_Status";
    List<MakeBooking> bookingAlredyExistList = new ArrayList<>();
    MakeBooking makeBooking = null;
    PreparedStatement preparedStatement = null;
    List<String> AlredyExistListBookingUser = new ArrayList<>();
    List<Integer> AlredyConfirmedListBookingUser = new ArrayList<>();
    int deskID;
    String userName;
    Date dateFinalBooking;
    String bookingStatus;
    int BkID;
    String Status;


    public userBookingModel() {
        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);
    }

    public Boolean isDbConnected() {
        try {
            return !connection.isClosed();
        } catch (Exception e) {
            return false;
        }
    }

    public List getAllExistingBkingID() throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Integer> allExistingID = new ArrayList<>();
        String query = "select * from Booking;";
        try {
            preparedStatement = connection.prepareStatement(query);
            Statement stm = connection.createStatement();
            resultSet = stm.executeQuery(query);
            while (resultSet.next()) {
                BkID = resultSet.getInt(SQLbookingID);
                allExistingID.add(BkID);
            }
            stm.close();
        } catch (Exception e) {
        } finally {
            preparedStatement.close();
            resultSet.close();
        }
        return allExistingID;
    }

    public List getAllExistingBookingUser() throws SQLException {
        ResultSet resultSet = null;
        String query = "select * from Booking;";
        try {
            preparedStatement = connection.prepareStatement(query);
            Statement stm = connection.createStatement();
            resultSet = stm.executeQuery(query);
            while (resultSet.next()) {
                String UN = resultSet.getString(SQLbookinUsername);
                AlredyExistListBookingUser.add(UN);
            }
            stm.close();
        } catch (Exception e) {

        } finally {
            preparedStatement.close();
            resultSet.close();
        }
        return AlredyExistListBookingUser;
    }

    public MakeBooking getCurrentBooking(String user) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select * from Booking where Booking_UserName='" + user + "';";
        try {
            preparedStatement = connection.prepareStatement(query);
            Statement stm = connection.createStatement();
            resultSet = stm.executeQuery(query);
            while (resultSet.next()) {
                if (resultSet.getString(SQLbookinUsername).equals(user)) {
                    deskID = resultSet.getInt(SQLbookingDeskID);
                    userName = resultSet.getString(SQLbookinUsername);
                    dateFinalBooking = new SimpleDateFormat("yyyy-MM-dd").parse(resultSet.getString(SQLbookingDate));
                    bookingStatus = resultSet.getString(SQLbookingStatus);
                    BkID = resultSet.getInt(SQLbookingID);
                    String CIS = resultSet.getString(SQLbookingCheckInStatus);
                    makeBooking = new MakeBooking(BkID, deskID, userName, dateFinalBooking, bookingStatus, CIS);
                }
            }
            stm.close();
        } catch (Exception e) {

        } finally {
            preparedStatement.close();
            resultSet.close();
        }
        return makeBooking;
    }

    public MakeBooking getCurrentBookingFromBkinId(int bkID) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select * from Booking where Booking_id='" + bkID + "';";
        try {
            preparedStatement = connection.prepareStatement(query);
            Statement stm = connection.createStatement();
            resultSet = stm.executeQuery(query);
            while (resultSet.next()) {
                if (resultSet.getString(SQLbookingID).equals(bkID)) {
                    deskID = resultSet.getInt(SQLbookingDeskID);
                    userName = resultSet.getString(SQLbookinUsername);
                    dateFinalBooking = new SimpleDateFormat("yyyy-MM-dd").parse(resultSet.getString(SQLbookingDate));
                    bookingStatus = resultSet.getString(SQLbookingStatus);
                    BkID = resultSet.getInt(SQLbookingID);
                    String CIS = resultSet.getString(SQLbookingCheckInStatus);
                    makeBooking = new MakeBooking(BkID, deskID, userName, dateFinalBooking, bookingStatus, CIS);
                }
            }
            stm.close();
        } catch (Exception e) {

        } finally {
            preparedStatement.close();
            resultSet.close();
        }
        return makeBooking;
    }

    public int getBookingID(String user) throws SQLException {
        ResultSet resultSet = null;
        Statement stm = connection.createStatement();
        String query = "select Booking_id from Booking where Booking_UserName='" + user + "';";
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = stm.executeQuery(query);
            BkID = resultSet.getInt(SQLbookingID);
            return BkID;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return BkID;
    }

    public String getDeskStatus(int deskId) {
        ResultSet resultSet = null;
        String data = "";
        String query = "select * from Desk where Desk_id=" + deskId + ";";
        try {
            preparedStatement = connection.prepareStatement(query);
            Statement stm = connection.createStatement();
            resultSet = stm.executeQuery(query);
            data = resultSet.getString("Desk_Status");
        } catch (SQLException throwables) {
            data = "";
        }
        return data;
    }

    public String getBookingDate(String user) {
        ResultSet resultSet = null;
        String date = "";
        String query = "select Booking_Date from Booking where Booking_UserName='" + user + "';";
        try {
            preparedStatement = connection.prepareStatement(query);
            Statement stm = connection.createStatement();
            resultSet = stm.executeQuery(query);
//            int temp =resultSet.getString(SQLbookingDate);
            date = resultSet.getString(SQLbookingDate);
            return date;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return date;
    }

    public String getBookingUsernameOfID(int BookingID) {
        String bookingUser = "";
        ResultSet resultSet = null;
        String query = "select Booking_UserName from Booking where Booking_id='" + BookingID + "';";
        try {
            preparedStatement = connection.prepareStatement(query);
            Statement stm = connection.createStatement();
            resultSet = stm.executeQuery(query);
            bookingUser = resultSet.getString(SQLbookinUsername);
            return bookingUser;
        } catch (SQLException throwables) {
            bookingUser = "";
        }
        return bookingUser;
    }

    public List allExistingBking() throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<MakeBooking> allExisting = new ArrayList<>();
        String query = "select * from Booking;";
        try {
            preparedStatement = connection.prepareStatement(query);
            Statement stm = connection.createStatement();
            resultSet = stm.executeQuery(query);
            while (resultSet.next()) {
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                BkID = resultSet.getInt(SQLbookingID);
                int DeskID = resultSet.getInt(SQLbookingDeskID);
                String UN = resultSet.getString(SQLbookinUsername);
                Date DFB = formatter.parse(resultSet.getString(SQLbookingDate));
                String BS = resultSet.getString(SQLbookingStatus);
                String CIS = resultSet.getString(SQLbookingCheckInStatus);
                makeBooking = new MakeBooking(BkID, DeskID, UN, DFB, BS, CIS);
                allExisting.add(makeBooking);
            }
            stm.close();
        } catch (Exception e) {
        } finally {
            preparedStatement.close();
            resultSet.close();
        }
        return allExisting;
    }

    public boolean setCheckIn(MakeBooking makeBooking, String Date) throws SQLException, ParseException {
        BkID = makeBooking.getBookingID();
        userName = makeBooking.getUserName();
        deskID = makeBooking.getDeskID();
        dateFinalBooking = makeBooking.getDateFinalBooking();
        bookingStatus = makeBooking.getBookingStatus();
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(
                    "update Booking set BookingCheckIn_Status = '" + "CHECKEDIN" + "' where Booking_id = '" + BkID + "';");

            st.executeUpdate("Insert into `AllBooking` VALUES ('" + BkID + "', '" + userName + "','" + deskID + "' ,'" + Date + "','" +
                    bookingStatus + "', '" + "CHECKEDIN" + "');");
            st.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean setNewBooking(MakeBooking makeBooking, String Date) throws SQLException, ParseException {
        deskID = makeBooking.getDeskID();
        userName = makeBooking.getUserName();
        dateFinalBooking = makeBooking.getDateFinalBooking();
        bookingStatus = makeBooking.getBookingStatus();
        try {
            Statement st = connection.createStatement();
            st.executeUpdate("insert into Booking (Booking_UserName, Booking_DeskId, Booking_Date) values ('" + userName
                    + "', " + deskID + ", '" + Date + "');");
            st.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public int getDeskID(String userName){
        ResultSet resultSet = null;
        int dID = 0;
        String query = "select * from Booking where Booking_UserName='" + userName + "';";
        try {
            preparedStatement = connection.prepareStatement(query);
            Statement stm = connection.createStatement();
            resultSet = stm.executeQuery(query);
            dID = resultSet.getInt(SQLbookingDeskID);
            return dID;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return dID;
    }
    //Admin booking section
    public boolean setDeleteBooking(int BookingID) throws SQLException {
        Boolean ret=false;
        try {
            Statement st = connection.createStatement();
            st.executeUpdate("Delete from Booking where Booking_id=" + BookingID + ";");
            st.close();
            ret= true;
        } catch (SQLException e) {
            ret=false;
        }
        return ret;
    }

    public boolean setUpdateBooking(int BookingID, String dateToUpdate) throws SQLException {
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(
                    "update Booking set Booking_Date = '" + dateToUpdate + "' where Booking_id = '" + BookingID + "';");
            st.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean setConfirmBooking(int BookingIDToConfirm) {
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(
                    "update Booking set Booking_Status = 'APPROVED' where Booking_id = '" + BookingIDToConfirm + "';");
            st.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public List getAllExistingConfirmedBookingUser() throws SQLException {
        ResultSet resultSet = null;
        String query = "select * from Booking WHERE Booking_Status ='APPROVED';";
        try {
            preparedStatement = connection.prepareStatement(query);
            Statement stm = connection.createStatement();
            resultSet = stm.executeQuery(query);
            while (resultSet.next()) {
                int bkingID = resultSet.getInt(SQLbookingID);
                AlredyConfirmedListBookingUser.add(bkingID);
            }
            stm.close();
        } catch (Exception e) {
        } finally {
            preparedStatement.close();
            resultSet.close();
        }
        return AlredyConfirmedListBookingUser;
    }

    //Desk model section
    public Boolean setDeskLockdown(int deskId) throws SQLException {
        boolean succUpdate;
        try {
            Statement st = connection.createStatement();
            st.executeUpdate("update Desk set Desk_Status = 'LOCKDOWN' where Desk_id=" + deskId + ";");
            succUpdate = true;
            st.close();
        } catch (SQLException e) {
            succUpdate = false;
        }
        return succUpdate;
    }

    public Boolean setDeskUnlockdown(int deskId) throws SQLException {
        boolean succUpdate;
        try {
            Statement st = connection.createStatement();
            st.executeUpdate("update Desk set Desk_Status = 'AVAILABLE' where Desk_id=" + deskId + ";");
            succUpdate = true;
            st.close();
        } catch (SQLException e) {
            succUpdate = false;
        }
        return succUpdate;
    }

    public Boolean setDeskBooked(int deskId) throws SQLException {
        boolean succUpdate;
        try {
            Statement st = connection.createStatement();
            st.executeUpdate("update Desk set Desk_Status = 'BOOKED' where Desk_id=" + deskId + ";");
            succUpdate = true;
            st.close();
        } catch (SQLException e) {
            succUpdate = false;
        }
        return succUpdate;
    }

    public List getAllDeskStatus() throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Desk> allDesk = new ArrayList<>();
        int deskID = 0;
        Desk newDesk;
        //Select
        String query = "select * FROM Desk;";
        try {
            preparedStatement = connection.prepareStatement(query);
            Statement stm = connection.createStatement();
            resultSet = stm.executeQuery(query);
            resultSet = preparedStatement.executeQuery();
            stm.close();
            while (resultSet.next()) {
                deskID = Integer.parseInt(resultSet.getString("Desk_id"));
                Status = resultSet.getString("Desk_Status");
                newDesk = new Desk(deskID, Status);
                allDesk.add(newDesk);
            }
        } catch (Exception e) {
        } finally {
            preparedStatement.close();
            resultSet.close();
        }
        return allDesk;
    }

    public List getBlackList(String userName) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Integer> allDeskTheyHaveSatOn = new ArrayList<>();
        int deskID = 0;
        String query = "Select * FROM AllBooking Where AllBooking_UserName = '" + userName + "';";
        try {
            preparedStatement = connection.prepareStatement(query);
            Statement stm = connection.createStatement();
            resultSet = stm.executeQuery(query);
            resultSet = preparedStatement.executeQuery();
            stm.close();
            while (resultSet.next()) {
                deskID = Integer.parseInt(resultSet.getString("AllBooking_DeskId"));
                allDeskTheyHaveSatOn.add(deskID);
            }
        } catch (Exception e) {
        } finally {
            preparedStatement.close();
            resultSet.close();
        }
        return allDeskTheyHaveSatOn;
    }

}