package main.model;

import main.SQLConnection;

import java.sql.*;
import java.util.Locale;

public class userLoginModel implements modelInterface{

    Connection connection;

    public userLoginModel() {

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

    public Boolean isLogin(String user, String pass) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select * from employee where username = ? and password= ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, pass);
            Statement stm = connection.createStatement();
            resultSet = stm.executeQuery("SELECT * from Employee");
            resultSet = preparedStatement.executeQuery();
            stm.close();
            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        } finally {
            preparedStatement.close();
            resultSet.close();
        }
    }

    public Boolean isSignedInRightNow(String user) throws SQLException {
        ResultSet employee = null;
        try {
            Statement stm = connection.createStatement();
            employee = stm.executeQuery("SELECT * from UserSignedInRightNow");
            if (employee.next()) {
                stm.executeUpdate("DELETE from UserSignedInRightNow;");
                stm.executeUpdate("Insert into `UserSignedInRightNow` VALUES ('" + user + "');");
            } else {
                stm.executeUpdate("Insert into `UserSignedInRightNow` VALUES ('" + user + "');");
            }
            stm.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public String getUserSignedIn() throws SQLException {
        String username = null;
        PreparedStatement preparedStatement = null;
        ResultSet employee = null;
        String query = "SELECT * from UserSignedInRightNow";
        try {
            preparedStatement = connection.prepareStatement(query);
            Statement stm = connection.createStatement();
            employee = stm.executeQuery(query);
            username = employee.getString("SignedIn_UserName");
            stm.close();
        } catch (Exception e) {
            return username;
        } finally {
            preparedStatement.close();
            employee.close();
        }
        return username;
    }

    public String UserType(String user) throws SQLException {
        String roleOfUser = null;
        PreparedStatement preparedStatement = null;
        ResultSet employee = null;
        String query = "select Employee.role from Employee where Employee.username = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user);
            Statement stm = connection.createStatement();
            employee = stm.executeQuery("SELECT * from Employee");
            while (employee.next()) {
                if (employee.getString("username").equals(user)) {
                    roleOfUser = employee.getString("role").toUpperCase(Locale.ROOT);
                }
            }
            stm.close();
        } catch (Exception e) {
            return roleOfUser;
        } finally {
            preparedStatement.close();
            employee.close();
        }
        return roleOfUser;
    }

    public String getUserStatus(String user) throws SQLException {
        String status = null;
        PreparedStatement preparedStatement = null;
        ResultSet employee = null;
        String query = "SELECT * from Employee";
        try {
            preparedStatement = connection.prepareStatement(query);
            Statement stm = connection.createStatement();
            employee = stm.executeQuery(query);
            while (employee.next()) {
                if (employee.getString("username").equals(user)) {
                    status = employee.getString("userStatus").toUpperCase(Locale.ROOT);
                }
            }
            stm.close();
        } catch (Exception e) {
            return status;
        } finally {
            preparedStatement.close();
            employee.close();
        }
        return status;
    }

}