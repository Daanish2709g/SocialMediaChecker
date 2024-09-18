package SocialMediaChecker;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONObject;

public class GetAccessToken {
    public static void main(String[] varargs) {
        try {
            GetAccessToken task = new GetAccessToken(new URL("https://api.instagram.com/oauth/access_token"));

            // Set your app's information here:
            task.setParam("client_id", "YOUR_CLIENT_ID");
            task.setParam("client_secret", "YOUR_CLIENT_SECRET");
            task.setParam("grant_type", "authorization_code");
            task.setParam("redirect_uri", "YOUR_REDIRECT_URI"); // Ensure this matches exactly
            task.setParam("code", "YOUR_AUTHORIZATION_CODE"); // This should be the code received from the initial authorization request

            String[] response = task.runTask();
            
            // Handle and print the response
            if (response.length > 0) {
                String jsonResponse = response[0];
                try {
                    JSONObject json = new JSONObject(jsonResponse);
                    System.out.println(json.toString(4)); // Pretty print with 4-space indentation
                } catch (Exception e) {
                    System.out.println("Error parsing response: " + e.getMessage());
                }
            } else {
                System.out.println("No response received.");
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private static final String userAgent = "Mozilla/5.0 (iPhone; U; CPU iPhone OS 7_0 like Mac OS X; en-us) AppleWebKit/532.9 (KHTML, like Gecko) Version/4.0.5 Mobile/8A293 Safari/6531.22.7";
    private final URL url;
    private HashMap<String, String> params;

    public GetAccessToken(URL target) {
        url = target;
        params = new HashMap<String, String>();
    }

    public void setParam(String key, String value) {
        this.params.put(key, value);
    }

    public String[] runTask() throws IOException {
        URLConnection connection = url.openConnection();
        connection.setDoInput(true);
        connection.setRequestProperty("User-Agent", userAgent);
        if (params.size() > 0) {
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            DataOutputStream postStream = new DataOutputStream(connection.getOutputStream());
            StringBuilder postBlob = new StringBuilder();
            Iterator<String> paramskeys = params.keySet().iterator();
            while (paramskeys.hasNext()) {
                String key = paramskeys.next();
                postBlob.append(key).append("=").append(URLEncoder.encode(params.get(key), "utf-8"));
                if (paramskeys.hasNext())
                    postBlob.append("&");
            }
            String chunk = postBlob.toString();
            System.out.println(String.format("POST to server: %s", chunk));
            postStream.writeBytes(chunk);
            postStream.close();
        }
        DataInputStream getStream = new DataInputStream(connection.getInputStream());
        ArrayList<String> result = new ArrayList<String>();
        String str;
        while (null != (str = getStream.readLine())) {
            result.add(str);
        }
        getStream.close();
        return result.toArray(new String[0]);
    }
}
