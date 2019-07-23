package Utils;

import java.io.*;

public class MyTravelerDataReader {
    public static String[] readTravelerData(String path) {
        String[] result = new String[5];
        try(BufferedReader reader = new BufferedReader(new FileReader(new File(path)))) {
            String line;
            reader.readLine();
            for(int i = 0; i < 5; i++) {
                line = reader.readLine();
                String[] dataTable = line.split("\"");
                result[i] = dataTable[3];
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
} // class

























