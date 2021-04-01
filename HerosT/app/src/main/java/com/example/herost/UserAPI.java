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


public class UserAPI {
    /**
     * registerUser
     * @param username username
     * @param password password
     * @return whether the user could register
     */
    public static boolean registerUser(String username, String password) throws IOException, ParseException {
        boolean canRegister = false;

        HttpURLConnection conn;
        DataOutputStream os;
        byte[] postData;

        URL url = new URL("http://10.0.2.2:5000/users/register"); //important to add the trailing slash after add
        String[] inputData = {"{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}"};

        for (String input : inputData) {
            postData = input.getBytes("utf-8");
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

            System.out.println(sb);

            JSONObject jsonObj = (JSONObject) new JSONParser().parse(sb.toString());


            canRegister = (Boolean) (API.convertObjectToList(jsonObj.get("response")).get(0));

        }
        return canRegister;
    }


    /**
     * loginUser function
     * @param username username
     * @param password password
     * @return whether the login was successful
     */
    public static long loginUser(String username, String password) throws IOException, ParseException {
        HttpURLConnection conn;
        DataOutputStream os;
        long userId = -1;
        byte[] postData;

        URL url = new URL("http://10.0.2.2:5000/users/login"); //important to add the trailing slash after add
        String[] inputData = {"{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}"};

        for (String input : inputData) {
            postData = input.getBytes("utf-8");
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
            userId = (long) jsonObj.get("userId");

        }
        return userId;
    }

    /**
     * get user record function
     * @param username username
     * @return user's record (int - heroCode)
     */
    public static long getUserRecord(String username) throws IOException, ParseException {
        long score = 0;

        HttpURLConnection conn;
        DataOutputStream os;
        byte[] postData;

        URL url = new URL("http://10.0.2.2:5000/users/get_user_record"); //important to add the trailing slash after add
        String[] inputData = {"{\"username\": \"" + username + "\"}"};

        for (String input : inputData) {
            postData = input.getBytes("utf-8");
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

            if (!sb.toString().equals("404")) {
                JSONObject jsonObj = (JSONObject) new JSONParser().parse(sb.toString());
                score = (Long) jsonObj.get("record");
            }
        }

        return score;
    }
}
