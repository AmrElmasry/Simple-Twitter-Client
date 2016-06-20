package com.example.amrelmasry.simpletwitterclient.followers;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.amrelmasry.simpletwitterclient.R;
import com.example.amrelmasry.simpletwitterclient.SimpleClientApp;
import com.example.amrelmasry.simpletwitterclient.common.models.AccessToken;
import com.example.amrelmasry.simpletwitterclient.common.models.User;
import com.example.amrelmasry.simpletwitterclient.common.utils.EndlessRecyclerViewScrollListener;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFollowersInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FollowersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FollowersFragment extends Fragment implements FollowersContract.View {

    private static final String ACCESS_TOKEN_PARAM = "accessTokenParam";
    private static final String SCREEN_NAME_PARAM = "screenNameParam";
    private static final String DEFAULT_CURSOR = "-1";
    private static final String LAST_PAGE_CURSOR = "0";
    private final String LOG_TAG = getClass().getSimpleName();
    @Inject
    FollowersPresenter presenter;
    @BindView(R.id.followers_loading_progressBar)
    ProgressBar progressBar;
    @BindView(R.id.followers_recycle_view)
    RecyclerView followersRecyclerView;
    private AccessToken mAccessToken;
    private String mScreenName;
    private String mCursor;
    private OnFollowersInteractionListener mListener;
    private FollowersAdapter adapter;

    public FollowersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param accessToken Parameter 1.
     * @param screenName  Parameter 2.
     * @return A new instance of fragment FollowersFragment.
     */

    public static FollowersFragment newInstance(AccessToken accessToken, String screenName) {
        FollowersFragment fragment = new FollowersFragment();
        Bundle args = new Bundle();
        args.putParcelable(ACCESS_TOKEN_PARAM, Parcels.wrap(accessToken));
        args.putString(SCREEN_NAME_PARAM, screenName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mAccessToken = Parcels.unwrap(getArguments().getParcelable(ACCESS_TOKEN_PARAM));
            mScreenName = getArguments().getString(SCREEN_NAME_PARAM);
        }
        mCursor = DEFAULT_CURSOR;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_followers, container, false);


        // Setup ButterKnife
        ButterKnife.bind(this, view);

        progressBar.setVisibility(View.VISIBLE);

        // initialize the adapter
        adapter = new FollowersAdapter(new ArrayList<>(), getActivity());
        followersRecyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        followersRecyclerView.setLayoutManager(layoutManager);
        followersRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                if (mCursor.equals(LAST_PAGE_CURSOR)) {
                    mListener.onNoMoreFollowersToShow();
                } else {
                    presenter.retrieveFollowers(mScreenName, mCursor);
                }
            }
        });

        // Inject this fragment to singleton RestComponent
        SimpleClientApp simpleClientApp = (SimpleClientApp) getActivity().getApplication();
        simpleClientApp.getRestComponent(mAccessToken).inject(this);

        // register this view to the presenter and start getting account info
        presenter.registerView(this);

        presenter.retrieveFollowers(mScreenName, mCursor);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFollowersInteractionListener) {
            mListener = (OnFollowersInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFollowersInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onFollowersRetrieved(List<User> followers) {
        progressBar.setVisibility(View.INVISIBLE);
        Log.i(LOG_TAG, "Received followers, show them in recycle view");
        int cursorSize = adapter.getItemCount();
        adapter.addFollowers(followers);
        adapter.notifyItemRangeInserted(cursorSize, followers.size());
    }

    @Override
    public void updateCursor(String newCursor) {
        mCursor = newCursor;
    }

    @Override
    public void onFailedToRetrieveFollowers() {
        // TODO
        progressBar.setVisibility(View.INVISIBLE);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFollowersInteractionListener {
        void onFollowerItemClicked(Uri uri);

        void onNoMoreFollowersToShow();
    }
}
