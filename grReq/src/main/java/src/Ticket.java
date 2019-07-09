package src;

import java.util.Date;

public class Ticket {
    private String ePass;
    private String passportEnd;
    private String ticketStatus;
    private Date activationDate;

    public Ticket(String ePass, String passportEnd) {
        this.ePass = ePass;
        this.passportEnd = passportEnd;
    }

    public void setTicketStatus(String newStatus){
        this.ticketStatus = newStatus;
    }

    @Override
    public String toString() {
        return ePass + "    " + passportEnd;
    }

    public String getePass() {
        return ePass;
    }

    public String getPassportEnd() {
        return passportEnd;
    }

    public String getTicketStatus() {
        return ticketStatus;
    }

    public Date getActivationDate() { return activationDate; }

    public void setActivationDate(Date activationDate) { this.activationDate = activationDate; }
} // class
