package oopfinalproject.model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileHandlingForApp {

    private final String fileName;

    public FileHandlingForApp(String fileName) {
        this.fileName = fileName;
    }

    public void saveUser(Profile user) {

        if (user == null) return; // if no user then no data in file
// updates the values of what ever quiz is taken
        int mcq   = Session.progressMap.getOrDefault("MCQ", 0);
        int tf    = Session.progressMap.getOrDefault("TF", 0);
        int match = Session.progressMap.getOrDefault("MATCH", 0);
//average of all three types of quiz
        int total = mcq + tf + match;
        double avg = total / 3.0;

        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName, true))) {
//try with resources and append to prevent overwriting
            pw.println(
                    user.getName() + " | " +
                            user.getId()   + " | " +
                            "MCQ: " + mcq +
                            " | TF: " + tf +
                            " | MATCH: " + match +
                            " | TOTAL: " + total +
                            " | AVG: " + String.format("%.2f", avg)
            );

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
