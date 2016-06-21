package com.example.amrelmasry.simpletwitterclient.followerinfo;

import com.example.amrelmasry.simpletwitterclient.common.models.Tweet;
import com.example.amrelmasry.simpletwitterclient.common.mvpbase.BaseViewInterface;

import java.util.List;

public class FollowerInfoContract {
    // implemented by the View
    interface View extends BaseViewInterface {
        void onTweetsRetrieved(List<Tweet> tweets);

    }

    // implemented by the Presenter
    interface Presenter {
        void retrieveLastTweetsInfo(String screenName, int count);
    }
}
