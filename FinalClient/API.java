import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class API {

    // convert Object to List
    public static List<?> convertObjectToList(Object obj) {
        List<?> list = new ArrayList<>();
        if (obj.getClass().isArray()) {
            list = Arrays.asList((Object[])obj);
        } else if (obj instanceof Collection) {
            list = new ArrayList<>((Collection<?>)obj);
        }
        return list;
    }

    /**
     * registerUser
     * @param username username
     * @param password password
     * @return whether the user could register
     */
    public boolean registerUser(String username, String password) throws IOException, ParseException {
        boolean canRegister = false;

        HttpURLConnection conn;
        DataOutputStream os;
        byte[] postData;

        URL url = new URL("http://127.0.0.1:5000/users/register"); //important to add the trailing slash after add
        String[] inputData = {"{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}"};

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

            System.out.println(sb);

            JSONObject jsonObj = (JSONObject) new JSONParser().parse(sb.toString());


            canRegister = (Boolean) (convertObjectToList(jsonObj.get("response")).get(0));

        }
        return canRegister;
    }


    /**
     * loginUser function
     * @param username username
     * @param password password
     * @return whether the login was successful
     */
    public long loginUser(String username, String password) throws IOException, ParseException {
        HttpURLConnection conn;
        DataOutputStream os;
        long userId = -1;
        byte[] postData;

        URL url = new URL("http://127.0.0.1:5000/users/login"); //important to add the trailing slash after add
        String[] inputData = {"{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}"};

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
            userId = (long) jsonObj.get("userId");

        }
        return userId;
    }


    public static void main(String[] args) throws IOException, ParseException {
        API conn = new API();
        System.out.println(conn.registerUser("Nada1212v", "1234"));
    }
}
