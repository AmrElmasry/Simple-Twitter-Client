package com.example.amrelmasry.simpletwitterclient.common.injection;

import android.app.Application;

import com.example.amrelmasry.simpletwitterclient.common.models.AccessToken;
import com.example.amrelmasry.simpletwitterclient.common.utils.NetworkUtils;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
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
    @Singleton
    public OkHttpOAuthConsumer provideConsumer() {
        OkHttpOAuthConsumer consumer = new OkHttpOAuthConsumer(NetworkUtils.CONSUMER_Key, NetworkUtils.CONSUMER_SECRET);
        consumer.setTokenWithSecret(accessToken.token, accessToken.tokenSecret);
        return consumer;
    }

    @Provides
    @Singleton
    public Cache provideOkHttpCache(Application application) {
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        return new Cache(application.getCacheDir(), cacheSize);
    }

    @Provides
    @Singleton
    public Interceptor provideCacheControlInterceptor(Application application) {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response originalResponse = chain.proceed(chain.request());
                if (NetworkUtils.isNetworkAvailable(application.getBaseContext())) {
                    int maxAge = 60; // read from cache for 1 minute
                    return originalResponse.newBuilder()
                            .removeHeader("Pragma")
                            .removeHeader("Cache-Control")
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .build();
                } else {
                    int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                    return originalResponse.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .build();
                }
            }
        };

    }

    @Provides
    @Singleton
    public OkHttpClient provideClient(OkHttpOAuthConsumer okHttpOAuthConsumer, Cache cache, Interceptor cacheInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(new SigningInterceptor(okHttpOAuthConsumer))
                .addNetworkInterceptor(cacheInterceptor)
                .cache(cache)
                .build();
    }


    @Provides
    @Singleton
    public Retrofit provideRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.twitter.com/1.1/")
                .client(client)
                .build();
    }


}
