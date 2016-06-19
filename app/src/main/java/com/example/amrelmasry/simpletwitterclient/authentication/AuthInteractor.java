package com.example.amrelmasry.simpletwitterclient.authentication;

import android.net.Uri;

import com.example.amrelmasry.simpletwitterclient.common.models.AccessToken;
import com.example.amrelmasry.simpletwitterclient.common.utils.NetworkUtils;

import javax.inject.Inject;

import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AuthInteractor {

    private final String LOG_TAG = getClass().getSimpleName();
    private OAuthProvider httpOAuthProvider;
    private CommonsHttpOAuthConsumer httpOAuthConsumer;

    @Inject
    public AuthInteractor(OAuthProvider httpOAuthProvider, CommonsHttpOAuthConsumer httpOAuthConsumer) {
        this.httpOAuthProvider = httpOAuthProvider;
        this.httpOAuthConsumer = httpOAuthConsumer;
    }

    /***
     * this method is the first step in Auth process, it returns with the Authorization url
     *
     * @return Observable of String Authorization url , use it for retrieving access token
     */
    public Observable<String> getReguestToken() {
        return Observable.create((Observable.OnSubscribe<String>) subscriber -> {
            try {
                String url = httpOAuthProvider.retrieveRequestToken(httpOAuthConsumer, NetworkUtils.CALLBACK_URL);
                subscriber.onNext(url);
                subscriber.onCompleted();
            } catch (Exception e) {
                subscriber.onError(e);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    /***
     * this method is the second step in Auth process, it returns with the AccessToken object
     *
     * @param uri authorization url retrieved from step one
     * @return Observable of AccessToken object
     */
    public Observable<AccessToken> getAccessToken(Uri uri) {

        return Observable.create((Observable.OnSubscribe<AccessToken>) subscriber -> {
            try {
                String verifier = uri.getQueryParameter(oauth.signpost.OAuth.OAUTH_VERIFIER);
                httpOAuthProvider.retrieveAccessToken(httpOAuthConsumer, verifier);
                String token = httpOAuthConsumer.getToken();
                String tokenSecret = httpOAuthConsumer.getTokenSecret();

                subscriber.onNext(new AccessToken(token, tokenSecret));
                subscriber.onCompleted();
            } catch (Exception e) {
                subscriber.onError(e);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
