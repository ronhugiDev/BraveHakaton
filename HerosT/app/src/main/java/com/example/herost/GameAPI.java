package com.example.herost;
import org.json.simple.parser.ParseException;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;


public class GameAPI {
     public static float getUserScoreEndGame(int heroId, int[] answers, String username) throws IOException, ParseException {
         float score = 0;
         HttpURLConnection conn;
         DataOutputStream os;
         byte[] postData;
         URL url;

         url = new URL("http://127.0.0.1:5000//game/end_session"); //important to add the trailing slash after add
         String[] inputData = {
                 "{      \"username\"  : \"" + username + "\"," +
                         "\"heroId\"   : " + heroId + "," +
                         "\"userAnswers\"  : " + Arrays.toString(answers) +
                 "}"
         };

         for (String input : inputData) {
             postData = input.getBytes(StandardCharsets.UTF_8);
             conn = (HttpURLConnection) url.openConnection();
             conn.setDoOutput(true);
             conn.setRequestMethod("POST");
             conn.setRequestProperty("Content-Type", "application/json");
             conn.setRequestProperty("charset", "utf-8");
             conn.setRequestProperty("Content-Length", Integer.toString(input.length()));
             os = new DataOutputStream(conn.getOutputStream());
             os.write(postData);
             os.flush();

             if (conn.getResponseCode() != 200) {
                 throw new RuntimeException("Failed : HTTP error code : "
                         + conn.getResponseCode());
             }

             BufferedReader br = new BufferedReader(new InputStreamReader(
                     (conn.getInputStream())));

             StringBuilder sb = new StringBuilder();
             String line;
             while ((line = br.readLine()) != null) {
                 sb.append(line);
             }

            score = Float.parseFloat(sb.toString());
         }
         return score;
     }
}
