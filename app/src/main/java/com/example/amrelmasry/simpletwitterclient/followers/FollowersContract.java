package com.example.amrelmasry.simpletwitterclient.followers;

import com.example.amrelmasry.simpletwitterclient.common.models.User;
import com.example.amrelmasry.simpletwitterclient.common.mvpbase.BaseViewInterface;

import java.util.List;

public interface FollowersContract {
    // implemented by the View
    interface View extends BaseViewInterface {
        void onFollowersRetrieved(List<User> followers);

        void updateCursor(String newCursor);

        void onFailedToRetrieveFollowers();
    }

    // implemented by the Presenter
    interface Presenter {
        void retrieveFollowers(String screenName, String cursor);
    }
}
