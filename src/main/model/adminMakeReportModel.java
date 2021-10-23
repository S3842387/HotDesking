package main.model;

import main.SQLConnection;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.*;

public class adminMakeReportModel implements modelInterface {

    // Get data for booking with date or
    // emp details
    Connection connection;

    // for both ADMIN AND EMP FROM ADMIN ACCOUNT
    public adminMakeReportModel() {
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

    public Boolean makeReportFromDatePast(String Date) throws SQLException {
        boolean made = false;
        String csvFilepath = "Report-Date.csv";
        PreparedStatement preparedStatement = null;
        Statement stm = connection.createStatement();
        ResultSet resultSet = null;
        String query = "Select * FROM AllBooking Where AllBooking_Date='" + Date + "';";

        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            FileWriter writer = new FileWriter(csvFilepath);
            BufferedWriter filewriter = new BufferedWriter(writer);

            filewriter.write("Booking_UserName, Booking_id, Booking_DeskId, Booking_Date, Booking_Status, CheckIn_Status");

            while (resultSet.next()) {
                String AllBooking_UserName = resultSet.getString("AllBooking_UserName");
                int AllBooking_id = resultSet.getInt("AllBooking_id");
                int AllBooking_DeskId = resultSet.getInt("AllBooking_DeskId");
                String AllBooking_Date = Date;
                String AllBooking_Status = resultSet.getString("AllBooking_Status");
                String AllBookingCheckIn_Status = resultSet.getString("AllBookingCheckIn_Status");

                if (AllBooking_UserName == null) {
                    AllBooking_UserName = "";
                } else {
                    AllBooking_UserName = "\"" + AllBooking_UserName + "\"";
                }

                String line = String.format("\"%s\", %d,  %d, %s, %s, %s", AllBooking_UserName, AllBooking_id, AllBooking_DeskId,
                        AllBooking_Date, AllBooking_Status, AllBookingCheckIn_Status);

                filewriter.newLine();
                filewriter.write(line);

                made = true;
            }
            stm.close();
            filewriter.close();
        } catch (Exception e) {

        } finally {
            preparedStatement.close();
            resultSet.close();
        }
        return made;
    }

    public Boolean makeReportFromDatePresentFuture(String Date) throws SQLException {
        boolean made = false;
        String csvFilepath = "Report-Date.csv";
        PreparedStatement preparedStatement = null;
        Statement stm = connection.createStatement();
        ResultSet resultSet = null;
        String query = "Select * FROM Booking Where Booking_Date='" + Date + "';";

        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            FileWriter writer = new FileWriter(csvFilepath);
            BufferedWriter filewriter = new BufferedWriter(writer);

            filewriter.write("Booking_UserName, Booking_id, Booking_DeskId, Booking_Date, Booking_Status, CheckIn_Status");

            while (resultSet.next()) {
                String AllBooking_UserName = resultSet.getString("Booking_UserName");
                int AllBooking_id = resultSet.getInt("Booking_id");
                int AllBooking_DeskId = resultSet.getInt("Booking_DeskId");
                String Booking_Date = Date;
                String AllBooking_Status = resultSet.getString("Booking_Status");
                String AllBookingCheckIn_Status = resultSet.getString("BookingCheckIn_Status");

                if (AllBooking_UserName == null) {
                    AllBooking_UserName = "";
                } else {
                    AllBooking_UserName = "\"" + AllBooking_UserName + "\"";
                }

                String line = String.format("\"%s\", %d,  %d, %s, %s, %s", AllBooking_UserName, AllBooking_id, AllBooking_DeskId,
                        Booking_Date, AllBooking_Status, AllBookingCheckIn_Status);

                filewriter.newLine();
                filewriter.write(line);

                made = true;
            }
            stm.close();
            filewriter.close();
        } catch (Exception e) {

        } finally {
            preparedStatement.close();
            resultSet.close();
        }
        return made;
    }

    public Boolean makeReportFromEmployee(String username) throws SQLException {
        boolean made = false;
        String csvFilepath = "Report-Employee-Details.csv";
        PreparedStatement preparedStatement = null;
        Statement stm = connection.createStatement();
        ResultSet resultSet = null;
        String query = "SELECT * FROM Employee WHERE username LIKE '" + username + "';";

        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            FileWriter writer = new FileWriter(csvFilepath);
            BufferedWriter filewriter = new BufferedWriter(writer);

            filewriter.write("Username, Employee_id, First_name, Last_name, Role, Secret_question,Answer_Sec_quest, User_status, Password");

            while (resultSet.next()) {
                String Username = resultSet.getString("username");
                int Employee_id = resultSet.getInt("employee_id");
                String First_name = resultSet.getString("first_name");
                String Last_name = resultSet.getString("last_name");
                String Role = resultSet.getString("role");
                String Secret_question = resultSet.getString("secret_question");
                String Answer_Sec_quest = resultSet.getString("answer_Sec_quest");
                String User_status = resultSet.getString("userStatus");
                String Password = resultSet.getString("password");

                if (Username == null) {
                    Username = "";
                } else {
                    Username = "\"" + Username + "\"";
                }

                String line = String.format("\"%s\", %d, %s, %s, %s, %s, %s, %s, %s", Username, Employee_id, First_name, Last_name, Role
                        , Secret_question, Answer_Sec_quest, User_status, Password);

                filewriter.newLine();
                filewriter.write(line);

                made = true;
            }
            stm.close();
            filewriter.close();
        } catch (Exception e) {

        } finally {
            preparedStatement.close();
            resultSet.close();
        }
        return made;
    }

}
