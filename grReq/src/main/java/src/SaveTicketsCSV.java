package src;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SaveTicketsCSV {


    public void saveTickets(ArrayList<Ticket> dataToSave, String fileName) {

        try(BufferedWriter bufferedWriter =
                    new BufferedWriter(new FileWriter(fileName))) {

            StringBuilder line = new StringBuilder();

            for (Ticket ticket : dataToSave) {
                line.append(ticket.getePass());
                line.append(",");
                line.append( "'" + ticket.getPassportEnd());
                line.append(",");
                line.append(ticket.getTicketStatus());
                line.append(",");
                line.append(ticket.getActivationDate());
                line.append("\n");
            }
            bufferedWriter.write(line.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    } // saveTickets

} // class



















