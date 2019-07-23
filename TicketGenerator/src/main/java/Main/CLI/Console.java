package Main.CLI;


import DataTemplates.EPassDetails;
import DataTemplates.Ticket;
import Main.CLI.Comands.*;
import Utils.MyTicketSaver;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Console {

    private final String PRINT_COMMAND = "print";
    private final String DATABASE_COMMAND = "db";
    private final String FILE_COMMAND = "file";
    private final String DB_FILE_COMMAND = "db_file";
    private final String HELP_COMMAND = "help";

    private final String WRONG_COMBINATION = "unsupported combination";

    private JCommander jcommander;
    private ConsolePrint consolePrint;
    private DataBaseSave dataBaseSave;
    private FileSave fileSave;
    private FromDbToFile fromDbToFile;
    private HelpCommand helpCommand;


    public Console() {
        prepareConsoleInterface();

    }


    public void runConsole(String[] args) {
        this.prepareConsoleInterface();
        this.handleInputArgs(args);
    }


    private void prepareConsoleInterface() {
        this.consolePrint = new ConsolePrint();
        this.dataBaseSave = new DataBaseSave();
        this.fileSave = new FileSave();
        this.fromDbToFile = new FromDbToFile();
        this.helpCommand = new HelpCommand();

        this.jcommander = JCommander.newBuilder()
                .addCommand(PRINT_COMMAND, consolePrint)
                .addCommand(DATABASE_COMMAND, dataBaseSave)
                .addCommand(FILE_COMMAND, fileSave)
                .addCommand(DB_FILE_COMMAND, fromDbToFile)
                .addCommand(HELP_COMMAND, helpCommand)
                .build();
    }


    private void handleInputArgs(String[] args) {
        try {
            jcommander.parse(args);
            String currentCommand = jcommander.getParsedCommand();
            switch (currentCommand) {
                case PRINT_COMMAND: {
                    printJson();
                    break;
                }
                case DATABASE_COMMAND: {
                    saveToDb();
                    break;
                }
                case FILE_COMMAND: {
                    saveToFile();
                    break;
                }
                case DB_FILE_COMMAND: {
                    saveFromDbToFile();
                    break;
                }
                case HELP_COMMAND: {
                    printHelp();
                    break;
                }
            } // switch
        } catch (ParameterException e) {
            //e.printStackTrace();
            System.out.println(welcomeMessage);
            jcommander.usage();
        } catch (IllegalArgumentException e) {
            //e.printStackTrace();
            System.out.println("wrong input data");
        } catch (NullPointerException e) {
            //e.printStackTrace();
            System.out.println("Command is required");
        }
    }


    private void printJson() throws IllegalArgumentException{
        Ticket newTicket;
        if(consolePrint.getExternalDataPath() == null)
            newTicket = createTicketFromStrings(consolePrint.getTicketStatus(), consolePrint.getEpassStatus());
        else
            newTicket = createTicketWithExternalData(consolePrint.getTicketStatus(), consolePrint.getEpassStatus(),
                    consolePrint.getExternalDataPath());
        if(newTicket != null)
            System.out.println(newTicket);
        else
            System.out.println(WRONG_COMBINATION);
    }


    private void saveToDb() throws IllegalArgumentException{
        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("my-persistence-unit");
        EntityManager entityManager = managerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        Ticket newTicket;
        if(dataBaseSave.getExternalDataPath() == null)
                newTicket = createTicketFromStrings(dataBaseSave.getTicketStatus(), dataBaseSave.getEpassStatus());
        else
            newTicket = createTicketWithExternalData(dataBaseSave.getTicketStatus(), dataBaseSave.getEpassStatus(),
                    dataBaseSave.getExternalDataPath());

        if(newTicket != null) {
            try {
                transaction.begin();
                entityManager.persist(newTicket);
                transaction.commit();
                System.out.println("Ticket id in database is:" + newTicket.getId());
            } catch (Exception e) {
                if (transaction != null)
                    transaction.rollback();
                e.printStackTrace();
            } finally {
                if (entityManager != null)
                    entityManager.close();
            } // finally
        } else
            System.out.println(WRONG_COMBINATION);
    }



    private void saveToFile() throws IllegalArgumentException{
        Ticket newTicket;
        if(fileSave.getExternalDataPath() == null)
            newTicket = createTicketFromStrings(fileSave.getTicketStatus(), fileSave.getEpassStatus());
        else
            newTicket = createTicketWithExternalData(fileSave.getTicketStatus(), fileSave.getEpassStatus(),
                    fileSave.getExternalDataPath());

        if(newTicket != null) {
            String fileFullPath = generateFilePath(fileSave.getSavePath(), newTicket);
            try {
                MyTicketSaver.saveTicketToJsonFile(newTicket, fileFullPath);
                System.out.println("File saved at: " + fileFullPath);
            } catch (IOException e) {
                e.printStackTrace();
            } // catch
        } else
            System.out.println(WRONG_COMBINATION);
    }


    private void saveFromDbToFile() {
        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("my-persistence-unit");
        EntityManager entityManager = managerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Ticket ticketToSave = entityManager.find(Ticket.class, fromDbToFile.getIndex());
            transaction.commit();
            String fileFullPath = generateFilePath(fromDbToFile.getSavePath(), ticketToSave);
            MyTicketSaver.saveTicketToJsonFile(ticketToSave, fileFullPath);
            System.out.println("File saved at: " + fileFullPath);
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        } finally {
            if (entityManager != null)
                entityManager.close();
        }
    }


    private Ticket createTicketFromStrings(String ticketStatus, String epassStatus) {
        System.out.println(ticketStatus);
        System.out.println(epassStatus);
        Ticket.ValidityState ticketEnum = Ticket.ValidityState.valueOf(ticketStatus);
        EPassDetails.PassStatus epassEnum = EPassDetails.PassStatus.valueOf(epassStatus);

        if(validateInputs(ticketEnum, epassEnum))
            return new Ticket(ticketEnum, epassEnum);
        else
            return null;
    }


    private Ticket createTicketWithExternalData(String ticketStatus, String epassStatus, String externalDataPath) {
        Ticket.ValidityState ticketEnum = Ticket.ValidityState.valueOf(ticketStatus);
        EPassDetails.PassStatus epassEnum = EPassDetails.PassStatus.valueOf(epassStatus);

        if(validateInputs(ticketEnum, epassEnum))
            return new Ticket(ticketEnum, epassEnum, externalDataPath);
        else
            return null;
    }


    private boolean validateInputs(Ticket.ValidityState ticketStatus,
                                   EPassDetails.PassStatus ePassStatus) {
        switch (ticketStatus) {
            case NOT_STARTED:
                if (ePassStatus.equals(EPassDetails.PassStatus.VALID) ||
                        ePassStatus.equals(EPassDetails.PassStatus.EXPIRED))
                    return false;
                else
                    return true;
            case VALID_TODAY:
                if (ePassStatus.equals(EPassDetails.PassStatus.NOT_STARTED) ||
                        ePassStatus.equals(EPassDetails.PassStatus.EXPIRED))
                    return false;
                else
                    return true;
                // VALID_YESTERDAY + NOT_VALID
            default:
                if (ePassStatus.equals(EPassDetails.PassStatus.VALID) ||
                        ePassStatus.equals(EPassDetails.PassStatus.NOT_STARTED))
                    return false;
                else
                    return true;
        } // switch
    }

    private String generateFilePath(String savePath, Ticket ticket) {
        StringBuilder builder = new StringBuilder();
        builder
                .append(savePath)
                .append("\\")
                .append(ticket.getValidityState())
                .append("-")
                .append(ticket.getePassDetailsStatus())
                .append("_")
                .append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH-mm-ss-SSS")))
                .append(".json");
        return builder.toString();
    }


    public void printHelp() {
        System.out.println(welcomeMessage);
        jcommander.usage();
    }


    private static final String welcomeMessage =
                    "\n" +
                    "Available combinations : " +
                    "\n VALID_TODAY: " +
                    "\n - VALID " +
                    "\n - REFUNDED " +
                    "\n - BLOCKED " +
                    "\n" +
                    "\n VALID_YESTERDAY: " +
                    "\n - BLOCKED " +
                    "\n - EXPIRED " +
                    "\n - REFUNDED " +
                    "\n" +
                    "\n NOT_STARTED: " +
                    "\n - REFUNDED " +
                    "\n - NOT_STATRED " +
                    "\n - BLOCKED " +
                    "\n" +
                    "\n NOT_VALID: " +
                    "\n - BLOCKED " +
                    "\n - EXPIRED " +
                    "\n - REFUNDED \n";


} // class

























