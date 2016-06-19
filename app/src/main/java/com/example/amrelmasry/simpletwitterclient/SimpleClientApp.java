package com.example.amrelmasry.simpletwitterclient;

import android.app.Application;

import com.example.amrelmasry.simpletwitterclient.common.injection.AuthComponent;
import com.example.amrelmasry.simpletwitterclient.common.injection.DaggerAuthComponent;


public class SimpleClientApp extends Application {

    private AuthComponent authComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        authComponent = DaggerAuthComponent.create();
    }

    public AuthComponent getAuthComponent() {
        return authComponent;
    }
}
