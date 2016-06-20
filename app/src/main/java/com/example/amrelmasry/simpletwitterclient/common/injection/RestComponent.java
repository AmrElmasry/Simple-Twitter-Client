package com.example.amrelmasry.simpletwitterclient.common.injection;

import com.example.amrelmasry.simpletwitterclient.accounts.AccountManagerFragment;
import com.example.amrelmasry.simpletwitterclient.followers.FollowersFragment;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

@Singleton
@Component(modules = RestModule.class)
public interface RestComponent {

    void inject(AccountManagerFragment accountManagerFragment);

    void inject(FollowersFragment followersFragment);

    Retrofit retrofit();
}


