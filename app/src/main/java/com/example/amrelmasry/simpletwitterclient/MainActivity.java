package com.example.amrelmasry.simpletwitterclient;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.amrelmasry.simpletwitterclient.authentication.AuthFragment;
import com.example.amrelmasry.simpletwitterclient.common.models.AccessToken;
import com.example.amrelmasry.simpletwitterclient.common.utils.NetworkUtils;

public class MainActivity extends AppCompatActivity implements AuthFragment.OnAuthFinishListener {

    private static String AUTH_FRAGMENT_TAG = "AuthTag";
    private static String ACCESS_TOKEN_SHAREDPREFRENCES = "AccessTokenSP";
    private static String TOKEN_KEY = "TokenKey";
    private static String TOKEN_SECRET_KEY = "TokenSecretKey";

    private final String LOG_TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new AuthFragment(), AUTH_FRAGMENT_TAG);
        fragmentTransaction.commit();

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
        SharedPreferences sharedPreferences = getSharedPreferences(ACCESS_TOKEN_SHAREDPREFRENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOKEN_KEY, accessToken.getToken());
        editor.putString(TOKEN_SECRET_KEY, accessToken.getTokenSecret());
        editor.commit();
        Log.i(LOG_TAG, "Token Saved Successfully");
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
}
