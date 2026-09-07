package oopfinalproject.model;

import java.util.*;
import com.google.gson.Gson;
import oopfinalproject.model.CoPilot;

public class MatchColumnAI {
    private List<String> leftWords;         // keywords
    private List<String> rightMeanings;     // definitions (shuffled)
    private Map<String, String> correctMap; // mapping word → correct meaning
    private CoPilot ai;
    private Gson gson = new Gson();//retruning java objects after parsing

    public MatchColumnAI(CoPilot ai) {
        this.ai = ai;
        leftWords = new ArrayList<>();
        rightMeanings = new ArrayList<>();
        correctMap = new HashMap<>();
    }

    // Generate whole quiz from topic
    public void generate(String topic) {
        String json = ai.ask(
                "Generate 5 key terms and simple definitions on topic '" + topic +
                        "'. Return ONLY JSON: {\"words\":[\"...\"], \"meanings\":[\"...\"]}"
        );

        WordSet set = gson.fromJson(json, WordSet.class);

        leftWords = set.words;
        rightMeanings = new ArrayList<>(set.meanings);

        // build correct mapping
        for (int i = 0; i < leftWords.size(); i++) {
            correctMap.put(leftWords.get(i), set.meanings.get(i));
        }

        // shuffle meanings for quiz randomness
        Collections.shuffle(rightMeanings);
    }

    public List<String> getLeftWords() { return leftWords; }

    public List<String> getRightMeanings() { return rightMeanings; }

    // checking user match
    public boolean check(int leftIndex, int rightIndex) {
        String word = leftWords.get(leftIndex);
        String chosenMeaning = rightMeanings.get(rightIndex);

        return correctMap.get(word).equals(chosenMeaning);
    }

    public String getExplanation(String word) {
        return ai.ask("Explain meaning of: " + word);
    }

    private class WordSet {
        List<String> words;
        List<String> meanings;
    }
}
