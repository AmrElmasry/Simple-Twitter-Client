package com.example.amrelmasry.simpletwitterclient.common.injection;

import com.example.amrelmasry.simpletwitterclient.authentication.AuthFragment;
import com.example.amrelmasry.simpletwitterclient.authentication.AuthInteractor;
import com.example.amrelmasry.simpletwitterclient.authentication.AuthPresenter;

import javax.inject.Singleton;

import dagger.Component;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;

@Singleton
@Component(modules = AuthModule.class)
public interface AuthComponent {

    void inject(AuthFragment authFragment);

    CommonsHttpOAuthConsumer commonsHttpOAuthConsumer();

    OAuthProvider oAuthProvider();

    AuthInteractor authInteractor();

    AuthPresenter authPresenter();
}
