package oopfinalproject.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import oopfinalproject.model.*;

import java.util.ArrayList;

public class QuizController {

    @FXML
    private TextField topicField;
    @FXML
    private Label questionLabel;
    @FXML
    private Button optionA;
    @FXML
    private Button optionB;
    @FXML
    private Button optionC;
    @FXML
    private Button optionD;
    @FXML
    private TextArea explanationArea;
    @FXML
    private Button nextBtn;

    private ArrayList<MCQ> questions;
    private MCQbased engine;
    private int index = 0;
    private int score = 0;
    private boolean answered = false;

    private final CoPilot copilot = new CoPilot();

    // -------------------- START QUIZ --------------------
    @FXML
    private void generateQuiz() {

        Session.quizStartTime = System.currentTimeMillis();

        if (topicField.getText().trim().isEmpty()) return;//no topic entered

        Session.lastTopic = topicField.getText().trim();//save topic in session

        questions = MCQGenerator.generateMCQs(Session.lastTopic);
        engine = new MCQbased(questions, null, copilot);

        index = 0;
        score = 0;

        loadQuestion();
    }

    // -------------------- LOAD QUESTION --------------------
    private void loadQuestion() {

        MCQ q = questions.get(index);

        questionLabel.setText(q.question);
        optionA.setText(q.options[0]);
        optionB.setText(q.options[1]);
        optionC.setText(q.options[2]);
        optionD.setText(q.options[3]);

        explanationArea.clear();
        answered = false;
    }

    // options
    @FXML
    private void selectA() {
        answer(0);
    }

    @FXML
    private void selectB() {
        answer(1);
    }

    @FXML
    private void selectC() {
        answer(2);
    }

    @FXML
    private void selectD() {
        answer(3);
    }

    // checking answers
    private void answer(int selectedIndex) {

        if (answered) return;
        answered = true;

        MCQ q = questions.get(index);

        if (engine.checkAnswer(q, selectedIndex)) {
            score++;
            explanationArea.setText("✔ Correct!");
        } else {
            // AI EXPLANATION
            String explanation = copilot.explainAnswer(
                    q.question,
                    q.options[q.correctIndex],
                    q.options[selectedIndex]
            );
            explanationArea.setText(explanation);
        }
    }

    // next button
    @FXML
    private void nextButtonClicked() {

        index++;

        if (index >= questions.size()) { // when questions finished
            Session.progressMap.put("MCQ", score);
            Session.quizEndTime = System.currentTimeMillis();

            goTo("Time.fxml");
        } else {
            loadQuestion();
        }
    }

    // scene switch
    private void goTo(String fxml) {
        try {
            Stage stage = (Stage) nextBtn.getScene().getWindow();

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
