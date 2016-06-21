package com.example.amrelmasry.simpletwitterclient.accounts;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.amrelmasry.simpletwitterclient.R;
import com.example.amrelmasry.simpletwitterclient.SimpleClientApp;
import com.example.amrelmasry.simpletwitterclient.common.models.AccessToken;
import com.example.amrelmasry.simpletwitterclient.common.models.User;

import org.parceler.Parcels;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AccountManagerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AccountManagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

/**
 * the fragment role is to handle logged in user accounts
 */

public class AccountManagerFragment extends Fragment implements AccountManagerContract.View {

    private static final String AccessToken_PARAM = "AccessToken";
    private final String LOG_TAG = getClass().getSimpleName();
    @Inject
    AccountManagerPresenter presenter;
    @BindView(R.id.account_loading_progressBar)
    ProgressBar loadingProgressBar;
    private AccessToken mAccessToken;
    private OnAccountManagerInteractionListener mListener;

    public AccountManagerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param accessToken Parameter 1.
     * @return A new instance of fragment AccountManagerFragment.
     */
    public static AccountManagerFragment newInstance(AccessToken accessToken) {
        AccountManagerFragment fragment = new AccountManagerFragment();
        Bundle args = new Bundle();
        args.putParcelable(AccessToken_PARAM, Parcels.wrap(accessToken));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mAccessToken = Parcels.unwrap(getArguments().getParcelable(AccessToken_PARAM));
        }

        Log.i(LOG_TAG, "Created " + mAccessToken);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account_manager, container, false);

        // Setup ButterKnife
        ButterKnife.bind(this, view);

        // show progressBar
        loadingProgressBar.setVisibility(View.VISIBLE);

        // Inject this fragment to singleton RestComponent
        SimpleClientApp simpleClientApp = (SimpleClientApp) getActivity().getApplication();
        simpleClientApp.getRestComponent(mAccessToken).inject(this);
        // register this view to the presenter and start getting account info
        presenter.registerView(this);
        presenter.retrieveAccountInfo();
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAccountManagerInteractionListener) {
            mListener = (OnAccountManagerInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnAccountManagerInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onAccountInfoRetrieved(User user) {
        loadingProgressBar.setVisibility(View.INVISIBLE);
        if (mListener != null) {
            mListener.saveAccountInfo(user);
        }
    }

    @Override
    public void onAccountInfoError() {
        loadingProgressBar.setVisibility(View.INVISIBLE);
        if (mListener != null) {
            mListener.showAccountInfoError();
        }
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
    public interface OnAccountManagerInteractionListener {

        void saveAccountInfo(User user);

        void showAccountInfoError();
    }
}
