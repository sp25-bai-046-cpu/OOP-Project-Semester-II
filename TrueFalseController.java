package oopfinalproject.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import oopfinalproject.model.*;

public class TrueFalseController {

    @FXML private TextField topicField;
    @FXML private Label questionLabel;
    @FXML private RadioButton trueRadio;
    @FXML private RadioButton falseRadio;
    @FXML private TextArea explainArea;
    @FXML private Button nextBtn;

    private TrueFalseAI quiz;
    private final CoPilot copilot = new CoPilot();

    private int index = 0;
    private int score = 0;
    private boolean answered = false;

    // ---------------- GENERATE ----------------
    @FXML
    private void onGenerate() {
        Session.quizStartTime = System.currentTimeMillis(); //total time for tf and match starts
        if (topicField.getText().trim().isEmpty()) return;

        quiz = new TrueFalseAI(topicField.getText().trim(), 5);
        Session.lastTopic = topicField.getText().trim(); // save topic

        index = 0;
        score = 0;
        answered = false;

        loadQuestion();
    }

    // ---------------- LOAD QUESTION ----------------
    private void loadQuestion() {
        questionLabel.setText(quiz.getQuestion(index));
        explainArea.clear();
        trueRadio.setSelected(false);
        falseRadio.setSelected(false);
        answered = false;
        nextBtn.setText("Check");
    }

    @FXML
    private void selectTrue() {
        trueRadio.setSelected(true);
        falseRadio.setSelected(false);
    }

    @FXML
    private void selectFalse() {
        falseRadio.setSelected(true);
        trueRadio.setSelected(false);
    }


    // ---------------- NEXT ----------------
    @FXML
    private void onNext() {

        // no option selected
        if (!trueRadio.isSelected() && !falseRadio.isSelected()) return;

        // First click check answer
        if (!answered) {

            boolean userAnswer = trueRadio.isSelected();//select hona wala is answer
            boolean correct = quiz.isCorrect(index, userAnswer);

            if (correct) {
                score++;
                explainArea.setText("✔ Correct!");
            } else {
                explainArea.setText(
                        copilot.explainAnswer(
                                quiz.getQuestion(index),
                                quiz.getCorrectAnswerText(index),
                                userAnswer ? "True" : "False"//checking using ternary operators
                        )//backend: question -correct -userans -explaining
                );
            }

            answered = true;// answer process completed after checking
            nextBtn.setText("Next");
            return;
        }

        // second click move to next question
        index++;

        if (index >= quiz.size()) {
            Session.progressMap.put("TF", score);
            goToMatch();
        } else {
            loadQuestion();
        }
    }

    private void goToMatch() {
        try {
            javafx.stage.Stage stage =
                    (javafx.stage.Stage) nextBtn.getScene().getWindow();

            javafx.scene.Parent root =
                    javafx.fxml.FXMLLoader.load(
                            getClass().getResource("/fxml/Match.fxml")
                    );

            //  SAME SCENE, SAME SIZE
            stage.getScene().setRoot(root);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

