package oopfinalproject.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import oopfinalproject.model.AIStudyMaterial;

public class StudyMaterialController {

    @FXML private TextField toenterStudyTopic;
    @FXML private TextArea forTopicInfo;
    @FXML private Button startQuizBtn;

    @FXML
    private void generateMaterial() {

        String topic = toenterStudyTopic.getText().trim();
        if (topic.isEmpty()) return; // if not searched

        forTopicInfo.setText("Generating..."); //searched

        new Thread(() -> {
            AIStudyMaterial m = new AIStudyMaterial(topic);
            String text = m.materialHeader() + "\n\n" + m.materialBody();
            javafx.application.Platform.runLater(() -> forTopicInfo.setText(text));
        }).start();
    }

    @FXML
    private void goToQuiz() {
        load("Quiz.fxml");
    }

    private void load(String fxml) {
            try {
                Stage stage = (Stage) startQuizBtn.getScene().getWindow();

                Parent root = FXMLLoader.load(
                        getClass().getResource("/fxml/" + fxml)
                );

                //keeping scene size
                Scene scene = stage.getScene();
                scene.setRoot(root);

            } catch (Exception e) {
                e.printStackTrace();
            }

    }


}