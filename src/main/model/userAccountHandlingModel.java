package main.model;

import main.SQLConnection;
import main.transferEntityDetails.Employee;
import main.transferEntityDetails.GeneralAccRequirements;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class userAccountHandlingModel implements modelInterface{

    Connection connection;
    String SQLempID = "employee_id";
    String SQLfirstName = "first_name";
    String SQLlastName = "last_name";
    String SQLrole = "role";
    String SQLsecretQuestion = "secret_question";
    String SQLanswerSecretQuestion = "Answer_Sec_quest";
    String SQLusername = "username";
    String SQLpassword = "password";
    String SQLuserStatus = "userStatus";
    String UN, FN, LN, role, SQ, ASQ, PW, US;
    int EID;
    Employee employeedetails = null;
    List<String> allExistingUserNames = new ArrayList<>();

    public userAccountHandlingModel() {
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

    public boolean setNewUsersBooking(String oldUserName, String newUserName) throws SQLException {
        boolean succInsert;
        try {
            Statement st = connection.createStatement();
            st.executeUpdate("update Booking set Booking_UserName = '" + newUserName + "' where Booking_UserName='" + oldUserName + "';");
            succInsert = true;
            st.close();
        } catch (SQLException e) {
            succInsert = false;
        }
        return succInsert;
    }

    public boolean setNewUserAllooking(String oldUserName, String newUserName) throws SQLException {
        boolean succInsert;
        try {
            Statement st = connection.createStatement();
            st.executeUpdate("update AllBooking set AllBooking_UserName = '" + newUserName + "' where AllBooking_UserName='" + oldUserName + "';");
            succInsert = true;
            st.close();
        } catch (SQLException e) {
            succInsert = false;
        }
        return succInsert;
    }

    public Employee allUserDetails(String user) throws SQLException {//Only returns user with username = user, if exists
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select * from Employee where username = '" + user + "';";
        try {
            preparedStatement = connection.prepareStatement(query);
            Statement stm = connection.createStatement();
            resultSet = stm.executeQuery(query);
            while (resultSet.next()) {
                if (resultSet.getString(SQLusername).equals(user)) {
                    UN = resultSet.getString(SQLusername);
                    FN = resultSet.getString(SQLfirstName);
                    LN = resultSet.getString(SQLlastName);
                    role = resultSet.getString(SQLrole);
                    SQ = resultSet.getString(SQLsecretQuestion);
                    ASQ = resultSet.getString(SQLanswerSecretQuestion);
                    EID = Integer.parseInt(resultSet.getString(SQLempID));
                    PW = resultSet.getString(SQLpassword);
                    US = resultSet.getString(SQLuserStatus);
                    employeedetails = new Employee(UN, FN, LN, role, SQ, ASQ, EID, PW, US);
                }
            }
            stm.close();
        } catch (Exception e) {
        } finally {
            preparedStatement.close();
            resultSet.close();
        }
        return employeedetails;
    }

    public List allExistingUserNames() throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select * from Employee;";
        try {
            preparedStatement = connection.prepareStatement(query);
            Statement stm = connection.createStatement();
            resultSet = stm.executeQuery(query);
            while (resultSet.next()) {
                UN = resultSet.getString(SQLusername);
                allExistingUserNames.add(UN);
            }
            stm.close();
        } catch (Exception e) {
        } finally {
            preparedStatement.close();
            resultSet.close();
        }
        return allExistingUserNames;
    }

    public boolean insertEmployeeDetailsForSignUp(GeneralAccRequirements employeeDetails) throws SQLException {
        boolean succInsert;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        UN = employeeDetails.getUsername();
        FN = employeeDetails.getFirstName();
        LN = employeeDetails.getLastName();
        role = employeeDetails.getRole();
        SQ = employeeDetails.getSecretQuestion();
        ASQ = employeeDetails.getAnswerToSecretQuestion();
        EID = employeeDetails.getEmpID();
        PW = employeeDetails.getPassword();
        US = employeeDetails.getUserStatus();

        try {
            Statement st = connection.createStatement();
            st.executeUpdate("Insert into `Employee` VALUES ('" + UN + "', '" + FN + "', '" + LN + "', '" +
                    role.toUpperCase(Locale.ROOT) + "', '" + SQ + "', '" + ASQ + "', '" + EID + "', '" + PW + "', '" + US + "');");
            succInsert = true;
            st.close();
        } catch (SQLException e) {
            succInsert = false;
        }
        return succInsert;
    }

    public boolean setNewUsers(Employee employeeDetails, String oldUserName) throws SQLException {
        String username = employeeDetails.getUsername();
        String first_name = employeeDetails.getFirstName();
        String last_name = employeeDetails.getLastName();
        String role = employeeDetails.getRole();
        String secret_question = employeeDetails.getSecretQuestion();
        String answer_Sec_quest = employeeDetails.getAnswerToSecretQuestion();
        String password = employeeDetails.getPassword();
        String userStatus = employeeDetails.getUserStatus();
        boolean succInsert;

        try {
            Statement st = connection.createStatement();
            st.executeUpdate("update Employee set username='" +
                    username + "', first_name='" + first_name + "', last_name='" + last_name + "', role='" +
                    role + "', secret_question='" + secret_question + "',answer_Sec_quest='" + answer_Sec_quest + "',password='" +
                    password + "',userStatus='" + userStatus + "' where username='" + oldUserName + "';");
            succInsert = true;
            st.close();
        } catch (SQLException e) {
            succInsert = false;
        }
        return succInsert;
    }

    public boolean deleteUser(String oldUserName) throws SQLException {//Deletes user and all related details
        boolean succInsert;
        try {
            Statement st = connection.createStatement();
            st.executeUpdate("DELETE from Booking where Booking_UserName ='" +
                    oldUserName +
                    "';");
            st.executeUpdate("DELETE from Employee where username ='" +
                    oldUserName +
                    "';");
            succInsert = true;
            st.close();
        } catch (SQLException e) {
            succInsert = false;
        }
        return succInsert;
    }
}