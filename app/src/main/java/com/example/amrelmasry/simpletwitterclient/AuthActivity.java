package com.example.amrelmasry.simpletwitterclient;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.amrelmasry.simpletwitterclient.accounts.AccountManagerFragment;
import com.example.amrelmasry.simpletwitterclient.authentication.AuthFragment;
import com.example.amrelmasry.simpletwitterclient.common.models.AccessToken;
import com.example.amrelmasry.simpletwitterclient.common.models.User;
import com.example.amrelmasry.simpletwitterclient.common.utils.NetworkUtils;

import org.parceler.Parcels;

public class AuthActivity extends AppCompatActivity implements AuthFragment.OnAuthFinishListener,
        AccountManagerFragment.OnAccountManagerInteractionListener {
    private static final String AUTH_FRAGMENT_TAG = "AuthFragmentTag";
    private static final String ACCOUNT_MANAGER_FRAGMENT_TAG = "AccountManagerFragmentTag";
    private static final String SAVED_USER_SHARED_PREFERENCES = "ScreenNamePreferences";
    private static final String SCREEN_NAME_KEY = "SavedUserKey";
    private static final String ACCESS_TOKEN_SHARED_PREFERENCES = "AccessTokenSharedPreferences";
    private static final String TOKEN_KEY = "TokenKey";
    private static final String TOKEN_SECRET_KEY = "TokenSecretKey";
    private final String LOG_TAG = getClass().getSimpleName();
    private AccessToken mAccessToken;
    private String mCurrentUserScreenName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        // get saved data if the user logged in before
        mCurrentUserScreenName = getSavedScreenName();
        mAccessToken = getSavedAccessToken();

        // check if the data is valid
        if (mCurrentUserScreenName != null && !mAccessToken.isEmpty()) {
            // user is logged in before
            // show user followers
            Log.d(LOG_TAG, "user is logged in, show followers");
            startFollowersActivity();
        } else {
            // show register screen
            Log.d(LOG_TAG, "user is not logged in, show log in button");
            openLoginScreen();
        }
    }


    @Override
    public void openBrowserForAuth(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    @Override
    public void showAuthError() {
        Log.e(LOG_TAG, "AuthError");
        Toast.makeText(AuthActivity.this, "Authentication error, try to login again", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // check if there is an intent got from authentication
        Uri uri = getIntent().getData();
        if (uri != null && uri.toString().startsWith(NetworkUtils.CALLBACK_URL)) {
            // pass uri to AuthFragment
            AuthFragment authFragment = (AuthFragment) getSupportFragmentManager().findFragmentByTag(AUTH_FRAGMENT_TAG);
            if (authFragment != null) {
                Log.i(LOG_TAG, "Received intent, callback url is passing to AuthFragment");
                authFragment.onCallbackUrlReceived(uri);
            }
        }
    }


    private void startFollowersActivity() {
        Intent intent = new Intent(this, FollowersActivity.class);
        intent.putExtra("USER_SCREEN_NAME", mCurrentUserScreenName);
        intent.putExtra("ACCESS_TOKEN", Parcels.wrap(mAccessToken));
        startActivity(intent);
        finish();
    }

    @Override
    public void showAccountInfoError() {
        Toast.makeText(this, "Failed to get Account Info, try log in again", Toast.LENGTH_SHORT).show();
    }

    private void showFragment(Fragment fragment, String fragmentTag) {
        Log.i(LOG_TAG, "Showing the fragment: " + fragment.getClass().getSimpleName());
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.activity_auth_fragment_container, fragment, fragmentTag);
        fragmentTransaction.commit();
    }

    private void openLoginScreen() {
        showFragment(new AuthFragment(), AUTH_FRAGMENT_TAG);
    }


    /**
     * this method should be called after a successful authentication process
     * it starts a process to get the user details
     */
    private void getLoggedInUserDetails() {
        if (!mAccessToken.isEmpty()) {
            AccountManagerFragment accountManagerFragment = AccountManagerFragment.newInstance(mAccessToken);
            showFragment(accountManagerFragment, ACCOUNT_MANAGER_FRAGMENT_TAG);
        } else {
            Log.e(LOG_TAG, "AccessToken is corrupted");
        }
    }


    @Override
    public void saveAccessToken(AccessToken accessToken) {
        // save token and token secret to SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(ACCESS_TOKEN_SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOKEN_KEY, accessToken.getToken());
        editor.putString(TOKEN_SECRET_KEY, accessToken.getTokenSecret());
        editor.apply();
        Log.i(LOG_TAG, "Token Saved Successfully");

        // set current AccessToken
        mAccessToken = accessToken;

        // get Logged in user details and store it
        getLoggedInUserDetails();
    }


    @Override
    public void saveAccountInfo(User user) {
        SharedPreferences sharedPreferences = getSharedPreferences(SAVED_USER_SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SCREEN_NAME_KEY, user.getScreenName());
        editor.apply();
        Log.i(LOG_TAG, "Token Saved Successfully");
        // update screenName
        mCurrentUserScreenName = user.getScreenName();
        // user account saved successfully, now get user followers
        startFollowersActivity();
    }

    private AccessToken getSavedAccessToken() {
        SharedPreferences mSharedPreferences = getSharedPreferences(ACCESS_TOKEN_SHARED_PREFERENCES, MODE_PRIVATE);
        String token = mSharedPreferences.getString(TOKEN_KEY, null);
        String tokenSecret = mSharedPreferences.getString(TOKEN_SECRET_KEY, null);
        return new AccessToken(token, tokenSecret);
    }

    private String getSavedScreenName() {
        SharedPreferences sharedPreferences = getSharedPreferences(SAVED_USER_SHARED_PREFERENCES, MODE_PRIVATE);
        return sharedPreferences.getString(SCREEN_NAME_KEY, null);

    }
}
