package oopfinalproject.model;

import oopfinalproject.model.Assisstant;

public class CoPilot extends Assisstant {
    @Override
    //used in mcq,tf,match
    public String explainAnswer(String question, String correctAnswer, String userAnswer) {
        if(userAnswer.equalsIgnoreCase(correctAnswer)) return "✔ Correct!";
      //conversions to avoid errors with AI connections
        question = question.replace("\n", " ").trim();
        correctAnswer = correctAnswer.replace("\n", " ").trim();
        userAnswer = userAnswer.replace("\n", " ").trim();

        String prompt = "Explain clearly and fully why the correct answer is correct and why the user's answer is wrong. "
                + "Use simple language suitable for students. "
                + "Do NOT shorten the explanation.\n"
                + "Question: " + question + "\n"
                + "Correct Answer: " + correctAnswer + "\n"
                + "User Answer: " + userAnswer + "\n"
                + "Provide step-by-step explanation:";

        String explanation = AIBrain.askAI(prompt);
         //ensuring smooth AI flow and exact answer
        // Only trim leading/trailing spaces and collapse multiple empty lines
        explanation = explanation.trim().replaceAll("\\n{2,}", "\n");

        // Append correct answer explicitly at the end
        return explanation + "\n✅ Correct Answer: " + correctAnswer;
    }//correct answer always visible making easy for system to identify

    public String displayInfo(String topic) {
        String prompt = "Explain the topic '" + topic + "' in simple terms for a student.";
        return AIBrain.askAI(prompt); //Ask for any doubt
        //used in copilot
    }
}
