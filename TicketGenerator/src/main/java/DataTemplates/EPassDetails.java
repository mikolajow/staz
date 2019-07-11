




package DataTemplates;


import Utils.MyValuesGenerator;

import java.time.LocalDate;
import java.util.Random;

public class EPassDetails {

    private String kind;
    private String type;

    private Traveler traveler;

    private PassStatus status;

    private ValidityPeriod validityPeriod;


    public EPassDetails(PassStatus ePassStatus, LocalDate validityDate, Ticket.ValidityState validityState) {
        this.status = ePassStatus;
        this.traveler = new Traveler();

        this.kind = MyValuesGenerator.kind();
        this.type = MyValuesGenerator.type();

        setValidityPeriod(validityState, validityDate);
    }












    public EPassDetails(PassStatus ePassStatus, String externalDataFilePath,
                        LocalDate validityDate, Ticket.ValidityState validityState) {
        this.status = ePassStatus;
        this.traveler = new Traveler(externalDataFilePath);


        //todo kind i type z pliku + traveler


        setValidityPeriod(validityState, validityDate);
    }













    private void setValidityPeriod(Ticket.ValidityState validityState, LocalDate validityDate) {
        if(validityState.equals(Ticket.ValidityState.NOT_STARTED) ||
                validityState.equals(Ticket.ValidityState.NOT_VALID))
            notStartedOrNotValidTicketState(validityState);
        else
            validPeriod(validityDate);
    }


    private void notStartedOrNotValidTicketState(Ticket.ValidityState validityState) {
        Random random = new Random();
        int changeInYears = random.nextInt(4); // 0-3
        int changeInMonths = random.nextInt(6); // 0-5
        int changeInDays = random.nextInt(21); //0-20

        int periodTimeInDays = random.nextInt(86) + 5; // 5-90

        LocalDate currentDate = LocalDate.now();
        LocalDate startPeriodDate;
        LocalDate finishPeriodDate;


        if (validityState.equals(Ticket.ValidityState.NOT_STARTED)) {
            startPeriodDate = currentDate.plusYears(changeInYears).plusMonths(changeInMonths).plusDays(changeInDays);
            finishPeriodDate = startPeriodDate.plusDays(periodTimeInDays);
        } else {
            startPeriodDate = currentDate.minusYears(changeInYears).minusMonths(changeInMonths).minusDays(changeInDays);
            finishPeriodDate = startPeriodDate.minusDays(periodTimeInDays);
        }


        this.validityPeriod = new ValidityPeriod(startPeriodDate, finishPeriodDate);
    }


    private void validPeriod(LocalDate validityDate) {
        Random random = new Random();

        int daysBackInTime = random.nextInt(45) + 3;
        int daysForwardInTime = random.nextInt(45) + 3;

        this.validityPeriod =
                new ValidityPeriod(validityDate.minusDays(daysBackInTime), validityDate.plusDays(daysForwardInTime));
    }











    enum PassStatus {
        BLOCKED,
        EXPIRED,
        NOT_STARTED,
        REFUNDED,
        VALID
    } // enum PassStatus


} // class














