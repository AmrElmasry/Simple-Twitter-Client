package com.example.amrelmasry.simpletwitterclient.common.models;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("screen_name")
    private String screenName;

    @SerializedName("profile_image_url")
    private String profileImageUrl;


    public User(String screenName, String profileImageUrl) {
        this.screenName = screenName;
        this.profileImageUrl = profileImageUrl;
    }


    public String getScreenName() {
        return screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    @Override
    public String toString() {
        return "User screenName is: " + screenName
                + " User profile Image Url is: " + profileImageUrl;
    }

}
