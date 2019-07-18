package gui;

import DataTemplates.EPassDetails;
import DataTemplates.Ticket;
import TicketSaver.SaverForTicket;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class MainFx extends Application {

    public void run(String[] args) {
        launch(args);
    }


    private String externalDataPath;
    private String pathToSave;

    private Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/viewDirectory/MainView.fxml"));

        this.stage = primaryStage;

        Scene scene = new Scene(root);

        initializeStageElements(scene);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initializeStageElements(Scene scene) {
        ChoiceBox ticketCheckBox = (ChoiceBox) scene.lookup("#ticket_status_dropbox");
        ticketCheckBox.getItems().add(Ticket.ValidityState.VALID_TODAY);
        ticketCheckBox.getItems().add(Ticket.ValidityState.VALID_YESTERDAY);
        ticketCheckBox.getItems().add(Ticket.ValidityState.NOT_STARTED);
        ticketCheckBox.getItems().add(Ticket.ValidityState.NOT_VALID);
        ticketCheckBox.setValue(Ticket.ValidityState.VALID_TODAY);

        ChoiceBox ePassCheckbox = (ChoiceBox) scene.lookup("#e_pass_status_dropbox");
        ePassCheckbox.getItems().add(EPassDetails.PassStatus.BLOCKED);
        ePassCheckbox.getItems().add(EPassDetails.PassStatus.EXPIRED);
        ePassCheckbox.getItems().add(EPassDetails.PassStatus.NOT_STARTED);
        ePassCheckbox.getItems().add(EPassDetails.PassStatus.REFUNDED);
        ePassCheckbox.getItems().add(EPassDetails.PassStatus.VALID);
        ePassCheckbox.setValue(EPassDetails.PassStatus.VALID);

        TextField savePathText = (TextField) scene.lookup("#save_ticket_path_text");
        savePathText.setEditable(false);

        TextField additionalDataPathText = (TextField) scene.lookup("#traveler_data_path_text");
        additionalDataPathText.setEditable(false);
    }


    @FXML
    public void onChooseSavePathClicked(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();

        this.pathToSave = directoryChooser.showDialog(this.stage).getPath();

        Button button = (Button) event.getSource();
        Scene scene = button.getScene();

        TextField savePathText = (TextField) scene.lookup("#save_ticket_path_text");
        savePathText.setText(this.pathToSave);

        event.consume();
    }


    @FXML
    public void onChooseTravelerDataClicked(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        this.externalDataPath = fileChooser.showOpenDialog(this.stage).getPath();

        Button button = (Button) event.getSource();

        Scene scene = button.getScene();

        TextField additionalDataPathText = (TextField) scene.lookup("#traveler_data_path_text");
        additionalDataPathText.setText(this.externalDataPath);

        event.consume();
    }


    @FXML
    public void createTicket(ActionEvent event) {
        Scene scene =  ((Button) event.getSource()).getScene();

        Ticket.ValidityState ticketStatus =
                (Ticket.ValidityState) ( ((ChoiceBox) scene.lookup("#ticket_status_dropbox")).getValue() );
        EPassDetails.PassStatus ePassStatus =
                (EPassDetails.PassStatus) ( ((ChoiceBox) scene.lookup("#e_pass_status_dropbox")).getValue() );

        Ticket newTicket = null;

        if(!validateInputs(ticketStatus, ePassStatus))
            showError("Error", "Wrong input combination",
                    "Available options: " +
                            "\n" +
                            "\n VALID_TODAY: " +
                            "\n - VALID " +
                            "\n - REFUNDED " +
                            "\n - BLOCKED " +
                            "\n" +
                            "\n VALID_YESTERDAY: " +
                            "\n - BLOCKED " +
                            "\n - EXPIRED " +
                            "\n - REFUNDED " +
                            "\n" +
                            "\n NOT_STARTED: " +
                            "\n - REFUNDED " +
                            "\n - NOT_STATRED " +
                            "\n - BLOCKED " +
                            "\n" +
                            "\n NOT_VALID: " +
                            "\n - BLOCKED " +
                            "\n - EXPIRED " +
                            "\n - REFUNDED");
        else if(this.pathToSave == null)
            showError("Error", "No path to save", "Choose patch where you want to save new json file");
        else if(externalDataPath == null)
            newTicket = generateDeafultTicket(ticketStatus, ePassStatus);
        else
            newTicket = loadDataAndGenerateTicket(ticketStatus, ePassStatus, externalDataPath);


        if(newTicket != null)
            saveTicket(newTicket);

        event.consume();
    }


    private boolean validateInputs(Ticket.ValidityState ticketStatus,
                                   EPassDetails.PassStatus ePassStatus) {
        switch(ticketStatus) {
            case NOT_STARTED:
                if(ePassStatus.equals(EPassDetails.PassStatus.VALID) ||
                        ePassStatus.equals(EPassDetails.PassStatus.EXPIRED))
                    return false;
                else
                    return true;
            case VALID_TODAY:
                if(ePassStatus.equals(EPassDetails.PassStatus.NOT_STARTED) ||
                        ePassStatus.equals(EPassDetails.PassStatus.EXPIRED))
                    return false;
                else
                    return true;
                // VALID_YESTERDAY + NOT_VALID
                default:
                    if (ePassStatus.equals(EPassDetails.PassStatus.VALID) ||
                            ePassStatus.equals(EPassDetails.PassStatus.NOT_STARTED))
                        return false;
                    else
                        return true;
        } // switch
    }

    private void showError(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }

    private Ticket generateDeafultTicket(Ticket.ValidityState ticketStatus, EPassDetails.PassStatus ePassStatus) {
        return new Ticket(ticketStatus, ePassStatus);
    }


    private Ticket loadDataAndGenerateTicket(Ticket.ValidityState ticketStatus,
                                           EPassDetails.PassStatus ePassStatus, String externalDataPath) {
        return new Ticket(ticketStatus, ePassStatus, externalDataPath);
    }

    private void saveTicket(Ticket newTicket) {
        StringBuilder builder = new StringBuilder();
        builder
                .append(this.pathToSave)
                .append("\\")
                .append(newTicket.getValidityState())
                .append("-")
                .append(newTicket.getePassDetailsStatus())
                .append("_")
                .append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-mm-dd'T'HH-mm-ss-SSS")))
                .append(".json");
        try {
            (new SaverForTicket()).saveTicketToJsonFile(newTicket, builder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


} // class



















