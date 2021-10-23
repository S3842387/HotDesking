package main.transferEntityDetails;

public class GeneralAccRequirements {// Can be for signing up
    String firstName;
    String lastName;
    String role;
    String secretQuestion;
    String answerToSecretQuestion;
    int empID;
    String username;
    String password;
    String userStatus;

    // Can Be used to sign up but emp id is automatically done like in database
    public GeneralAccRequirements(String username, String firstName, String lastName, String role,
                                  String secretQuestion, String answerToSecretQuestion, int empID, String password, String userStatus) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.secretQuestion = secretQuestion;
        this.answerToSecretQuestion = answerToSecretQuestion;
        this.empID = empID;
        this.password = password;
        this.userStatus = userStatus;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = this.username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSecretQuestion() {
        return secretQuestion;
    }

    public void setSecretQuestion(String secretQuestion) {
        this.secretQuestion = secretQuestion;
    }

    public String getAnswerToSecretQuestion() {
        return answerToSecretQuestion;
    }

    public void setAnswerToSecretQuestion(String answerToSecretQuestion) {
        this.answerToSecretQuestion = answerToSecretQuestion;
    }

    public int getEmpID() {
        return empID;
    }

    public void setEmpID(int empID) {
        this.empID = empID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }
}
