
package DataTemplates;


import Utils.MyValuesGenerator;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.io.*;
import java.time.LocalDate;
import java.util.Random;

@Entity
public class EPassDetails {

    @Expose(serialize = false)
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




    public EPassDetails(PassStatus ePassStatus, LocalDate validityDate, Ticket.ValidityState validityState) {
        this.status = ePassStatus;
        this.traveler = new Traveler();

        this.kind = MyValuesGenerator.kind();
        this.type = MyValuesGenerator.type();

        generateValidPeriod(validityDate);
    }



    public EPassDetails(PassStatus ePassStatus, String externalDataFilePath,
                        LocalDate validityDate, Ticket.ValidityState validityState) {
        this.status = ePassStatus;

        JsonObject travelerJson;

        try (BufferedReader reader = new BufferedReader(new FileReader(new File(externalDataFilePath)))) {

            Gson gson = new Gson();
            travelerJson = gson.fromJson(reader, JsonObject.class);

            this.kind = travelerJson.get("passKind").getAsString();
            this.type = travelerJson.get("passType").getAsString();

            this.traveler = new Traveler(travelerJson);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        generateValidPeriod(validityDate);
    }

    public EPassDetails() {}


    private void generateValidPeriod(LocalDate validityDate) {
        Random random = new Random();

        int daysBackInTime = random.nextInt(45) + 3;
        int daysForwardInTime = random.nextInt(45) + 3;

        this.validityPeriod =
                new ValidityPeriod(validityDate.minusDays(daysBackInTime), validityDate.plusDays(daysForwardInTime));
    }


    public enum PassStatus {
        BLOCKED,
        EXPIRED,
        NOT_STARTED,
        REFUNDED,
        VALID
    } // enum PassStatus


    public PassStatus getEpassStatus() {
        return this.status;
    }

} // class














