package com.example.amrelmasry.simpletwitterclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amrelmasry.simpletwitterclient.common.models.AccessToken;
import com.example.amrelmasry.simpletwitterclient.common.models.User;
import com.example.amrelmasry.simpletwitterclient.followers.FollowersFragment;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FollowersActivity extends AppCompatActivity
        implements
        FollowersFragment.OnFollowersInteractionListener {

    private static String FOLLOWERS_FRAGMENT_TAG = "FollowersFragmentTag";


    private final String LOG_TAG = getClass().getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.user_screen_name)
    TextView userScreenNameTextView;

    private AccessToken mAccessToken;
    private String mCurrentUserScreenName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers);

        // Setup Butterknife
        ButterKnife.bind(this);
        // Setup the toolbar
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        mAccessToken = Parcels.unwrap(intent.getParcelableExtra("ACCESS_TOKEN"));
        mCurrentUserScreenName = intent.getStringExtra("USER_SCREEN_NAME");
        // check if the User is logged in, get saved data
        updateToolbar();
        getUserFollowers();
    }

    private void updateToolbar() {
        userScreenNameTextView.setText(mCurrentUserScreenName);
    }


    private void showFragment(Fragment fragment, String fragmentTag) {
        Log.i(LOG_TAG, "Showing the fragment: " + fragment.getClass().getSimpleName());
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_activity_fragment_container, fragment, fragmentTag);
        fragmentTransaction.commit();
    }


    private void getUserFollowers() {
        FollowersFragment followersFragment = FollowersFragment.newInstance(mAccessToken, mCurrentUserScreenName);
        showFragment(followersFragment, FOLLOWERS_FRAGMENT_TAG);
    }


    @Override
    public void onFollowerItemClicked(User follower) {
        // open FollowerInfoActivity to show Follower Info
        Intent intent = new Intent(this, FollowerInfoActivity.class);
        intent.putExtra("Follower", Parcels.wrap(follower));
        intent.putExtra("AccessToken", Parcels.wrap(mAccessToken));
        startActivity(intent);
    }

    @Override
    public void onNoMoreFollowersToShow() {
        Toast.makeText(this, "All followers are loaded", Toast.LENGTH_SHORT).show();
    }
}
