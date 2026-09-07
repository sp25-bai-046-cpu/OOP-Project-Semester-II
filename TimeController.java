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
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import oopfinalproject.model.Session;

public class TimeController {

    @FXML
    private Label timeLabel;
    @FXML
    private Label remarksLabel;
    @FXML
    private Button progressBtn;
    @FXML
    private void initialize() {

        int time = Session.getTimeTaken();
        int tf = Session.progressMap.getOrDefault("TF", 0);
        int match = Session.progressMap.getOrDefault("MATCH", 0);

        timeLabel.setText("⏱ Time: " + time + " seconds");

        if (time <= 60) {
            remarksLabel.setText("Excellent speed! 🚀");
        } else if (time <= 120) {
            remarksLabel.setText("Good performance 👍");
        } else {
            remarksLabel.setText("Try to be faster next time 🙂");
        }
    }

    @FXML
    private void goToProgress() {
        try {
            Stage stage = (Stage) progressBtn.getScene().getWindow();
            Parent root = FXMLLoader.load(
                    getClass().getResource("/fxml/progress.fxml")
            );
            Scene scene = stage.getScene();
            scene.setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}