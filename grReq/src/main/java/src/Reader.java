package src;

import java.io.*;
import java.util.ArrayList;

public class Reader {

    public ArrayList<Ticket> readFile(String path) {
        ArrayList<Ticket> data = new ArrayList<>();

        File file = new File(path);

        try(BufferedReader bfReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bfReader.readLine()) != null) {
                checkAndAddLine(data, line);
            } // while
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    } // readLinies


    private void checkAndAddLine(ArrayList<Ticket> data, String line) {
        Ticket ticket = getTicket(line);
        if (ticket != null)
            data.add(ticket);
    } // checkAndAddLine


    private Ticket getTicket(String line) {

        if(!line.contains("\""))
            return null;

        // +1 at first and third indexes because we do not want to cut "
        int ePassStartingIndex = line.indexOf("\"") + 1;
        int ePassFinishIndex = line.indexOf("\"", ePassStartingIndex + 1);
        int passportStartingIndex = line.indexOf("\"", ePassFinishIndex + 1) + 1;
        int passportFinishIndex = line.indexOf("\"", passportStartingIndex + 1) ;

        if(passportFinishIndex == 0 || ePassFinishIndex == 0 || passportStartingIndex == 0)
            return null;

        String ePass = line.substring(ePassStartingIndex, ePassFinishIndex);
        String fullPassport = line.substring(passportStartingIndex, passportFinishIndex);

        int passportLength = fullPassport.length();
        if(passportLength < 3)
            return null;
        else if (passportLength == 3)
            return new Ticket(ePass, fullPassport);

        String passportEnd = fullPassport.substring(passportLength-3);

        return new Ticket(ePass, passportEnd);
    } // getTicket


} // class




























