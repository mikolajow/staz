


package DataTemplates;

import Utils.MyValuesGenerator;



public class Traveler {


    private String fullName;
    private String passportNumber;


    public Traveler() {
        //todo pierwsza litera imienia. pierwsza nazwiska******ostatnia nazwiska
        this.fullName = MyValuesGenerator.name();
        //todo  *******ostatnie dwie cyfry
        this.passportNumber = MyValuesGenerator.passportNumber();
    }





    //todo konstruktor z pliku
    public Traveler(String filePath) {
    }





} // class











