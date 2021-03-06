package com.example.amrelmasry.simpletwitterclient.accounts;

import com.example.amrelmasry.simpletwitterclient.common.models.User;
import com.example.amrelmasry.simpletwitterclient.common.mvpbase.BaseViewInterface;

public interface AccountManagerContract {


    // implemented by the View
    interface View extends BaseViewInterface {
        void onAccountInfoRetrieved(User user);

        void onAccountInfoError();

    }

    // implemented by the Presenter
    interface Presenter {
        void retrieveAccountInfo();
    }
}
