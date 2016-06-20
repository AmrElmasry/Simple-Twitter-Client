package com.example.amrelmasry.simpletwitterclient.authentication;

import android.net.Uri;

import com.example.amrelmasry.simpletwitterclient.common.mvpbase.BasePresenter;

import javax.inject.Inject;

public class AuthPresenter extends BasePresenter<AuthContract.View> implements AuthContract.Presenter {

    private final String LOG_TAG = getClass().getSimpleName();
    private AuthInteractor authInteractor;

    @Inject
    public AuthPresenter(AuthInteractor authInteractor) {
        this.authInteractor = authInteractor;
    }


    @Override
    public void retrieveRequestToken() {
        authInteractor.getReguestToken()
                .subscribe(url -> {
                    view.onRequestTokenRetrieved(url);

                }, throwable -> {
                    view.onRequestTokenError(throwable);
                });
    }

    @Override
    public void retrieveAccessToken(Uri uri) {
        authInteractor.getAccessToken(uri).subscribe(accessToken -> {
            view.onAccessTokenRetrieved(accessToken);
        }, throwable -> {
            view.onAccessTokenError(throwable);
        });
    }

    public void registerView(AuthContract.View view) {
        this.view = view;
    }
}
