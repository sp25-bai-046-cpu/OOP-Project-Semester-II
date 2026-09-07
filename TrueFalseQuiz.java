package oopfinalproject.model;

import java.util.ArrayList;

public class TrueFalseQuiz extends Quiz {

    private ArrayList<String> questions;
    private Boolean[] answers;
    private Assisstant copilot;
    private Timer timerTF;

    public TrueFalseQuiz(ArrayList<String> questions,
                         Boolean[] answers,
                         Timer timerTF,
                         Assisstant copilot) {

        super("True / False Quiz", timerTF);
        this.questions = questions;
        this.answers = answers;
        this.copilot = copilot;
        this.timerTF = timerTF;
    }

    // ---------- TIMER ----------
    public void startTimer() {
        timerTF.start();
    }

    public String stopTimer() {
        timerTF.stop();
        return "⏱ Total Time: " + timerTF.getDuration() + " seconds";
    }

    // ---------- BACKEND CHECK ----------
    public boolean checkAnswer(int questionIndex, boolean userAnswer) {
        return answers[questionIndex] == userAnswer;
    }

    // AI explanation for wrong answer
    public String getExplanation(int questionIndex) {
        return copilot.ask(
                "Explain the correct True/False answer for: " +
                        questions.get(questionIndex)
        );
    }

    // ---------- For DriverTest ----------
    public ArrayList<String> getQuestions() {
        return questions;
    }

    public Boolean[] getAnswers() {
        return answers;
    }

    @Override
    public String displayRemarks(int score, int total) {
        double percent = (score * 100.0) / total;

        if (percent <= 40)
            return "💡 Remarks: Improvement is required.\n==========================";

        else if (percent <= 70)
            return "💡 Remarks: Average Performance.\n==========================";

        return "💡 Remarks: Excellent Performance.\n==========================";
    }
}
