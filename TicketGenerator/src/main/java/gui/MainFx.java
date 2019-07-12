package gui;

import DataTemplates.EPassDetails;
import DataTemplates.Ticket;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;


public class MainFx extends Application {

    public void run(String[] args) {
        launch(args);
    }

    private Stage stage;

    private ChoiceBox ticketCheckBox;
    private ChoiceBox ePassCheckbox;

    private TextField savePathText;
    private TextField additionalDataPathText;

    private Button browseSavePathButton;
    private Button browseAdditionalDataButton;

    private DirectoryChooser directoryChooser;

    private File externalData;
    private String pathToSave;


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/viewDirectory/MainView.fxml"));

        this.stage = primaryStage;
        this.directoryChooser = new DirectoryChooser();

        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.show();

        initializeStageElements(scene);

    }



    private void initializeStageElements(Scene scene) {

        this.ticketCheckBox = (ChoiceBox) scene.lookup("#ticket_status_dropbox");
        this.ticketCheckBox.getItems().add(Ticket.ValidityState.VALID_TODAY);
        this.ticketCheckBox.getItems().add(Ticket.ValidityState.VALID_YESTERDAY);
        this.ticketCheckBox.getItems().add(Ticket.ValidityState.NOT_STARTED);
        this.ticketCheckBox.getItems().add(Ticket.ValidityState.NOT_VALID);
        this.ticketCheckBox.setValue(Ticket.ValidityState.VALID_TODAY);

        this.ePassCheckbox = (ChoiceBox) scene.lookup("#e_pass_status_dropbox");
        this.ePassCheckbox.getItems().add(EPassDetails.PassStatus.BLOCKED);
        this.ePassCheckbox.getItems().add(EPassDetails.PassStatus.EXPIRED);
        this.ePassCheckbox.getItems().add(EPassDetails.PassStatus.NOT_STARTED);
        this.ePassCheckbox.getItems().add(EPassDetails.PassStatus.REFUNDED);
        this.ePassCheckbox.getItems().add(EPassDetails.PassStatus.VALID);
        this.ePassCheckbox.setValue(EPassDetails.PassStatus.VALID);

        this.savePathText = (TextField) scene.lookup("#save_ticket_path_text");
        this.additionalDataPathText = (TextField) scene.lookup("#traveler_data_path_text");

        this.browseSavePathButton = (Button) scene.lookup("#choose_save_path_button");
        this.browseAdditionalDataButton = (Button) scene.lookup("#choose_traveler_data_button");
    }


    @FXML
    public void savePathButtonClicked(ActionEvent event) {
        this.externalData = directoryChooser.showDialog(this.stage);
    }

} // class
