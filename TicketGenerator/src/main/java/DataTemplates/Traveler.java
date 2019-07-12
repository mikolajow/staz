


package DataTemplates;

import Utils.MyValuesGenerator;
import com.google.gson.JsonObject;


public class Traveler {

    private final static int STAR_NUMBER = 8;

    private transient String fullName;
    private String fullNameSecured;
    private transient String passportNumber;
    private String passportNumberSecured;


    public Traveler() {
        String firstName = MyValuesGenerator.firstName();
        String lastName = MyValuesGenerator.lastName();

        this.fullName = firstName + " " + lastName;
        setSecuredName(firstName, lastName);

        this.passportNumber = MyValuesGenerator.passportNumber();
        setSecuredPassportNumber();
    }


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


    public Traveler(JsonObject travelerJson) {

        String firstName = travelerJson.get("travelerName").getAsString();
        String lastName = travelerJson.get("travelerSurname").getAsString();

        this.passportNumber = travelerJson.get("passportNumber").getAsString();
        this.fullName = firstName + " " + lastName;

        setSecuredPassportNumber();
        setSecuredName(firstName, lastName);
    } // constructor from file


} // class











