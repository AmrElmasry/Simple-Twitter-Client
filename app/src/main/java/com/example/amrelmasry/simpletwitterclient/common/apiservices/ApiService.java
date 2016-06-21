package com.example.amrelmasry.simpletwitterclient.common.apiservices;

import com.example.amrelmasry.simpletwitterclient.common.models.Tweet;
import com.example.amrelmasry.simpletwitterclient.common.models.User;
import com.example.amrelmasry.simpletwitterclient.followers.FollowersResponse;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface ApiService {
    @GET("account/verify_credentials.json?skip_status=true")
    Observable<User> verifyUser();

    @GET("followers/list.json?skip_status=true&include_user_entities=false")
    Observable<FollowersResponse> getFollowers(@Query("screen_name") String screenName,
                                               @Query("cursor") String cursor);

    @GET("statuses/user_timeline.json")
    Observable<List<Tweet>> getTweets(@Query("screen_name") String screenName,
                                      @Query("count") int count);
}
