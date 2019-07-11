


package DataTemplates;


import java.time.LocalDate;
import java.time.LocalDateTime;


public class Ticket {

    // todo save to json file
    private final String fileSavePath;

    private ValidityState validityState;

    private LocalDateTime activationDate;
    private LocalDate validityDate;

    private EPassDetails ePassDetails;


    public Ticket(ValidityState ticketStatus, EPassDetails.PassStatus ePassStatus, String fileSavePath) {
        this.validityState = ticketStatus;
        this.fileSavePath = fileSavePath;

        generateActivationAndValidityDate();

        this.ePassDetails = new EPassDetails(ePassStatus, validityDate, validityState);
    } // random traveler constructor


    public Ticket(ValidityState ticketStatus, EPassDetails.PassStatus ePassStatus,
                  String fileSavePath, String externalDataFilePath) {
        this.validityState = ticketStatus;
        this.fileSavePath = fileSavePath;

        generateActivationAndValidityDate();

        this.ePassDetails = new EPassDetails(ePassStatus, externalDataFilePath, this.validityDate, validityState);
    } // File traveler constructor





    private void generateActivationAndValidityDate() {
        if (this.validityState.equals(ValidityState.NOT_VALID) ||
                this.validityState.equals(ValidityState.NOT_STARTED)) {
            this.validityDate = null;
            this.activationDate = null;
        } else if (this.validityState.equals(ValidityState.VALID_YESTERDAY)) {
            this.activationDate = LocalDateTime.now().minusDays(1);
            this.validityDate = LocalDate.now().minusDays(1);
        } else {
            this.activationDate = LocalDateTime.now();
            this.validityDate = LocalDate.now();
        }
    }




    enum ValidityState {
        VALID_TODAY,
        VALID_YESTERDAY,
        NOT_STARTED,
        NOT_VALID
    } // enum ValidityState


} // class















