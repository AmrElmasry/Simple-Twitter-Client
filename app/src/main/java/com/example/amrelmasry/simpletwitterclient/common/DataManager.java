package com.example.amrelmasry.simpletwitterclient.common;

import com.example.amrelmasry.simpletwitterclient.common.apiservices.ApiService;
import com.example.amrelmasry.simpletwitterclient.common.models.User;
import com.example.amrelmasry.simpletwitterclient.followers.FollowersResponse;

import javax.inject.Inject;

import retrofit2.Retrofit;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DataManager {

    private ApiService apiService;

    @Inject
    public DataManager(Retrofit retrofit) {
        apiService = retrofit.create(ApiService.class);
    }

    public Observable<User> getLoggedInUserInformation() {
        return apiService
                .verifyUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<FollowersResponse> getFollowersList(String screenName, String cursor) {
        return apiService
                .getFollowers(screenName, cursor)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
