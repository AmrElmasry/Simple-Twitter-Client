package com.example.amrelmasry.simpletwitterclient.common.models;

import com.google.gson.annotations.SerializedName;

public class User {

    private static final String NORMAL_IMAGE_VARIANT = "_normal";
    private static final String BIGGER_IMAGE_VARIANT = "_bigger";

    @SerializedName("screen_name")
    private String screenName;

    @SerializedName("name")
    private String fullName;

    @SerializedName("profile_image_url")
    private String profileImageUrl;

    @SerializedName("description")
    private String bio;


    public User(String screenName, String fullName, String profileImageUrl, String bio) {
        this.screenName = screenName;
        this.fullName = fullName;
        this.profileImageUrl = profileImageUrl;
        this.bio = bio;
    }


    public String getScreenName() {
        return screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    /***
     * this method for processing the normal image size url (48px by 48px) and get
     * bigger image size url (73px by 73px)
     * this is based on how Twitter api handle photos, check :
     * https://dev.twitter.com/overview/general/user-profile-images-and-banners
     *
     * @return bigger image url
     */
    public String getBiggerProfileImageUrl() {
        return profileImageUrl.replace(NORMAL_IMAGE_VARIANT, BIGGER_IMAGE_VARIANT);
    }

    @Override
    public String toString() {
        return "User screenName is: " + screenName
                + " User profile Image Url is: " + profileImageUrl;
    }

    public String getBio() {
        return bio;
    }

    public String getFullName() {
        return fullName;
    }
}
