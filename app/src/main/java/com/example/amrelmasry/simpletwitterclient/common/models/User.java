package com.example.amrelmasry.simpletwitterclient.common.models;

import com.google.gson.annotations.SerializedName;

public class User {

    private static final String NORMAL_IMAGE_VARIANT = "_normal";
    private static final String BIGGER_IMAGE_VARIANT = "_bigger";

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

}
