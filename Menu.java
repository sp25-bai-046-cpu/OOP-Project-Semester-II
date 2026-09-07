package oopfinalproject.model;


//Applying interface
interface GreetingProvider{
    public String greeting();
}
// ===================== MENU =====================
public class Menu implements GreetingProvider {
    public String displayGreeting() { return "=======Welcome to Prep Pilot !!=========="; }

    @Override
    public String greeting() {
        return "=============================\n Always There for you ✨";
    }
    public String exitCompletely(){
        return "Exiting Prep Pilot completely. Goodbye!💝";
    }
}
