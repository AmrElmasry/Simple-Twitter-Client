package com.example.amrelmasry.simpletwitterclient.common.injection;

import com.example.amrelmasry.simpletwitterclient.authentication.AuthInteractor;
import com.example.amrelmasry.simpletwitterclient.authentication.AuthPresenter;
import com.example.amrelmasry.simpletwitterclient.common.utils.NetworkUtils;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;

@Module
public class AuthModule {

    @Singleton
    @Provides
    public CommonsHttpOAuthConsumer provideCommonsHttpOAuthConsumer() {
        return new CommonsHttpOAuthConsumer(NetworkUtils.CONSUMER_Key, NetworkUtils.CONSUMER_SECRET);
    }

    @Singleton
    @Provides
    public OAuthProvider provideOAuthProvider() {
        return new CommonsHttpOAuthProvider(
                NetworkUtils.REQUEST_TOKEN_URL,
                NetworkUtils.ACCESS_TOKEN_URL,
                NetworkUtils.AUTHORIZE_URL);
    }

    @Provides
    AuthInteractor getAuthInteractor(OAuthProvider oAuthProvider, CommonsHttpOAuthConsumer commonsHttpOAuthConsumer) {
        return new AuthInteractor(oAuthProvider, commonsHttpOAuthConsumer);
    }

    @Provides
    AuthPresenter getAuthPresenter(AuthInteractor authInteractor) {
        return new AuthPresenter(authInteractor);
    }
}
