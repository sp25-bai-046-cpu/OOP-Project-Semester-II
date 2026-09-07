package oopfinalproject.model;

import java.util.HashMap;
import java.util.Map;

public class Session {

    public static Profile currentUser;

    // MCQ / TF / Match scores
    public static Map<String, Integer> progressMap = new HashMap<>();
//for each quiz type we have integer value that is score so used hashmap
    public static String lastTopic; //topic entered by user
    public static int timeTaken;
    public static long quizStartTime;
    public static long quizEndTime;

    public static int getTimeTaken() {
            return (int) ((quizEndTime - quizStartTime) / 1000);
        }

    public static void reset() { // no longer previous user
        currentUser = null;
        progressMap.clear();
        lastTopic = null;
        timeTaken = 0;
    }
}
