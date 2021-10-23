package main.transferEntityDetails;

import java.util.Date;

public class MakeBooking extends GeneralBookingDet {
    public MakeBooking(Integer bookingID, Integer deskID, String userName, Date dateFinalBooking, String bookingStatus, String checkInStatus) {
        super(bookingID, deskID, userName, dateFinalBooking, bookingStatus, checkInStatus);
    }
}
