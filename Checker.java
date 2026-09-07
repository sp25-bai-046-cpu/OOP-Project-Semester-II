package oopfinalproject.model;


//oopfinalproject.model.Checker class- This class specifically aims to gather all the exception handling present in Code(till now).
public class Checker {
    public static void check_choice(int choice) {
        if (choice >= 7) {
            throw new IllegalArgumentException("The option you entered is out of range");
        }
    }
}
