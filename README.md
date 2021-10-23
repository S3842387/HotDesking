# Readme
## COSC2391 Further Programming, SEM 1 2021 Assignment 2
### Anjali Manoj, S3842387
##-------------------------------------------------------------------------------------------
## Packaging
The main class is Main.java
Packaging for classes:
- main.controller
- main.model
- main.view 
  
Packaging for test:
- test.model

## About the project
For this project the requirement was to develop a system to manage ‘Hotdesking’ methodology to allocate tables and seats to Arub employees.

## How to run
The system is run from 'Main' in the 'main' folder. Before running please run the database.
To sign in as an employee, the details are username: test password: test, for admin the details are username: admin password: admin.
Due to some issues with database, after a heavy request, like logging in into a new account of making a booking after deleting, you might have to quit and run again to do the next request. The features work but there are some run-time errors.
When running some tests, due to them being tested with dummy data, please run the database again then run the tests. Some specific ones may need to be run separately.

### Test cases
  -userTestAllGeneralFields: contains test for features not specific to a user
  -adminTestAllFields: contains test for features specific to admin
  -employeeTestAllFields: contains test for features specific to employee


### Know bugs
  - Due to some reason, I'm currently not sure about, the database seems to not have the power to handle the user signing in and employee then logging out and then sign in into another acc, especially if it is an admin. When done in separate session then all functions work properly, but it gets slower otherwise.

### Things that have not been working or complete
As much I have understood from the specification, I have completed everything apart from the issues with the database having timeout issues.


## Reference
Method for date conversion is taken and altered from:
Gootooru, N., 2021. How to check if the date is before the specified date? - Java Date Examples. [online] Java2novice.com. Available at: <http://java2novice.com/java-util-date-example/check-before-date/> [Accessed 14 June 2021].
