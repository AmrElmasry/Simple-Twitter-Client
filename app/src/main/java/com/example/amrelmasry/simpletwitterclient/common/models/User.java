package com.example.amrelmasry.simpletwitterclient.common.models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class User {

    private static final String NORMAL_IMAGE_VARIANT = "_normal";
    private static final String BIGGER_IMAGE_VARIANT = "_bigger";

    // public fields required by Parceler
    @SerializedName("screen_name")
    public String screenName;

    @SerializedName("name")
    public String fullName;

    @SerializedName("profile_image_url")
    public String profileImageUrl;

    @SerializedName("description")
    public String bio;

    @SerializedName("profile_banner_url")
    public String profileBannerUrl;

    public User(String screenName, String fullName, String profileImageUrl, String bio, String profileBannerUrl) {
        this.screenName = screenName;
        this.fullName = fullName;
        this.profileImageUrl = profileImageUrl;
        this.bio = bio;
        this.profileBannerUrl = profileBannerUrl;
    }

    // required by Parceler
    public User() {
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

    public String getProfileBannerUrl() {
        return profileBannerUrl;
    }
}
