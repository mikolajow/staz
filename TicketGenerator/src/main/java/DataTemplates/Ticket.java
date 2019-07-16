


package DataTemplates;


import Utils.MyValuesGenerator;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;


public class Ticket {



    private ValidityState validityState;

    private LocalDateTime activationDate;
    private LocalDate validityDate;

    private EPassDetails ePassDetails;


    public Ticket(ValidityState ticketStatus, EPassDetails.PassStatus ePassStatus) {
        this.validityState = ticketStatus;

        generateActivationAndValidityDate();

        this.ePassDetails = new EPassDetails(ePassStatus, validityDate, validityState);
    } // random traveler constructor


    public Ticket(ValidityState ticketStatus, EPassDetails.PassStatus ePassStatus, String externalDataFilePath) {
        this.validityState = ticketStatus;

        generateActivationAndValidityDate();

        this.ePassDetails = new EPassDetails(ePassStatus, externalDataFilePath, this.validityDate, validityState);
    } // File traveler constructor


    private void generateActivationAndValidityDate() {
        if (this.validityState.equals(ValidityState.NOT_VALID) ||
                this.validityState.equals(ValidityState.NOT_STARTED)) {

            Date randomDate = MyValuesGenerator.date();

            int year = 1900 + randomDate.getYear();
            int month = 1 + randomDate.getMonth();
            int day = randomDate.getDate();

            Random generator = new Random();
            int hour = generator.nextInt(24);
            int minute = generator.nextInt(60);
            int second = generator.nextInt(60);
            int millisecond = generator.nextInt(1000);

            this.validityDate = LocalDate.of(year, month, day);

            this.activationDate = LocalDateTime.of(year, month, day, hour, minute, second, millisecond);
        } else if (this.validityState.equals(ValidityState.VALID_YESTERDAY)) {
            this.activationDate = LocalDateTime.now().minusDays(1);
            this.validityDate = LocalDate.now().minusDays(1);
        } else {
            this.activationDate = LocalDateTime.now();
            this.validityDate = LocalDate.now();
        }
    }


    public enum ValidityState {
        VALID_TODAY,
        VALID_YESTERDAY,
        NOT_STARTED,
        NOT_VALID
    } // enum ValidityState


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder
                .append(validityState)
                .append("\n")
                .append(activationDate.format(DateTimeFormatter.ofPattern("yyyy-mm-dd'T'HH:mm:ss.SSS")))
                .append("\n")
                .append(validityDate)
                .append("\n");

        return stringBuilder.toString();
    }








    public ValidityState getValidityState() {
        return validityState;
    }

    public EPassDetails.PassStatus getePassDetailsStatus() {
        return ePassDetails.getEpassStatus();
    }
} // class















