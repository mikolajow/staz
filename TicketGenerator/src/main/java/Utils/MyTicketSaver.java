package Utils;

import DataTemplates.Ticket;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class MyTicketSaver {

    public static void saveTicketToJsonFile(Ticket ticketToSave, String filePath) throws IOException {
        String stringTicket = ticketToSave.toString();
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filePath)));
        writer.write(stringTicket);
        writer.close();
    } // saveTicketToJsonFile()
} // class















