package oopfinalproject.model;

// ===================== AI-INTEGRATED MCQ QUIZ =====================
public class DisplayManagerForMCQ implements GreetingProvider {
    @Override
    public String greeting()
    {
        return "============ Best of luck 👩‍💻 ============";
    }

    public String inputHeader()
    {
        return "Enter quiz topic: ";
    }
    public String setDifficulty(){
        return "Select difficulty: 1. Easy 2. Medium 3. Hard";
    }

    public String AIheader(){
        return "⏹Fetching AI-generated MCQs...";
    }
}
