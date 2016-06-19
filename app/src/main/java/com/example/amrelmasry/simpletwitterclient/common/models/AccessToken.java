package com.example.amrelmasry.simpletwitterclient.common.models;

public class AccessToken {
    private String token;
    private String tokenSecret;

    public AccessToken(String token, String tokenSecret) {
        this.token = token;
        this.tokenSecret = tokenSecret;
    }

    public String getToken() {
        return token;
    }

    public String getTokenSecret() {
        return tokenSecret;
    }

    @Override
    public String toString() {
        return "Access Token is " + token + " Access Token Secret is " + tokenSecret;
    }

    public boolean isEmpty() {
        return (token == null || tokenSecret == null || token.isEmpty() || tokenSecret.isEmpty());
    }
}
