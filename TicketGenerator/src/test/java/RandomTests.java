import DataTemplates.EPassDetails;
import DataTemplates.Ticket;
import TicketSaver.LocalDateAdapter;
import TicketSaver.LocalDateTimeAdapter;
import TicketSaver.SaverForTicket;
import com.github.javafaker.Faker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Test;


import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class RandomTests {




    @Test
    public void ticketConstructionTest() {

        Ticket ticket = new Ticket(Ticket.ValidityState.VALID_TODAY, EPassDetails.PassStatus.VALID);

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();

        System.out.println();
        System.out.println("JSON = ");
        System.out.println(gson.toJson(ticket));

        try {
            SaverForTicket.saveTicketToJsonFile(ticket, "valid.json");
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println();
        System.out.println("TICKET = ");
        System.out.println(ticket);
    }

    @Test
    public void ticketConstructionTest2() {

        Ticket ticket = new Ticket(Ticket.ValidityState.NOT_VALID, EPassDetails.PassStatus.EXPIRED);

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();

        System.out.println();
        System.out.println("JSON = ");
        System.out.println(gson.toJson(ticket));

        try {
            SaverForTicket.saveTicketToJsonFile(ticket, "not_valid.json");
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println();
        System.out.println("TICKET = ");
        System.out.println(ticket);
    }




    @Test
    public void fromFileTicketConstructionTest() {

        Ticket ticket = new Ticket(Ticket.ValidityState.NOT_VALID, EPassDetails.PassStatus.EXPIRED,
                "C:\\Users\\mikolaj.kasperek\\IdeaProjects\\TicketGenerator\\ExampleTravelerData.json");

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();

        System.out.println();
        System.out.println("JSON = ");
        System.out.println(gson.toJson(ticket));

        try {
            SaverForTicket.saveTicketToJsonFile(ticket, "not_valid.json");
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println();
        System.out.println("TICKET = ");
        System.out.println(ticket);
    }






    @Test
    public void dateTest() {

        Date date = new Date();
        System.out.println("year= " + date.getYear() + " month= " + date.getMonth() + " day= " + date.getDay());
    }










    @Test
    public void passportNumberGeneratorTest() {

        int[] array = new Random().ints(8, 48, 91).toArray();

        for(int c : array){
            System.out.print(c + ", ");
        }

        System.out.println();

        String generatedString = new String(array, 0, 8);
        System.out.println("@@@@@@@@@@@@@@@@@");
        System.out.println(generatedString);
    }




    @Test
    public void dateGeneratorTest() {

        Faker faker = new Faker(new Locale("pl"));


        String name = faker.name().fullName(); // Miss Samanta Schmidt
        String firstName = faker.name().firstName(); // Emory
        String lastName = faker.name().lastName(); // Barton


        Calendar calendar = new GregorianCalendar();
        Date now = calendar.getTime();



        Date date =  faker.date().between(new Date(99, 0, 1), new Date(100, 0, 1));



        System.out.println("year= " + date.getYear() + " month= " + date.getMonth() + " day= " + date.getDay());
        System.out.println("year= " + now.getYear() + " month= " + now.getMonth() + " day= " + now.getDate());
        System.out.println("@@@@@@@@@@@@@");

        System.out.println(name);
        System.out.println(firstName);
        System.out.println(lastName);
    }


} // class













