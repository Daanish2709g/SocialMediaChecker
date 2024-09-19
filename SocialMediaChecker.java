package com.socialmedia.tool;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class SocialMediaChecker {

    private static final String[] SOCIAL_MEDIA_PLATFORMS = {
        "facebook.com/",    // Updated for Facebook pages
        "instagram.com/",   // Updated for Instagram profiles
        "twitter.com/"      // Updated for Twitter profiles
    };

    public void checkSocialMedia(String domain) {
        for (String platform : SOCIAL_MEDIA_PLATFORMS) {
            String url = "https://" + platform + domain;
            System.out.println("Checking: " + url);
            boolean active = isActive(url);
            if (active) {
                System.out.println(url + " is active.");
            } else {
                System.out.println(url + " is not active.");
            }
        }
    }

    private boolean isActive(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET"); // Use GET for better checking
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setInstanceFollowRedirects(true); // Follow redirects
            
            // Set the User-Agent header to mimic a standard browser
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            
            int responseCode = connection.getResponseCode();
            // Consider a page active if the response code is 200 or 3xx (redirects)
            return responseCode >= 200 && responseCode < 400;
        } catch (IOException e) {
            // Log the exception if needed
            System.err.println("Error checking URL: " + urlString + " - " + e.getMessage());
            return false;
        }
    }
}
