package main.model;

import main.SQLConnection;
import org.sqlite.SQLiteException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class userResetPassModel implements modelInterface{

    Connection connection;
    String SQLsecretQuestion = "secret_question";
    String SQLanswerSecretQuestion = "Answer_Sec_quest";
    String SQLusername = "username";
    ResultSet resultSet = null;


    public userResetPassModel() {

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

    public boolean isUsernameCorrect(String User) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select * from Employee where username = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, User);
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

    public List SecQuest(String User) throws SQLException {
        String Quest = "";
        String Answer = "";
        List<String> QuestNDAns = new ArrayList<>();

        PreparedStatement preparedStatement = null;
        String query = "select * from Employee;";
        try {
            preparedStatement = connection.prepareStatement(query);
            Statement stm = connection.createStatement();
            resultSet = stm.executeQuery(query);
            while (resultSet.next()) {
                if (resultSet.getString(SQLusername).equals(User)) {
                    String UN = resultSet.getString(SQLusername);
                    Quest = resultSet.getString(SQLsecretQuestion);
                    Answer = resultSet.getString(SQLanswerSecretQuestion);
                    QuestNDAns.add(0, UN);
                    QuestNDAns.add(1, Quest);
                    QuestNDAns.add(2, Answer);
                }
            }
        } catch (Exception e) {
        } finally {
            preparedStatement.close();
            resultSet.close();
        }
        return QuestNDAns;
    }

    public boolean setNewPassword(String User, String newPass) throws SQLException, SQLiteException {
        boolean succInsert = false;
        String query = "select * from Employee;";
        Statement st = connection.createStatement();
        try {
            resultSet = st.executeQuery(query);
            while (resultSet.next()) {
                if (resultSet.getString(SQLusername).equals(User)) {
                    st.executeUpdate("update Employee set password = '" + newPass + "' where username ='" + User + "';");
                    succInsert = true;
                }
            }
            st.close();
        } catch (SQLException e) {
            succInsert = false;
        }
        return succInsert;
    }
}
