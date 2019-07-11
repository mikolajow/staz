package Utils;

import com.github.javafaker.Faker;

import java.util.*;

public class MyValuesGenerator {

    private final static Faker faker = new Faker(new Locale("pl"));
    private final static ArrayList<String> ePassKinds = new ArrayList<String>() {{
        add("Global Mobile Pass");
        add("Austria Mobile Pass");
        add("Benelux Mobile Pass");
        add("Bulgaria Mobile Pass");
        add("Croatia Mobile Pass");
        add("Czech Republic Mobile Pass");
        add("Denmark Mobile Pass");
        add("Finland Mobile Pass");
        add("France Mobile Pass");
        add("Great-Britain Mobile Pass");
        add("Greek Islands Mobile Pass");
        add("Greece Mobile Pass");
        add("Hungary Mobile Pass");
        add("Ireland Mobile Pass");
        add("Italy Mobile Pass");
        add("Lithuania Mobile Pass");
        add("Macedonia Mobile Pass");
        add("Norway Mobile Pass");
        add("Poland Mobile Pass");
        add("Portugal Mobile Pass");
        add("Romania Mobile Pass");
        add("Scandinavia Mobile Pass");
        add("Serbia Mobile Pass");
        add("Slovakia Mobile Pass");
        add("Slovenia Mobile Pass");
        add("Spain Mobile Pass");
        add("Sweden Mobile Pass");
        add("Turkey Mobile Pass");
    }};


    public static String passportNumber() {
        int[] ints = new Random().ints(8, 48, 91).toArray();
        return new String(ints, 0, 8);
    }


    public static String name() {
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        return firstName + " " + lastName;
    }


    public static String type() {
        Random random = new Random();
        int ageGroup = random.nextInt(4);
        int ticketClass = random.nextInt(2);

        StringBuilder stringBuilder = new StringBuilder();

        if (ticketClass == 0)
            stringBuilder.append("1st class ");
        else
            stringBuilder.append("2nd class ");

        switch (ageGroup) {
            case 0: {
                stringBuilder.append(AgeGroups.Child);
                break;
            }
            case 1: {
                stringBuilder.append(AgeGroups.Youth);
                break;
            }
            case 2: {
                stringBuilder.append(AgeGroups.Adult);
                break;
            }
            case 3: {
                stringBuilder.append(AgeGroups.Senior);
                break;
            }
        }// switch
        return stringBuilder.toString();
    }


    public static String kind() {
        Random random = new Random();
        int index = random.nextInt(ePassKinds.size());
        return ePassKinds.get(index);
    }







    public static Date dateBetween(Date startDate, Date finishDate) {
        return faker.date().between(startDate, finishDate);
    }







    private enum AgeGroups {
        Child,
        Youth,
        Adult,
        Senior
    }

} // class









