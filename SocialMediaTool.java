package com.socialmedia.tool;

import java.util.Scanner;

public class SocialMediaTool {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the company's domain name :");
        String domain = scanner.nextLine();
        scanner.close();

        SocialMediaChecker checker = new SocialMediaChecker();
        checker.searchSocialMedia(domain);
    }
}
