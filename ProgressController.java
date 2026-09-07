package oopfinalproject.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import oopfinalproject.model.Session;

public class ProgressController {

    @FXML private AnchorPane rootPane;

    @FXML private Label usernameLabel;
    @FXML private Label idLabel;
    @FXML private Label topicLabel;
    @FXML private Label scoreLabel;

    @FXML
    public void initialize() {

        // ---------- SHOW DATA ----------
        if (Session.currentUser != null) {
            usernameLabel.setText(
                    "Username: " + Session.currentUser.getName()
            );
            idLabel.setText(
                    "User ID: " + Session.currentUser.getId()
            );
        }

        topicLabel.setText("Topic: " + Session.lastTopic); // topic searched

        int totalScore = 0;
        for (int s : Session.progressMap.values()) {  //useful in tf n match
            totalScore += s;
        }

        scoreLabel.setText("Score: " + totalScore);

        // key handling
        //root to be attached to the scene
        rootPane.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {//jis scene pr jana ho that should be present

                // MOST IMPORTANT ,keep root focuses else issue
                rootPane.requestFocus();

                newScene.setOnKeyPressed(e -> {
// lamda expression // short code
                    if (e.getCode() == KeyCode.B) {
                        go("Menu.fxml");    // Back to menu
                    }
                });
            }
        });
    }

    private void go(String fxml) {
            try {
                Stage stage = (Stage) rootPane.getScene().getWindow();

                Parent root = FXMLLoader.load(
                        getClass().getResource("/fxml/" + fxml)
                );

                // KEEP SAME SIZE
                Scene scene = stage.getScene();
                scene.setRoot(root);

            } catch (Exception e) {
                e.printStackTrace();
            }

    }


}