package oopfinalproject.model;

public class DisplayManagerForCoPilot implements GreetingProvider {

    public String copilotHeader()
    {
        return "\n=====WELCOME TO COPILOT🕵‍♀=====x\n";
    }

    public String furtherAssistance()
    {
        return "\nDo you need more assisstence?(yes/no):";
    }

    @Override
    public String greeting()
    {
        return "\nHOW CAN I HELP YOU?";
    }

    public String copilotSeeOff()
    {
        return "Exiting then❗........Take Care buddy.....😊"+
                "\n=====================================================";
    }
}
