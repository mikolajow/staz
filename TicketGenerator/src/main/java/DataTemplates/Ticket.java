package DataTemplates;

import Utils.MyTicketDataGenerator;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;


@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    public LocalDateTime getActivationDate() {
        return activationDate;
    }

    public LocalDate getValidityDate() {
        return validityDate;
    }

    public EPassDetails getePassDetails() {
        return ePassDetails;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "validity_state")
    private ValidityState validityState;

    @Column(name = "activation_date")
    private LocalDateTime activationDate;
    @Column(name = "validity_date")
    private LocalDate validityDate;


    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn (name = "e_pass_id")
    private EPassDetails ePassDetails;



    public Ticket(){}
    public Ticket(ValidityState ticketStatus, EPassDetails.PassStatus ePassStatus) {
        this.validityState = ticketStatus;
        generateActivationAndValidityDate();
        this.ePassDetails = new EPassDetails(ePassStatus, validityDate);
    } // random traveler constructor


    public Ticket(ValidityState ticketStatus, EPassDetails.PassStatus ePassStatus, String externalDataFilePath) {
        this.validityState = ticketStatus;
        generateActivationAndValidityDate();
        this.ePassDetails = new EPassDetails(ePassStatus, externalDataFilePath, this.validityDate);
    } // File traveler constructor







    //todo gdzies to przeniesc xD
    private void generateActivationAndValidityDate() {
        if (this.validityState.equals(ValidityState.NOT_VALID) ||
                this.validityState.equals(ValidityState.NOT_STARTED)) {

            Date randomDate = MyTicketDataGenerator.date();

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
        } else { // VALID_TODAY
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

    public ValidityState getValidityState() {
        return validityState;
    }

    public EPassDetails.PassStatus getePassDetailsStatus() {
        return ePassDetails.getStatus();
    }


    @Override
    public String toString() {
        EPassDetails epass = this.getePassDetails();
        Traveler traveler = epass.getTraveler();
        ValidityPeriod validityPeriod = epass.getValidityPeriod();

        StringBuilder builder = new StringBuilder();

        return builder.append("{\n" ).append(
                "  \"validityState\": \"" ).append( this.getValidityState() ).append( "\",\n" ).append(
                "  \"activationDate\": \"" ).append( this.getActivationDate() ).append( "\",\n" ).append(
                "  \"validityDate\": \"" ).append( this.getValidityDate() ).append( "\",\n" ).append(
                "  \"ePassDetails\": {\n" ).append(
                "    \"kind\": \"" ).append( epass.getKind() ).append( "\",\n" ).append(
                "    \"type\": \"" ).append( epass.getType() ).append( "\",\n" ).append(
                "    \"traveler\": {\n" ).append(
                "      \"fullNameSecured\": \"" ).append( traveler.getFullNameSecured() ).append( "\",\n" ).append(
                "      \"passportNumberSecured\": \"" ).append( traveler.getPassportNumberSecured() ).append( "\"\n" ).append(
                "    },\n" ).append(
                "    \"status\": \"" ).append( epass.getStatus() ).append( "\",\n" ).append(
                "    \"validityPeriod\": {\n" ).append(
                "      \"startDate\": \"" ).append( validityPeriod.getStartDate() ).append( "\",\n" ).append(
                "      \"endDate\": \"" ).append( validityPeriod.getEndDate() ).append( "\"\n" ).append(
                "    }\n" ).append(
                "  }\n" ).append(
                "}").toString();
    }
    public long getId() {
        return id;
    }
} // class















