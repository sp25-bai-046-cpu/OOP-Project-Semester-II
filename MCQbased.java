package oopfinalproject.model;

import java.util.ArrayList;

public class MCQbased extends Quiz {

    private ArrayList<MCQ> mcqList;
    private Assisstant chatbot;

    public MCQbased(ArrayList<MCQ> mcqs, Timer timer, Assisstant chatbot) {
        super("AI Quiz Set", timer);
        this.mcqList = mcqs;
        this.chatbot = chatbot;
    }

    public ArrayList<MCQ> getQuestions() {
        return mcqList;
    }

    // Check using index
    public boolean checkAnswer(MCQ q, int userIndex) {
        if (userIndex < 0 || userIndex > 3) return false;
        return userIndex == q.correctIndex;
    }

    // Ask AI for explanation
    public String getExplanation(MCQ q, int userIndex) {
        if (userIndex < 0 || userIndex > 3)
            return "No answer selected.";

        String userAnswerText = q.options[userIndex];
        String correctAnswerText = q.getCorrectAnswerText();

        return chatbot.explainAnswer(q.question, correctAnswerText, userAnswerText);
    }

    @Override
    public String displayRemarks(int score, int total) {
        double percent = (score * 100.0) / total;

        if (percent <= 40)
            return "💡 Remarks: Improvement is required.\n========================== ";
        else if (percent <= 70)
            return "💡 Remarks: Average Performance.\n========================== ";
        else
            return "💡 Remarks: Excellent Performance.\n========================== ";
    }
}
