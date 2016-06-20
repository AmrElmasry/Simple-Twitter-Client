package com.example.amrelmasry.simpletwitterclient;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.example.amrelmasry.simpletwitterclient.accounts.AccountManagerFragment;
import com.example.amrelmasry.simpletwitterclient.authentication.AuthFragment;
import com.example.amrelmasry.simpletwitterclient.common.models.AccessToken;
import com.example.amrelmasry.simpletwitterclient.common.models.User;
import com.example.amrelmasry.simpletwitterclient.common.utils.NetworkUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements AuthFragment.OnAuthFinishListener, AccountManagerFragment.OnAccountManagerInteractionListener {

    private static final String SAVED_USER_KEY = "SavedUserKey";
    private static String AUTH_FRAGMENT_TAG = "AuthFragmentTag";
    private static String ACCOUNT_MANAGER_FRAGMENT_TAG = "AccountManagerFragmentTag";
    private static String ACCESS_TOKEN_SHARED_PREFERENCES = "AccessTokenSharedPreferences";
    private static String TOKEN_KEY = "TokenKey";
    private static String TOKEN_SECRET_KEY = "TokenSecretKey";
    private final String LOG_TAG = getClass().getSimpleName();
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private AccessToken mAccessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup Butterknife
        ButterKnife.bind(this);
        // Setup the toolbar
        setSupportActionBar(toolbar);
        showFragment(new AuthFragment(), AUTH_FRAGMENT_TAG);
    }

    @Override
    public void openBrowserForAuth(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    @Override
    public void showAuthError() {
        // TODO handle error cases later
        Log.e(LOG_TAG, "AuthError");
        Toast.makeText(MainActivity.this, "Failed  to log in", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void saveAccessToken(AccessToken accessToken) {
        // save token and token secret to SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(ACCESS_TOKEN_SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOKEN_KEY, accessToken.getToken());
        editor.putString(TOKEN_SECRET_KEY, accessToken.getTokenSecret());
        editor.commit();
        Log.i(LOG_TAG, "Token Saved Successfully");

        // set current AccessToken
        mAccessToken = accessToken;

        // get Logged in user details and store it
        getLoggedInUserDetails();
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


    private void showFragment(Fragment fragment, String fragmentTag) {
        Log.i(LOG_TAG, "Sowing the fragment: " + fragment.getClass().getSimpleName());
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment, fragmentTag);
        fragmentTransaction.commit();
    }

    @Override
    public void showAccountInfo(User user) {
        // just show toast for now
        Toast.makeText(MainActivity.this, user.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void saveAccountInfo(User user) {
        SharedPreferences sharedPreferences = getSharedPreferences(ACCESS_TOKEN_SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SAVED_USER_KEY, user.getScreenName());
        editor.commit();
        Log.i(LOG_TAG, "Token Saved Successfully");
    }

    @Override
    public void showAccountInfoError() {
        // just show toast for now
        Toast.makeText(MainActivity.this, "Failed to get Account Info", Toast.LENGTH_SHORT).show();
    }
}
