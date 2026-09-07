package oopfinalproject.controller;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import oopfinalproject.model.CoPilot;
import oopfinalproject.model.DisplayManagerForCoPilot;
import oopfinalproject.model.InvalidPromptException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
public class CoPilotController {
    @FXML private BorderPane root;
    @FXML private TextArea forDescription;
    @FXML private TextField forTopicToDescribe;
    @FXML private Button sendBtn;

    private final CoPilot assistant = new CoPilot();
    private final DisplayManagerForCoPilot display = new DisplayManagerForCoPilot();

    private boolean waitingYesNo = false; // answer yes or no
    private boolean active = true; // chatbot active till yes

    @FXML
    public void initialize() {
        forDescription.setEditable(false);
        forDescription.setText(display.copilotHeader() //greetings
                + "\n" + display.greeting() + "\n\n");
    }

    @FXML
    private void askQuestion() { //action event on btn

        if (!active) { //copilot deactivated
            forDescription.appendText("\n❌ Conversation has ended.\n");
            return;
        }

        String userText = forTopicToDescribe.getText().trim();
        if (userText.isEmpty()) return; //if user set nothing then nothing

        forDescription.appendText("👤 You: " + userText + "\n\n");
        forTopicToDescribe.clear();

        //  waiting for yes/no
        if (waitingYesNo) {
            handleYesNo(userText);
            return;
        }

        // Check prompt validity BEFORE asking AI

        //checking if user only enters study questions
        try { // using Custom Exception
            if (!InvalidPromptException.studyRelevant(userText)) {// extends class exception
                throw new InvalidPromptException("⚠ Only study-related questions are allowed!");
            }
        } catch (InvalidPromptException e) { //catch block
            forDescription.appendText("❌ Error: " + e.getMessage() + "\n\n");
            forDescription.appendText("📘 Please ask something related to STUDY.\n\n");
            return; // stop here, don't call AI
        }

        // if valid question ,ask AI
        forDescription.appendText("⏳ Thinking...\n");

        new Thread(() -> {
            String reply = assistant.displayInfo(userText);

            Platform.runLater(() -> {
                forDescription.appendText("🤖 CoPilot:\n" + reply + "\n\n");
                forDescription.appendText(display.furtherAssistance() + "\n");
                waitingYesNo = true;
            });
        }).start();
    }

    private void handleYesNo(String response) {
        response = response.toLowerCase();

        if (response.equals("yes")) {
            waitingYesNo = false;
            forDescription.appendText("\n🙂 Sure! Ask your next question:\n");
        }
        else if (response.equals("no")) {
            active = false;
            waitingYesNo = false; //user choice no so exit
            forDescription.appendText("\n" + display.copilotSeeOff() + "\n");
            navigate(sendBtn,"Menu.fxml");
        }
        else {  // input validation
            forDescription.appendText("⚠ Please reply only 'yes' or 'no'.\n");
        }
    }

// safe navigation
protected void navigate(Button source, String fxml) {
    try {
        Stage stage = (Stage) source.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/" + fxml));
        Scene scene = stage.getScene();
        scene.setRoot(root);
    } catch (Exception e) {
        e.printStackTrace();
    }
}

}