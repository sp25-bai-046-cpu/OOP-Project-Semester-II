package oopfinalproject.model;

// ===================== ASSISTANT / COPILOT =====================
public abstract class Assisstant {
    abstract public String explainAnswer(String question, String correctAnswer, String userAnswer);
    abstract public String displayInfo(String topic);
    public String ask(String prompt) {
        return AIBrain.askAI(prompt);
    }
}