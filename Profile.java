package oopfinalproject.model;

public class Profile {

    private String name;
    private String id;
    private int totalAttempts;
    private double average;

    public Profile(String name, String id, int totalAttempts, double average) {
        this.name = name;
        this.id = id;
        this.totalAttempts = totalAttempts;
        this.average = average;
    }

    // for login
    public Profile(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() { return name; }
    public String getId() { return id; }

    public int getTotalAttempts() { return totalAttempts; }
    public double getAverage() { return average; }
}
