package com.example.amrelmasry.simpletwitterclient.authentication;

import android.net.Uri;

import com.example.amrelmasry.simpletwitterclient.common.models.AccessToken;
import com.example.amrelmasry.simpletwitterclient.common.mvpbase.BaseViewInterface;

public interface AuthContract {

    // implemented by the View
    interface View extends BaseViewInterface {
        void onRequestTokenRetrieved(String url);

        void onAccessTokenRetrieved(AccessToken accessToken);

        void onRequestTokenError(Throwable throwable);

        void onAccessTokenError(Throwable throwable);
    }

    // implemented by the Presenter
    interface Presenter {
        void retrieveRequestToken();

        void retrieveAccessToken(Uri uri);
    }
}
