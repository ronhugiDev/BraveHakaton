package com.example.herost;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class TalesAPI
{
    /**
     * get preview function
     * @param username username
     * @return all heroes, heroes user already has done
     */
    public static List<ArrayList<String>> getPreview(String username) throws IOException, ParseException {
        List<ArrayList<String>> preview = new ArrayList<>();
        ArrayList<String> allHeroes;
        ArrayList<String> passedHeroes;
        HttpURLConnection conn;
        DataOutputStream os;
        byte[] postData;
        URL url;

        url = new URL("http://127.0.0.1:5000/tales/preview"); //important to add the trailing slash after add
        String[] inputData = {"{\"username\": \"" + username + "\"}"};

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

            JSONObject jsonObj = (JSONObject) new JSONParser().parse(sb.toString());
            allHeroes = (ArrayList<String>) API.convertObjectToList(jsonObj.get("all_heroes"));
            passedHeroes = (ArrayList<String>) API.convertObjectToList(jsonObj.get("passed_heroes"));
            preview.add(allHeroes);
            preview.add(passedHeroes);

        }

        return preview;
    }



    public static HashMap<Character, HashMap<String, String>> getTalesById(int id) throws ParseException, IOException {
        HashMap<Character, HashMap<String, String>> tales = null;
        HttpURLConnection conn;
        DataOutputStream os;
        byte[] postData;
        URL url;

        url = new URL("http://127.0.0.1:5000/tales/get_tales_by_id"); //important to add the trailing slash after add
        String[] inputData = {"{\"taleId\":" + id + "}"};

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

            JSONObject jsonObj = (JSONObject) new JSONParser().parse(sb.toString());

            HashMap<Character, HashMap<String, String>> hashMap = (HashMap)jsonObj;
            tales = hashMap;
        }

        return tales;
    }
}
