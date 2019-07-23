package Main.CLI.Comands;

import Main.CLI.Validators.DirectoryParameterValidator;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=")
public class FileSave {
    @Parameter(names = {"-sp", "--save_path"},
            required = true,
            description = "Absolute path to directory where you want to save new ticket File",
            validateWith = DirectoryParameterValidator.class)
    private String savePath;

    @Parameter(names = {"-ts", "--ticket_status"},
            required = true,
            description = "Status of ticket you want to create")
    private String ticketStatus;

    @Parameter(names = {"-es", "--epass_status"},
            required = true,
            description = "Status of ePass you want for your ticket")
    private String epassStatus;

    @Parameter(names = {"-tp", "--traveler_path"},
            description = "Absolute path to file with traveler data")
    private String externalDataPath;


    public String getSavePath() {
        return savePath;
    }

    public String getTicketStatus() {
        return ticketStatus;
    }

    public String getEpassStatus() {
        return epassStatus;
    }

    public String getExternalDataPath() {
        return externalDataPath;
    }
} // class
