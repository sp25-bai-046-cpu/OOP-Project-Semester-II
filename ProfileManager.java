package oopfinalproject.model;

import java.util.ArrayList;

public class ProfileManager {

    private ArrayList<Profile> profiles;

    public ProfileManager() {
        profiles = new ArrayList<>();
    }

    public void addProfile(Profile p) {
        profiles.add(p);
    }

    public ArrayList<Profile> getAllProfiles() {
        return profiles;
    }

    // For printing (if needed)
    public String userInfo(Profile p) {
        return "=========USER PROFILE======\n" +
                "🧑User Name: " + p.getName() +
                "\n🎫 User ID: " + p.getId() +
                "\n================================";
    }

    public String progressHeader() {
        return "\n========= 📊 Progress Report =========";
    }

    public String averageProgressHeader(double avg) {
        return String.format("⭐Average: %.2f /5\n=================", avg);
    }
}
