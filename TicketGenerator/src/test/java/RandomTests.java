import DataTemplates.EPassDetails;
import DataTemplates.Ticket;
import Utils.MyTravelerDataReader;
import com.github.javafaker.Faker;
import org.junit.Test;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.*;

public class RandomTests {


    @Test
    public void readingTravelerTest() {
        String[] data =
                MyTravelerDataReader.readTravelerData(
                        "C:\\Users\\mikolaj.kasperek\\IdeaProjects\\TicketGenerator\\ExampleTravelerData.json");
        for(String s : data)
            System.out.println(s);
    }










    @Test
    public void baseTest() {
        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("my-persistence-unit");
        EntityManager entityManager = managerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            Ticket newTicket = new Ticket(Ticket.ValidityState.VALID_TODAY, EPassDetails.PassStatus.VALID);

            entityManager.persist(newTicket);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        } finally {
            if (entityManager != null)
                entityManager.close();
        }
    }

    @Test
    public void baseTest10() {
        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("my-persistence-unit");
        EntityManager entityManager = managerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            for (int i=0; i<10; i++) {
                Ticket newTicket = new Ticket(Ticket.ValidityState.VALID_TODAY, EPassDetails.PassStatus.VALID);
                entityManager.persist(newTicket);
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        } finally {
            if (entityManager != null)
                entityManager.close();
        }
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













