package oopfinalproject.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import oopfinalproject.model.*;

import java.util.List;

public class MatchController {

    @FXML private Label left1, left2, left3, left4, left5;
    @FXML private Label right1, right2, right3, right4, right5;
    @FXML private TextArea detailArea;

    private final CoPilot copilot = new CoPilot();
    private MatchColumnAI quiz;

    private Label selectedLeft = null;
    private int score = 0;
    private int attempts = 0;

    @FXML
    private void initialize() {

        quiz = new MatchColumnAI(copilot);
        quiz.generate(Session.lastTopic); // on the same topic as we did tf

        List<String> left = quiz.getLeftWords();
        List<String> right = quiz.getRightMeanings();
 // placing the data from list in the column given by AI
        left1.setText(left.get(0));
        left2.setText(left.get(1));
        left3.setText(left.get(2));
        left4.setText(left.get(3));
        left5.setText(left.get(4));
//same here
        right1.setText(right.get(0));
        right2.setText(right.get(1));
        right3.setText(right.get(2));
        right4.setText(right.get(3));
        right5.setText(right.get(4));
    }

    // left click
    @FXML
    private void onLeftClicked(javafx.scene.input.MouseEvent e) {
        selectedLeft = (Label) e.getSource();
        detailArea.setText("Selected: " + selectedLeft.getText());
    }

    // right click
    @FXML
    private void onRightClicked(javafx.scene.input.MouseEvent e) {
   //ensures full action
        //user can forget or miss if used press ,better to use click safe
        if (selectedLeft == null) return;

        Label right = (Label) e.getSource();

        int lIndex = getLeftIndex(selectedLeft);
        int rIndex = getRightIndex(right);

        boolean correct = quiz.check(lIndex, rIndex);
        attempts++; // one question is attempted

        if (correct) {
            score++;

            // green
            selectedLeft.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
            right.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");

            detailArea.setText("✔ Correct Match!");
        } else {

            // red
            selectedLeft.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            right.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");

            detailArea.setText(
                    copilot.explainAnswer(
                            selectedLeft.getText(),
                            quiz.getRightMeanings().get(lIndex),
                            right.getText()
                    )
            );
        }

        selectedLeft = null;

        // auto finish
        if (attempts == 5) {
            finish();
        }
    }

    // move to next
    private void finish() {
        Session.quizEndTime = System.currentTimeMillis();
        Session.progressMap.put("MATCH", score);
        go("Time.fxml");
    }

    // helperss
    private int getLeftIndex(Label l) {
        if (l == left1) return 0;
        if (l == left2) return 1;
        if (l == left3) return 2;
        if (l == left4) return 3;
        return 4;
    }

    private int getRightIndex(Label r) {
        if (r == right1) return 0;
        if (r == right2) return 1;
        if (r == right3) return 2;
        if (r == right4) return 3;
        return 4;
    }

    private void go(String fxml) {
        try {
            Stage stage = (Stage) left1.getScene().getWindow();
             Parent root=FXMLLoader.load(getClass().getResource("/fxml/" + fxml));
            Scene scene = stage.getScene();
            scene.setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
