package com.example.amrelmasry.simpletwitterclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.amrelmasry.simpletwitterclient.common.models.AccessToken;
import com.example.amrelmasry.simpletwitterclient.common.models.User;
import com.example.amrelmasry.simpletwitterclient.followerinfo.FollowerInfoFragment;

import org.parceler.Parcels;

public class FollowerInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follower_info);

        Intent intent = getIntent();
        User follower = Parcels.unwrap(intent.getParcelableExtra(getString(R.string.follower_key)));
        AccessToken accessToken = Parcels.unwrap((intent.getParcelableExtra(getString(R.string.access_key))));
        FollowerInfoFragment followerInfoFragment =
                FollowerInfoFragment.newInstance(follower, accessToken);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.follower_info_fragment_container, followerInfoFragment);
        fragmentTransaction.commit();
    }

}
