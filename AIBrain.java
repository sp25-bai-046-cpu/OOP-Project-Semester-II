package oopfinalproject.model;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
  // using json manually only (NO SCHEMA)
public class AIBrain {

    private static final String API_URL = "https://api.groq.com/openai/v1/chat/completions";

    public static String askAI(String prompt) {
        try {
            String apiKey = System.getenv("GROQ_API_KEY");//environment variable

            if (apiKey == null || apiKey.isEmpty()) {
                return "❌ ERROR: GROQ_API_KEY is not set.";
            } // to protect from crash

            String body = "{"
                    + "\"model\":\"llama-3.1-8b-instant\","
                    + "\"messages\":[{\"role\":\"user\",\"content\":\"" + escape(prompt) + "\"}]"
                    + "}"; //body for AI choose model,etc

            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + apiKey);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true); //connections are correct

            try (OutputStream os = conn.getOutputStream()) {//try with resources auto close
                os.write(body.getBytes());
            }//sending request to body server

            int status = conn.getResponseCode();
            InputStream is = (status >= 200 && status < 300)
                    ? conn.getInputStream()
                    : conn.getErrorStream();// connection checking if 200 to 299 success
            //else error

            BufferedReader br = new BufferedReader(new InputStreamReader(is));//using buffer for speed
            StringBuilder sb = new StringBuilder();//using Input stream as uses uni code
            String line;//reading the data send by AI

            while ((line = br.readLine()) != null) {
                sb.append(line);//while data is present
            }

            String response = sb.toString();//convert all respond to string
//            System.out.println("\n---- RAW AI RESPONSE ----\n");
//            System.out.println(response);
//            System.out.println("\n---- END RAW RESPONSE ----\n");

            return extractContent(response);//gives the required content

        } catch (Exception e) {
            return "❌ Exception: " + e.getMessage();
        }
    } //data extraction is happening jo data required h ap wo let parse horaha h
    private static String escape(String text) {
        return text
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n");//avoide breakage of AI connection
    }


    // NEW FIXED EXTRACTOR
    private static String extractContent(String json) {
        //manual json parsing ,not using json library
        String key = "\"content\":\"";
        int start = json.lastIndexOf(key); // last content block (correct one), final content detaa h
        if (start == -1) return "⚠ AI returned no content.";// if not giving safe fall back

        start += key.length();
        StringBuilder out = new StringBuilder();

        for (int i = start; i < json.length(); i++) {
            char c = json.charAt(i);

            if (c == '"' && json.charAt(i - 1) != '\\')
                break;//actual end pr stop

            if (c == '\\' && i + 1 < json.length()) {
                char next = json.charAt(i + 1);

                if (next == 'n') {
                    out.append('\n');
                    i++;
                    continue;
                }
                if (next == '"') {
                    out.append('"');
                    i++;
                    continue;
                } //remove all the extra spaces etc
            }

            out.append(c);
        }

        return out.toString().trim();//give final answer only
    }
}
