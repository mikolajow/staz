package src;

import java.util.Comparator;
import java.util.Date;

public class Status_DateComparator implements Comparator<Ticket> {


    @Override
    public int compare(Ticket o1, Ticket o2) {

        String firstStatus = o1.getTicketStatus();
        String secondStatus = o2.getTicketStatus();

        Date firstDate = o1.getActivationDate();
        Date secondDate = o2.getActivationDate();


        if (firstStatus.equals(secondStatus))
            if (firstStatus.equals("NOT_STARTED"))
                return firstDate.compareTo(secondDate);
            else
                return 0;
        else if (firstStatus.equals("code 500"))
            return 1;
        else if (secondStatus.equals("code 500"))
            return -1;
        else if (firstStatus.equals("NOT_VALID"))
            return 1;
        else
            return -1;

    } // compare




} // class
