package oopfinalproject.model;

public class InvalidPromptException extends Exception{

    public InvalidPromptException(String Prompt){
        super(Prompt);
    }

    public static boolean studyRelevant(String prompt){
        prompt=prompt.toLowerCase();
        String AIresponse= AIBrain.askAI("is it related to study?yes or no? "+prompt);
        AIresponse=AIresponse.toLowerCase(); // asking AI by giving prompt
        //AI tells if it is study relevant or not instead of processing information
        if(AIresponse.contains("yes"))//if AI response statement contains yes word throw exception
            return true;
        return false;
    }
}
