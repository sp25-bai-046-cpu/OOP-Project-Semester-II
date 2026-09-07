package oopfinalproject.model;

// ===================== STUDY MATERIAL =====================
abstract class StudyMaterial {
    public String materialHeader() { return "========== Happy Learning 🧠 =========="; }
    abstract public String materialBody();
}

public class AIStudyMaterial extends StudyMaterial {
    private String topic;
    public AIStudyMaterial(String topic) { this.topic = topic; }

    @Override
    public String materialBody() {
        String prompt = "Provide a concise study material on topic: '" + topic + "'. Use simple language suitable for students.";
        return AIBrain.askAI(prompt);
    }
}

