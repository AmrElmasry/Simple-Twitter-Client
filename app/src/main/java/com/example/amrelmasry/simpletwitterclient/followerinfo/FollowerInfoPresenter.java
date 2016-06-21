package com.example.amrelmasry.simpletwitterclient.followerinfo;

import android.util.Log;

import com.example.amrelmasry.simpletwitterclient.common.DataManager;
import com.example.amrelmasry.simpletwitterclient.common.mvpbase.BasePresenter;

import javax.inject.Inject;

public class FollowerInfoPresenter extends BasePresenter<FollowerInfoContract.View>
        implements FollowerInfoContract.Presenter {

    private DataManager mDataManager;
    private final String LOG_TAG = getClass().getSimpleName();

    @Inject
    public FollowerInfoPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void retrieveLastTweetsInfo(String screenName, int count) {
        mDataManager.getTweets(screenName, count).subscribe(tweets -> {
            view.onTweetsRetrieved(tweets);
            Log.i(LOG_TAG, "Tweets Retrieved successfully");
        }, throwable -> {
            Log.e(LOG_TAG, throwable.getMessage());
        });
    }
}
