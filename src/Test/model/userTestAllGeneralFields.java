package Test.model;

import javafx.fxml.Initializable;
import main.model.userAccountHandlingModel;
import main.model.userLoginModel;
import main.model.userResetPassModel;
import main.transferEntityDetails.GeneralAccRequirements;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class userTestAllGeneralFields implements Initializable {
    /*
     *
     *
     * Please run database again before testing as they are hard coded dummy values
     *
     * */
    private final userLoginModel userLoginModel = new userLoginModel();
    private final userAccountHandlingModel userAccountHandlingModel = new userAccountHandlingModel();
    private final userResetPassModel userResetPassModel = new userResetPassModel();

    GeneralAccRequirements dummyAdminCorrectInput = new GeneralAccRequirements("s3842387", "Anjali",
            "Manoj", "ADMIN", "name?",
            "anjali", 100, "password", "ACTIVATED");

    GeneralAccRequirements dummyEmployeeCorrectInput = new GeneralAccRequirements("q", "q",
            "q", "EMPLOYEE", "q?",
            "q", 200, "q", "ACTIVATED");

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
    public void userSignInTrueWithValidInfoAdmin() throws SQLException {
        assertTrue(userAccountHandlingModel.insertEmployeeDetailsForSignUp(dummyAdminCorrectInput));
    }

    @Test
    public void userSignInTrueWithValidInfoEmp() throws SQLException {
        assertTrue(userAccountHandlingModel.insertEmployeeDetailsForSignUp(dummyEmployeeCorrectInput));
    }

    @Test
    public void userSignInTrueWithInValidInfo() throws SQLException {
        assertFalse(userLoginModel.isLogin("TEST", "TEST"));
    }

    //Logging in
    @Test
    public void userLoginTrueWithValidUser() throws SQLException {
        assertTrue(userLoginModel.isLogin("test", "test"));
        assertTrue(userLoginModel.isSignedInRightNow("test"));
    }

    @Test
    public void userLoginTrueWithInValidUser() throws SQLException {
        assertFalse(userLoginModel.isLogin("TEST", "TEST"));
    }

    //Reset password
    @Test
    public void resetPasswordTrueWithValidUser() throws SQLException {
        GeneralAccRequirements dummyReset = new GeneralAccRequirements("reset", "q",
                "q", "EMPLOYEE", "q?",
                "q", 1000, "q", "ACTIVATED");
        userAccountHandlingModel.insertEmployeeDetailsForSignUp(dummyReset);
        assertTrue(userResetPassModel.setNewPassword(dummyReset.getUsername(), "reset"));
       userAccountHandlingModel.deleteUser(dummyReset.getUsername());
    }

    @Test
    public void resetPasswordTrueWithInValidUser() throws SQLException {
        assertFalse(userResetPassModel.setNewPassword("ME", "reset"));
    }

    @AfterEach
    public void delete() throws SQLException, ParseException {
        userAccountHandlingModel.deleteUser(dummyAdminCorrectInput.getUsername());
        userAccountHandlingModel.deleteUser(dummyEmployeeCorrectInput.getUsername());
    }
}