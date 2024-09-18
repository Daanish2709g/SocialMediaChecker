package SocialMediaChecker;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;

public class GetTwitterAccessToken {
    public static void main(String[] args) {
        try {
            GetTwitterAccessToken task = new GetTwitterAccessToken(new URL("https://api.twitter.com/oauth2/token"));
            
            // Set your app's information here:
            task.setParam("grant_type", "authorization_code");
            task.setParam("code", "YOUR_AUTHORIZATION_CODE");
            task.setParam("redirect_uri", "YOUR_REDIRECT_URI");
            task.setParam("client_id", "YOUR_CLIENT_ID");
            task.setParam("client_secret", "YOUR_CLIENT_SECRET");
            
            String[] response = task.runTask();
            for (String line : response) {
                System.out.println(line);
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
    
    private static final String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36";
    private final URL url;
    private HashMap<String, String> params;
    
    public GetTwitterAccessToken(URL target) {
        url = target;
        params = new HashMap<>();
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
            Iterator<String> paramsKeys = params.keySet().iterator();
            while (paramsKeys.hasNext()) {
                String key = paramsKeys.next();
                postBlob.append(key).append("=").append(URLEncoder.encode(params.get(key), "utf-8"));
                if (paramsKeys.hasNext())
                    postBlob.append("&");
            }
            String chunk = postBlob.toString();
            System.out.println(String.format("POST to server: %s", chunk));
            postStream.writeBytes(chunk);
            postStream.close();
        }
        DataInputStream getStream = new DataInputStream(connection.getInputStream());
        StringBuilder response = new StringBuilder();
        String str;
        while ((str = getStream.readLine()) != null) {
            response.append(str).append("\n");
        }
        getStream.close();
        return response.toString().split("\n");
    }
}
