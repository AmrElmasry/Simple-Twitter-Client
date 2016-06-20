package com.example.amrelmasry.simpletwitterclient.accounts;

import android.util.Log;

import com.example.amrelmasry.simpletwitterclient.common.DataManager;
import com.example.amrelmasry.simpletwitterclient.common.mvpbase.BasePresenter;

import javax.inject.Inject;

public class AccountManagerPresenter extends BasePresenter<AccountManagerContract.View>
        implements AccountManagerContract.Presenter {

    private final String LOG_TAG = getClass().getSimpleName();
    private DataManager mDataManager;

    @Inject
    public AccountManagerPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void retrieveAccountInfo() {
        mDataManager.getLoggedInUserInformation().subscribe(user -> {
            Log.i(LOG_TAG, "User retrieved: " + user.toString());
            view.onAccountInfoRetrieved(user);
        }, throwable -> {
            Log.e(LOG_TAG, "Failed to retrieve User");
            view.onAccountInfoError();
        });
    }

    public void registerView(AccountManagerContract.View view) {
        this.view = view;
    }


}
