Drop Table if exists `Employee`;
Drop Table if exists `Desk`;
Drop Table if exists `Booking`;
Drop Table if exists `UserSignedInRightNow`;
Drop Table if exists `AllBooking`;

CREATE TABLE "Employee" (
                            "username" TEXT NOT NULL UNIQUE,
                            "first_name" TEXT,
                            "last_name" TEXT,
                            "role" TEXT CHECK("role" IN ('EMPLOYEE', 'ADMIN')),
                            "secret_question" TEXT,
                            "answer_Sec_quest" TEXT,
                            "employee_id" INTEGER NOT NULL UNIQUE ,
                            "password" TEXT,
                            "userStatus" TEXT NOT NULL DEFAULT 'ACTIVATED'
                                CHECK("userStatus" IN ('ACTIVATED', 'DEACTIVATED')),
                            PRIMARY KEY("username") );
Insert into `Employee` VALUES ('test', 'Anjali', 'Manoj', 'EMPLOYEE', 'What uni do you got to?', 'RMIT', '1', 'test', 'ACTIVATED'),
                              ('admin', 'admin', 'admin', 'ADMIN', 'What uni do you got to?', 'RMIT', '2', 'admin', 'ACTIVATED');

---------------------------------------------------------
CREATE TABLE "UserSignedInRightNow" ( --Stores the user signed in at the moment
                                        "SignedIn_UserName" TEXT UNIQUE,
                                        FOREIGN KEY("SignedIn_UserName") REFERENCES "Employee"("username"), PRIMARY KEY("SignedIn_UserName") );
--No initial value as it is inserted or updated when  user is signed in
-- ------------------------------------------------------------------------------
CREATE TABLE "Booking" ( --Stores current booking(1 per user)
                           "Booking_id" INTEGER NOT NULL UNIQUE,
                           "Booking_UserName" TEXT UNIQUE, --NOT UNIQUE AS one user can book for diffrent dats
                           "Booking_DeskId" INTEGER CHECK(0<Booking_DeskId or Booking_DeskId<17),
                           "Booking_Date" DATE,
                           "Booking_Status" TEXT NOT NULL DEFAULT 'WAITING'
                               CHECK("Booking_Status" IN ('APPROVED','CANCELED', 'REJECTED', 'WAITING')),
                           "BookingCheckIn_Status" TEXT NOT NULL DEFAULT 'PENDING'
                               CHECK("BookingCheckIn_Status" IN ('PENDING','CHECKEDIN')),
                           FOREIGN KEY("Booking_UserName") REFERENCES "Employee"("username"),
                           FOREIGN KEY("Booking_DeskId") REFERENCES "Desk"("Desk_id"), PRIMARY KEY("Booking_id" AUTOINCREMENT) );

Insert into `Booking` VALUES ('1', 'test','1' ,'2021-06-19','WAITING','PENDING');

-- ------------------------------------------------------------------------------
CREATE TABLE "Desk" ( "Desk_id" INTEGER NOT NULL UNIQUE CHECK(Desk_id<17 and 0<Desk_id),--can use get number if even then set true or eg. n+4 = true till 16
                      "Desk_Status" TEXT NOT NULL DEFAULT 'AVAILABLE'
                          CHECK("Desk_Status" IN ('AVAILABLE', 'LOCKDOWN', 'BOOKED')),
                      PRIMARY KEY("Desk_id" AUTOINCREMENT));
-- ------------------------------------------------------------------------------
Insert into `Desk` VALUES (1, 'BOOKED'),(2, 'AVAILABLE'), (3, 'AVAILABLE'), (4, 'AVAILABLE'), (5, 'AVAILABLE'), (6, 'AVAILABLE'),
                          (7, 'AVAILABLE'), (8, 'AVAILABLE'), (9, 'AVAILABLE'), (10, 'AVAILABLE'), (11, 'AVAILABLE'), (12, 'AVAILABLE'), (13, 'AVAILABLE'), (14, 'AVAILABLE'),
                          (15, 'AVAILABLE'), (16, 'AVAILABLE');

-- ------------------------------------------------------------------------------
CREATE TABLE "AllBooking" ( --Stores all booking after check in
                              "AllBooking_id" INTEGER NOT NULL,
                              "AllBooking_UserName" TEXT NOT NULL,
                              "AllBooking_DeskId" INTEGER NOT NULL,
                              "AllBooking_Date" DATE NOT NULL,
                              "AllBooking_Status" TEXT NOT NULL,
                              "AllBookingCheckIn_Status" TEXT NOT NULL DEFAULT 'CHECKEDIN'
                                  CHECK("AllBookingCheckIn_Status" IN ('CHECKEDIN')),
                              FOREIGN KEY("AllBooking_UserName") REFERENCES "Employee"("username"),
                              FOREIGN KEY("AllBooking_DeskId") REFERENCES "Desk"("Desk_id") );

insert into AllBooking (AllBooking_id, AllBooking_UserName, AllBooking_DeskId, AllBooking_Date, AllBooking_Status,
                        AllBookingCheckIn_Status)
values (1, 'test', 2, '2002-06-15','APPROVED', 'CHECKEDIN');