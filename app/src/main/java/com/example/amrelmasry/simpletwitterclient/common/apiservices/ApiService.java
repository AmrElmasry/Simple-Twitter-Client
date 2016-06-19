package com.example.amrelmasry.simpletwitterclient.common.apiservices;

import com.example.amrelmasry.simpletwitterclient.common.models.User;

import retrofit2.http.GET;
import rx.Observable;

public interface ApiService {
    @GET("account/verify_credentials.json?skip_status=true")
    Observable<User> verifyUser();
}
