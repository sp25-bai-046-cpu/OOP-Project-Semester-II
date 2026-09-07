package oopfinalproject.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TrueFalseAI {

    public static class TFItem {
        public String question;
        public boolean answer; //matches json question and its correct answer
    }

    private final List<TFItem> items = new ArrayList<>();//final for safe design

    public TrueFalseAI(String topic, int count) {
        generateFromAI(topic, count);
    }

    private void generateFromAI(String topic, int n) {

        String prompt =
                "Generate EXACTLY " + n + " True/False statements about \"" + topic + "\".\n" +
                        "Return ONLY valid JSON array with fields question and answer.\n" +
                        "Example:\n" +
                        "[{\"question\":\"...\",\"answer\":true}]";

        String response = AIBrain.askAI(prompt);

        try {
            Gson gson = new Gson();
            Type type = TypeToken.getParameterized(List.class, TFItem.class).getType();
            List<TFItem> parsed = gson.fromJson(response, type);

            if (parsed == null || parsed.size() < n) //AI returns something n correct number of questions
                throw new Exception("Invalid AI response");

            items.clear();
            items.addAll(parsed);//storing data safely

        } catch (Exception e) {
            // fallback (NO CRASH)
            items.clear();
            for (int i = 0; i < n; i++) {
                TFItem it = new TFItem();
                it.question = "Default True/False question about " + topic;
                it.answer = (i % 2 == 0);
                items.add(it);
            }
        }
    }

    // ---------------- HELPERS ----------------

    public int size() {
        return items.size();
    }

    public String getQuestion(int idx) {
        return items.get(idx).question;
    }

    public boolean isCorrect(int idx, boolean userAnswer) {
        return items.get(idx).answer == userAnswer;
    }

    public String getCorrectAnswerText(int idx) {
        return items.get(idx).answer ? "True" : "False";
    }
}
