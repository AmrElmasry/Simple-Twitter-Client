package com.example.amrelmasry.simpletwitterclient.followers;

import com.example.amrelmasry.simpletwitterclient.common.models.User;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * this is a model class for the api response for retrieve followers list
 */
public class FollowersResponse {

    @SerializedName("users")
    private List<User> followers;
    @SerializedName("next_cursor_str")
    private String nextCursor;

    public FollowersResponse(List<User> followers, String nextCursor) {
        this.followers = followers;
        this.nextCursor = nextCursor;
    }

    public String getNextCursor() {
        return nextCursor;
    }

    public List<User> getFollowers() {
        return followers;
    }
}
