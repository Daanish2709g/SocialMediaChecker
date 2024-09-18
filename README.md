# SocialMediaChecker

This project demonstrates how to obtain an Instagram Access Token using Java. The GetAccessToken class handles sending a POST request to the Instagram API to exchange an authorization code for an access token.

## Overview
The project involves:

Setting up the Java project.
Writing the GetAccessToken class.
Configuring the required parameters.
Running the application to obtain an Instagram Access Token.

## Prerequisites
Java Development Kit (JDK) 8 or later
An Instagram Developer account with an App created
Authorization code from Instagram's OAuth process

## Setup Instructions
1. Clone the Repository
Start by cloning the repository to your local machine:

git clone https://github.com/yourusername/instagram-access-token-project.git
cd instagram-access-token-project


## Set Up the Java Project
Open your favorite Java IDE (e.g., IntelliJ IDEA, Eclipse).
Import the project or create a new Java project and add the GetAccessToken.java file to the src directory.

Configure the GetAccessToken Class
Open the GetAccessToken.java file and modify the following parameters:

client_id: Your Instagram App’s Client ID
client_secret: Your Instagram App’s Client Secret
redirect_uri: The redirect URI you specified during the OAuth authorization process
code: The authorization code received after the user authorizes your app


task.setParam("client_id", "YOUR_CLIENT_ID");
task.setParam("client_secret", "YOUR_CLIENT_SECRET");
task.setParam("grant_type", "authorization_code");
task.setParam("redirect_uri", "YOUR_REDIRECT_URI");
task.setParam("code", "YOUR_AUTHORIZATION_CODE");

 ## Run the Application
Compile and run the GetAccessToken class.
The application will make a POST request to the Instagram API and attempt to retrieve an access token.
If successful, the output will display the access token in JSON format.

Handle Errors
If you encounter a 400 Bad Request error:

Double-check the API endpoint and parameters.
Ensure the redirect_uri matches the one used during the authorization step.
Verify the code and other parameters are correct.


## output: {
    "access_token": "IGQVJ...XYZ",
    "token_type": "bearer",
    "user_id": "17841400000000000"
}
