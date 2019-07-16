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

        if(!validateInputs())
            showError("Error", "Wrong input combination", "Available options: \n bananana: \n - a \n - b");
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









    //todo
    private boolean validateInputs() {
        return true;
    }









    private void showError(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }

    private Ticket generateDeafultTicket(Ticket.ValidityState ticketStatus, EPassDetails.PassStatus ePassStatus) {
        //System.out.println("generateDeafultTicket");
        return new Ticket(ticketStatus, ePassStatus);
    }


    private Ticket loadDataAndGenerateTicket(Ticket.ValidityState ticketStatus,
                                           EPassDetails.PassStatus ePassStatus, String externalDataPath) {
        //System.out.println("loadDataAndGenerateTicket");
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



















