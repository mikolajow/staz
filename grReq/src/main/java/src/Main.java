package src;

import java.util.ArrayList;

import static src.Constants.FILE_NAME;
import static src.Constants.FILE_PATH;

public class Main {

    public static void main(String[] args) {


        src.Reader reader = new src.Reader();

        ArrayList<src.Ticket> data = reader.readFile(FILE_PATH);

        src.TicketChecker ticketChecker = new src.TicketChecker();

        ticketChecker.checkTickets(data);

        src.SaveTicketsCSV saver = new src.SaveTicketsCSV();

        data.sort(new Status_DateComparator());

        saver.saveTickets(data, FILE_NAME);

    } // main

} // class
