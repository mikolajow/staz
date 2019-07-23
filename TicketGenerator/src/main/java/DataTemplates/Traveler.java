package DataTemplates;

import Utils.MyTicketDataGenerator;
import javax.persistence.*;

@Entity
public class Traveler {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private transient final static int STAR_NUMBER = 8;

    private transient String fullName;
    @Column(name = "full_name")
    private String fullNameSecured;
    private transient String passportNumber;
    @Column(name = "passport_number")
    private String passportNumberSecured;


    public Traveler() {
        String firstName = MyTicketDataGenerator.firstName();
        String lastName = MyTicketDataGenerator.lastName();

        this.fullName = firstName + " " + lastName;
        setSecuredName(firstName, lastName);

        this.passportNumber = MyTicketDataGenerator.passportNumber();
        setSecuredPassportNumber();
    }


    Traveler(String firstName, String lastName, String passportNumber) {
        this.passportNumber = passportNumber;
        this.fullName = firstName + " " + lastName;
        setSecuredPassportNumber();
        setSecuredName(firstName, lastName);
    } // constructor from file


    private void setSecuredName(String firstName, String lastName) {
        StringBuilder secureNameBuilder = new StringBuilder();

        secureNameBuilder
                .append(firstName.charAt(0))
                .append(". ")
                .append(lastName.charAt(0));

        for (int i = 0; i < STAR_NUMBER; i++)
            secureNameBuilder.append("*");

        secureNameBuilder.append(lastName.charAt(lastName.length() - 1));

        this.fullNameSecured = secureNameBuilder.toString();
    }
    private void setSecuredPassportNumber() {
        StringBuilder securePassportBuilder = new StringBuilder();

        for(int i = 0; i < STAR_NUMBER; i++)
            securePassportBuilder.append("*");

        int passportLength = passportNumber.length();
        securePassportBuilder
                .append(passportNumber.charAt(passportLength -2))
                .append(passportNumber.charAt(passportLength -1));

        this.passportNumberSecured = securePassportBuilder.toString();
    }
    public String getFullNameSecured() {
        return fullNameSecured;
    }
    public String getPassportNumberSecured() {
        return passportNumberSecured;
    }
} // class











