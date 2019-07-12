package Utils;

import com.github.javafaker.Faker;
import java.util.*;
import java.util.concurrent.TimeUnit;

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

    private final static int passportDigitsNumber = 8;


    public static String passportNumber() {
        int[] ints = new Random().ints(passportDigitsNumber, 48, 91).toArray();
        String passportNumber = new String(ints, 0, passportDigitsNumber);
        return passportNumber;
    }


    public static String firstName() {
        return faker.name().firstName();
    }

    public static String lastName() {
        return faker.name().lastName();
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


    public static Date date() {
        double random = Math.random();

        if (random > 0.5)
            return faker.date().future(77760, TimeUnit.HOURS); // około 9 lat
        else
            return faker.date().past(77760, TimeUnit.HOURS); // około 9 lat
    }


    private enum AgeGroups {
        Child,
        Youth,
        Adult,
        Senior
    }

} // class









