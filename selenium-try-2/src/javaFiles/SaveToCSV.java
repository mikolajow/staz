package javaFiles;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


public class SaveToCSV {

    private final String FILE_NAME;

    public SaveToCSV(String fileName){
        FILE_NAME = fileName;
    }


    public <T> void saveData(List<T> dataToSave) {

        try(BufferedWriter bufferedWriter =
                    new BufferedWriter(new FileWriter(FILE_NAME))) {

            StringBuilder line = new StringBuilder();
            for (T element : dataToSave) {
                line.append(element);
                line.append("\n");
            }
            bufferedWriter.write(line.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    } // saveData

} // class
