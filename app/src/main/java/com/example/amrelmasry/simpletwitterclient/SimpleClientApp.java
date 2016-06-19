package com.example.amrelmasry.simpletwitterclient;

import android.app.Application;

import com.example.amrelmasry.simpletwitterclient.common.injection.AuthComponent;
import com.example.amrelmasry.simpletwitterclient.common.injection.DaggerAuthComponent;
import com.example.amrelmasry.simpletwitterclient.common.injection.DaggerRestComponent;
import com.example.amrelmasry.simpletwitterclient.common.injection.RestComponent;
import com.example.amrelmasry.simpletwitterclient.common.injection.RestModule;
import com.example.amrelmasry.simpletwitterclient.common.models.AccessToken;


public class SimpleClientApp extends Application {

    private AuthComponent authComponent;
    private RestComponent restComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        authComponent = DaggerAuthComponent.create();
    }

    public RestComponent getRestComponent(AccessToken accessToken) {
        if (restComponent == null) {
            restComponent = DaggerRestComponent.builder().restModule(new RestModule(accessToken)).build();
            return restComponent;
        } else {
            return restComponent;
        }
    }

    public AuthComponent getAuthComponent() {
        return authComponent;
    }
}
