package com.example.amrelmasry.simpletwitterclient.common.models;

import com.google.gson.annotations.SerializedName;

public class Tweet {

    @SerializedName("text")
    private String text;

    public Tweet(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
