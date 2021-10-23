package main.transferEntityDetails;

public class Employee extends GeneralAccRequirements {
    public Employee(String username, String firstName, String lastName, String role, String secretQuestion,
                    String answerToSecretQuestion, int empID, String password, String userStatus) {
        super(username, firstName, lastName, role, secretQuestion, answerToSecretQuestion, empID, password, userStatus);
    }
}
