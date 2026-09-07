package oopfinalproject.model;

import java.util.ArrayList;

public class MCQGenerator {

    public static ArrayList<MCQ> generateMCQs(String topic) {

        String prompt =
                "Generate EXACTLY 5 multiple choice questions on the topic: " + topic + ".\n" +
                        "Use THIS EXACT FORMAT:\n\n" +
                        "Q1: <question>\n" +
                        "A) <option1>\n" +
                        "B) <option2>\n" +
                        "C) <option3>\n" +
                        "D) <option4>\n" +
                        "Answer: <A/B/C/D>\n\n" +
                        "(Repeat for Q2 to Q5)\n\n" +
                        "Do NOT add explanations, notes, or any extra text.";

        String output = AIBrain.askAI(prompt);

        ArrayList<MCQ> list = parse(output);

        if (list.size() < 5) {
            System.out.println("⚠ AI returned fewer than 5 questions. Using fallback.");
            return getFallback(topic);
        }

        return list;
    }
//all manual parsing, splitting data to question recognizing options,
// storing everything in structured form in arraylist then loop for using them in mcq
    //No gson so manual
    private static ArrayList<MCQ> parse(String text) {
        ArrayList<MCQ> list = new ArrayList<>();

        // Correct way: keep Q1, Q2… intact
        String[] parts = text.split("(?=Q\\d+:)");
    //splits questions
        for (String p : parts) {
            p = p.trim(); //loop for parsing
            if (!p.startsWith("Q")) continue;
      //skips invalid blocks
            String[] lines = p.split("\n");
            if (lines.length < 6) continue;

            // First line after Q is the question
            String question = lines[0].substring(lines[0].indexOf(":") + 1).trim();
         //keeps Q only removes number
            String[] options = new String[4];

            for (String l : lines) {
                l = l.trim();

                if (l.startsWith("A)") || l.startsWith("A.")) options[0] = l.substring(2).trim();
                if (l.startsWith("B)") || l.startsWith("B.")) options[1] = l.substring(2).trim();
                if (l.startsWith("C)") || l.startsWith("C.")) options[2] = l.substring(2).trim();
                if (l.startsWith("D)") || l.startsWith("D.")) options[3] = l.substring(2).trim();
            }//reads every line of options supports A) or A.

            // Extract correct answer
            int correct = -1;
            for (String l : lines) {
                if (l.toLowerCase().startsWith("answer")) {
                    String ans = l.split(":")[1].trim().toUpperCase();
                    switch (ans) {
                        case "A": correct = 0; break;
                        case "B": correct = 1; break;
                        case "C": correct = 2; break;
                        case "D": correct = 3; break;
                    }
                }  //reads Answer: A) or A.
            }

            if (correct != -1) {
                list.add(new MCQ(question, options, correct));
            } // now the array list converted raw data to the form of question options n answers
        }

        return list;
    }

    private static ArrayList<MCQ> getFallback(String topic) {
        ArrayList<MCQ> list = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            list.add(new MCQ(
                    "Default question " + i + " for topic: " + topic,
                    new String[]{"Option 1", "Option 2", "Option 3", "Option 4"},
                    0
            ));
        }
        return list;
    }
}
