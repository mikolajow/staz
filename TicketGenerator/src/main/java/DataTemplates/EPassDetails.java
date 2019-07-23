package DataTemplates;


import Utils.MyTicketDataGenerator;
import Utils.MyTravelerDataReader;

import javax.persistence.*;
import java.io.*;
import java.time.LocalDate;

import static Utils.MyTicketDataGenerator.generateValidPeriod;

@Entity
public class EPassDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String kind;
    private String type;

    @OneToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "traveler_id")
    private Traveler traveler;

    @Enumerated(EnumType.STRING)
    @Column(name = "e_pass_status")
    private PassStatus status;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "startDate", column = @Column(name = "start_date")),
            @AttributeOverride(name = "endDate", column = @Column(name = "end_date"))
    })
    private ValidityPeriod validityPeriod;


    public EPassDetails() {}

    EPassDetails(PassStatus ePassStatus, LocalDate validityDate) {
        this.status = ePassStatus;
        this.traveler = new Traveler();
        this.kind = MyTicketDataGenerator.kind();
        this.type = MyTicketDataGenerator.type();

        this.validityPeriod = generateValidPeriod(validityDate);
    }


    EPassDetails(PassStatus ePassStatus, String externalDataFilePath,
                 LocalDate validityDate) {
        this.status = ePassStatus;
        String[] data = MyTravelerDataReader.readTravelerData(externalDataFilePath);
        this.kind = data[3];
        this.type = data[4];
        this.traveler = new Traveler(data[0], data[1], data[2]);
        this.validityPeriod = generateValidPeriod(validityDate);
    }

    public enum PassStatus {
        BLOCKED,
        EXPIRED,
        NOT_STARTED,
        REFUNDED,
        VALID
    } // enum PassStatus

    public long getId() {
        return id;
    }

    public String getKind() {
        return kind;
    }

    public String getType() {
        return type;
    }

    public Traveler getTraveler() {
        return traveler;
    }

    public PassStatus getStatus() {
        return status;
    }

    public ValidityPeriod getValidityPeriod() {
        return validityPeriod;
    }
} // class














