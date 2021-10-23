package main.transferEntityDetails;

import java.util.Date;

public class GeneralBookingDet {
    Date dateNeedBooking;
    //    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-DD hh:mm");
    Date dateFinalBooking;
    //    Date neww = new SimpleDateFormat("yyyy-MM-dd").parse(dateFinalBooking.toString());
    String userName;
    Integer bookingID;
    String bookingStatus;
    Integer deskID;
    String checkInStatus;

    // Insert into `Booking` VALUES ('1', '3842387', '2022-01-01','APPROVED');

    // User cant enter booking id so sort booking if in asending or des order and
    // get last vale and add
    // 1 to it, this is the new booking id for new user

    // String bookingStatus automaticially approved if no clash in date and booking
    // seat

    public GeneralBookingDet(Integer bookingID, Integer deskID, String userName, Date dateFinalBooking,
                             String bookingStatus, String checkInStatus) {
        this.bookingID = bookingID;
        this.deskID = deskID;
        this.userName = userName;
        this.dateFinalBooking = dateFinalBooking;
        this.bookingStatus = bookingStatus;
        this.checkInStatus = checkInStatus;
    }

    public Date getDdateNeedBooking() {
        return dateNeedBooking;
    }

    public void setDateNeedBooking(Date dateNeedBooking) {
        this.dateNeedBooking = dateNeedBooking;
    }

    public Date getDateFinalBooking() {
        return dateFinalBooking;
    }

    public void setDateFinalBooking(Date dateFinalBooking) {
        this.dateFinalBooking = dateFinalBooking;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getBookingID() {
        return bookingID;
    }

    public void setbookingID(Integer bookingID) {
        this.bookingID = bookingID;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public Integer getDeskID() {
        return deskID;
    }

    public void setDeskID(int deskID) {
        this.deskID = deskID;
    }

    public String getcheckInStatus() {
        return checkInStatus;
    }

    public void setcheckInStatus(String checkInStatus) {
        this.checkInStatus = checkInStatus;
    }
}
