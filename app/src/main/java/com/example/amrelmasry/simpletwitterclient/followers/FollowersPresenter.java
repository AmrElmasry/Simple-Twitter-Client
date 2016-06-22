package com.example.amrelmasry.simpletwitterclient.followers;

import android.util.Log;

import com.example.amrelmasry.simpletwitterclient.common.DataManager;
import com.example.amrelmasry.simpletwitterclient.common.mvpbase.BasePresenter;

import javax.inject.Inject;

public class FollowersPresenter extends BasePresenter<FollowersContract.View>
        implements FollowersContract.Presenter {


    private final String LOG_TAG = getClass().getSimpleName();
    private DataManager mDataManager;

    @Inject
    public FollowersPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }


    @Override
    public void retrieveFollowers(String screenName, String cursor) {
        mDataManager.getFollowersList(screenName, cursor).subscribe(followersResponse -> {
            Log.i(LOG_TAG, "Followers Retrieved Successfully");
            view.onFollowersRetrieved(followersResponse.getFollowers());
            view.onNextCursorRetrieved(followersResponse.getNextCursor());
        }, throwable -> {
            view.onFailedToRetrieveFollowers();
            Log.e(LOG_TAG, "Failed to retrieve Followers" + throwable.getMessage());
        });
    }

    @Override
    public void reloadFollowers(String screenName, String cursor) {
        mDataManager.getFollowersList(screenName, cursor).subscribe(followersResponse -> {
            Log.i(LOG_TAG, "Followers Retrieved Successfully");
            view.onReloadComplete(followersResponse.getFollowers());
            view.onNextCursorRetrieved(followersResponse.getNextCursor());
        }, throwable -> {
            view.onFailedToRetrieveFollowers();
            Log.e(LOG_TAG, "Failed to retrieve Followers" + throwable.getMessage());
        });
    }
}
