package main.transferEntityDetails;

public class Desk {
    int DeskID;
    String Status;

    public Desk(int DeskID, String Status) {
        this.DeskID = DeskID;
        this.Status = Status;
    }

    public int getDeskID() {
        return DeskID;
    }

//    public void setDeskID(int DeskID) {
//        this.DeskID = this.DeskID;
//    }

    public String getDeskStatus() {
        return Status;
    }

    public void setDeskStatus(String username) {
        this.Status = this.Status;
    }

}
