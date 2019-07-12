package TicketSaver;

import DataTemplates.Ticket;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;


public class SaverForTicket {


    public static void saveTicketToJsonFile(Ticket ticketToSave, String filePath) throws IOException {

            FileWriter writer = new FileWriter(filePath);

            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                    .create();

            gson.toJson(ticketToSave, writer);

            writer.close();

    } // saveTicketToJsonFile()




} // class















