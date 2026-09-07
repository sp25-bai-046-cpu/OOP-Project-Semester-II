package oopfinalproject.model;
import oopfinalproject.model.*;
import java.util.ArrayList;
import java.util.Scanner;

// ===================== oopfinalproject.model.MCQ =====================
public class MCQ {
    public String question;
   public String[] options;
   public  int correctIndex; // 0-based index

    public MCQ(String question, String[] options, int correctIndex) {
        this.question = question;
        this.options = options;
        this.correctIndex = correctIndex;
    }

    public String getCorrectAnswerText() {
        if (correctIndex >= 0 && correctIndex < options.length)
            return options[correctIndex];
        else
            return "";
    }

    // helper for interactive asking (keeps driver tidy)
    public void ask(Scanner input, MCQbased quizSet, CoPilot copilot, int[] scoreHolder) {
        System.out.println("\nQ: " + question);
        for (int i = 0; i < options.length; i++)
            System.out.println((i + 1) + ". " + options[i]);

        int ans;
        while (true) {
            try {
                System.out.print("Your answer: ");
                ans = Integer.parseInt(input.nextLine());
                if (ans < 1 || ans > 4) throw new Exception();
                break;
            } catch (Exception e) {
                System.out.println("⚠ Enter a valid option between 1–4!");
            }
        }

        if (quizSet.checkAnswer(this, ans - 1)) {
            System.out.println("✔ Correct!");
            scoreHolder[0]++;
        } else {
            System.out.println("✘ Wrong!");
            System.out.println(quizSet.getExplanation(this, ans - 1));
        }
    }
}
