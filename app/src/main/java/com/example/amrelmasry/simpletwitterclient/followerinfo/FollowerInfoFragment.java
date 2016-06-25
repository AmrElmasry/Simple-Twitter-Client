package com.example.amrelmasry.simpletwitterclient.followerinfo;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.amrelmasry.simpletwitterclient.R;
import com.example.amrelmasry.simpletwitterclient.SimpleClientApp;
import com.example.amrelmasry.simpletwitterclient.common.models.AccessToken;
import com.example.amrelmasry.simpletwitterclient.common.models.Tweet;
import com.example.amrelmasry.simpletwitterclient.common.models.User;
import com.example.amrelmasry.simpletwitterclient.common.utils.RecyclerViewItemsDecoration;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FollowerInfoFragment extends Fragment implements FollowerInfoContract.View {

    private static final String ARG_FOLLOWER = "FollowerParam";
    private static final String ARG_ACCESS_TOKEN = "AccessTokenParam";
    private static final int TWEETS_COUNT = 10;
    private final String LOG_TAG = getClass().getSimpleName();
    @Inject
    FollowerInfoPresenter presenter;
    @BindView(R.id.follower_info_tweets_recycler_view)
    RecyclerView tweetsRecyclerView;
    @BindView(R.id.follower_info_profile_image)
    ImageView followerProfileImageView;
    @BindView(R.id.follower_info_background_image)
    ImageView followerBackgroundImageView;
    @BindView(R.id.follower_info_toolbar)
    Toolbar toolbar;
    @BindView(R.id.follower_info_collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.follower_info_screen_name)
    TextView screenNameTextView;
    @BindView(R.id.follower_info_bio)
    TextView bioTextView;
    private User mFollower;
    private AccessToken mAccessToken;
    private FollowerInfoAdapter adapter;


    public FollowerInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param follower    Parameter 1.
     * @param accessToken Parameter 2.
     * @return A new instance of fragment FollowerInfoFragment.
     */
    public static FollowerInfoFragment newInstance(User follower, AccessToken accessToken) {
        FollowerInfoFragment fragment = new FollowerInfoFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_FOLLOWER, Parcels.wrap(follower));
        args.putParcelable(ARG_ACCESS_TOKEN, Parcels.wrap(accessToken));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mFollower = Parcels.unwrap(getArguments().getParcelable(ARG_FOLLOWER));
            mAccessToken = Parcels.unwrap(getArguments().getParcelable(ARG_ACCESS_TOKEN));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_follower_info, container, false);
        ButterKnife.bind(this, view);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // show follower info
        collapsingToolbar.setTitle(mFollower.getFullName());
        collapsingToolbar.setExpandedTitleColor(ContextCompat.getColor(getActivity(), android.R.color.transparent));

        Picasso.with(getActivity()).load(mFollower.getBiggerProfileImageUrl()).into(followerProfileImageView);
        Picasso.with(getActivity()).load(mFollower.getProfileBannerUrl()).into(followerBackgroundImageView);

        screenNameTextView.setText(String.format("@%s", mFollower.getScreenName()));
        bioTextView.setText(mFollower.getBio());

        // Inject this fragment to singleton RestComponent
        SimpleClientApp simpleClientApp = (SimpleClientApp) getActivity().getApplication();
        simpleClientApp.getRestComponent(mAccessToken).inject(this);
        // register this view to the presenter and start getting account info
        presenter.registerView(this);
        presenter.retrieveLastTweetsInfo(mFollower.getScreenName(), TWEETS_COUNT);

        // initialize the adapter
        adapter = new FollowerInfoAdapter(new ArrayList<>());
        tweetsRecyclerView.setAdapter(adapter);
        tweetsRecyclerView.addItemDecoration(new RecyclerViewItemsDecoration(getActivity()));
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        tweetsRecyclerView.setLayoutManager(layoutManager);

        // to override the up button behavior
        setHasOptionsMenu(true);

        return view;
    }


    // override the up button behavior
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTweetsRetrieved(List<Tweet> tweets) {
        Log.i(LOG_TAG, "Received followers, show them in recycle view");
        int cursorSize = adapter.getItemCount();
        adapter.addTweets(tweets);
        adapter.notifyItemRangeInserted(cursorSize, tweets.size());
    }


}
