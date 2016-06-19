package com.example.amrelmasry.simpletwitterclient.common.models;

import org.parceler.Parcel;

@Parcel
public class AccessToken {

    // fields must be public to be detected by annotations
    public String token;
    public String tokenSecret;

    // required empty constructor for the Parceler library
    public AccessToken() {
    }
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
