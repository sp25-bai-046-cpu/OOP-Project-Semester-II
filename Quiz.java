package oopfinalproject.model;

import java.util.ArrayList;
import java.util.Scanner;



// ===================== QUIZ =====================

public abstract class Quiz {
    protected String Question;
    private oopfinalproject.model.Quiz.Timer timer;

    public Quiz(String question, oopfinalproject.model.Quiz.Timer timer) { this.Question = question; this.timer = timer; }

    public abstract String displayRemarks(int score, int total);


    public static class Timer {
        private long startTime=0, endTime=0;
        public void start(){ startTime = System.currentTimeMillis(); }
        public void stop(){ endTime = System.currentTimeMillis(); }
        public long getDuration(){ return ((endTime - startTime)/1000); }
    }

    public void startQuizTimer(){
        timer.start();
    }
    public String stopQuizTimer(){
        timer.stop();
        return "⏱ Total Time: "+timer.getDuration()+" seconds";
    }
}
