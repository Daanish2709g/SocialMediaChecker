package com.socialmedia.tool;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class SocialMediaChecker {

    private static final String[] SOCIAL_MEDIA_SEARCH_QUERIES = {
        "https://www.linkedin.com/search/results/companies/?keywords=",  // LinkedIn company search
        "https://www.facebook.com/search/top?q=",                        // Facebook search
        "https://twitter.com/search?q="                                  // Twitter search
    };

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the company name: ");
        String companyName = scanner.nextLine();

        SocialMediaChecker checker = new SocialMediaChecker();
        checker.searchSocialMedia(companyName);
    }

    public void searchSocialMedia(String companyName) {
        for (String platformSearchQuery : SOCIAL_MEDIA_SEARCH_QUERIES) {
            String searchUrl = buildSearchUrl(platformSearchQuery, companyName);
            System.out.println("Searching: " + searchUrl);
            if (isActive(searchUrl)) {
                openInBrowser(searchUrl);
            } else {
                System.out.println("No active page found for: " + companyName + " on this platform.");
            }
        }
    }

    private String buildSearchUrl(String platformQuery, String companyName) {
        return platformQuery + companyName.replace(" ", "%20");  // Replace spaces with URL encoded spaces (%20)
    }

    private boolean isActive(String urlString) {
        try {
            HttpURLConnection connection = setupConnection(urlString);
            int responseCode = connection.getResponseCode();
            return isSuccessCode(responseCode);
        } catch (IOException e) {
            System.err.println("Error checking URL: " + urlString + " - " + e.getMessage());
            return false;
        }
    }

    private void openInBrowser(String urlString) {
        String os = System.getProperty("os.name").toLowerCase();
        try {
            if (os.contains("win")) {
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + urlString);
            } else if (os.contains("mac")) {
                Runtime.getRuntime().exec("open " + urlString);
            } else if (os.contains("nix") || os.contains("nux")) {
                Runtime.getRuntime().exec("xdg-open " + urlString);
            } else {
                System.err.println("Cannot open browser on this platform.");
            }
            System.out.println("Opened in browser: " + urlString);
        } catch (IOException e) {
            System.err.println("Failed to open browser for URL: " + urlString + " - " + e.getMessage());
        }
    }

    private HttpURLConnection setupConnection(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        connection.setInstanceFollowRedirects(true);
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");
        return connection;
    }

    private boolean isSuccessCode(int responseCode) {
        return responseCode >= 200 && responseCode < 400;
    }
}
