package Main.CLI.Comands;

import Main.CLI.Validators.DirectoryParameterValidator;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=")
public class FromDbToFile {
    @Parameter(names = {"-sp", "--save_path"},
            required = true,
            description = "Absolute path to directory where you want to save new ticket File",
            validateWith = DirectoryParameterValidator.class)
    private String savePath;

    @Parameter(names = {"-i", "--index"},
            required = true,
            description = "Ticket index from database that you want to save to file")
    private long index;


    public String getSavePath() {
        return savePath;
    }

    public long getIndex() {
        return index;
    }
} // class
