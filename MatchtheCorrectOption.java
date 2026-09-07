package oopfinalproject.model;

import java.util.ArrayList;

class MatchtheCorrectOption extends Quiz {

    private ArrayList<String> leftList;
    private ArrayList<String> rightList;
    private Timer timerMTC;
    private Assisstant chatbot;

    public MatchtheCorrectOption(Timer timerMTC,
                                 ArrayList<String> leftList,
                                 ArrayList<String> rightList,
                                 Assisstant chatbot) {

        super("Match the Column", timerMTC);
        this.timerMTC = timerMTC;
        this.leftList = leftList;
        this.rightList = rightList;
        this.chatbot = chatbot;
    }

    // ---------- TIMER ----------
    public void startTimer() {
        timerMTC.start();
    }

    public String stopTimer() {
        timerMTC.stop();
        return "⏱ Total Time: " + timerMTC.getDuration() + " seconds";
    }

    // ---------- BACKEND CHECK ----------
    // leftIndex and rightIndex given by DriverTest or GUI
    public boolean checkMatch(int leftIndex, int rightIndex) {

        return leftIndex == rightIndex;
    }

    // Explanation from AI when wrong

    public String getExplanation(int leftIndex, int userRightIndex) {

        String prompt = "Explain why the correct match is:\n" +
                leftList.get(leftIndex) + " → " + rightList.get(leftIndex) +
                "\nBut the user chose:\n" +
                rightList.get(userRightIndex) +
                "\nGive a simple step-by-step explanation.";

        return chatbot.ask(prompt);
    }


    // ---------- For DriverTest to access data ----------
    public ArrayList<String> getLeftList() {
        return leftList;
    }

    public ArrayList<String> getRightList() {
        return rightList;
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
