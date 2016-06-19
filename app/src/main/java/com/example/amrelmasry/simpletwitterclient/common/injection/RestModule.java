package com.example.amrelmasry.simpletwitterclient.common.injection;

import com.example.amrelmasry.simpletwitterclient.common.models.AccessToken;
import com.example.amrelmasry.simpletwitterclient.common.utils.NetworkUtils;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;
import se.akerfeldt.okhttp.signpost.SigningInterceptor;

@Module
public class RestModule {

    private AccessToken accessToken;

    public RestModule(AccessToken accessToken) {
        this.accessToken = accessToken;
    }

    @Provides
    public OkHttpOAuthConsumer provideConsumer() {
        OkHttpOAuthConsumer consumer = new OkHttpOAuthConsumer(NetworkUtils.CONSUMER_Key, NetworkUtils.CONSUMER_SECRET);
        consumer.setTokenWithSecret(accessToken.token, accessToken.tokenSecret);
        return consumer;
    }

    @Provides
    public OkHttpClient provideClient(OkHttpOAuthConsumer okHttpOAuthConsumer) {
        return new OkHttpClient.Builder()
                .addInterceptor(new SigningInterceptor(okHttpOAuthConsumer))
                .build();
    }


    @Provides
    public Retrofit provideRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.twitter.com/1.1/")
                .client(client)
                .build();
    }


}
