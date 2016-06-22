package com.example.amrelmasry.simpletwitterclient.common.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/***
 * This class contains Utilities for API communication and Authentication
 */
public class NetworkUtils {

    public static final String CALLBACK_URL = "app://simpletwitterclient";
    public static final String CONSUMER_Key = "E2wLT0CeTsA38hV45GIswj2XK";
    public static final String CONSUMER_SECRET = "IOpnpm9YEpWly1gjmNwDaCAMNz3E2zL2cNDYhJRq5AC8MVlrRX";

    public static final String REQUEST_TOKEN_URL = "https://api.twitter.com/oauth/request_token";
    public static final String ACCESS_TOKEN_URL = "https://api.twitter.com/oauth/access_token";
    public static final String AUTHORIZE_URL = "https://api.twitter.com/oauth/authorize";


    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }
}
