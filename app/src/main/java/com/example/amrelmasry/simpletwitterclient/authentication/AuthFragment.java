package com.example.amrelmasry.simpletwitterclient.authentication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.amrelmasry.simpletwitterclient.R;
import com.example.amrelmasry.simpletwitterclient.SimpleClientApp;
import com.example.amrelmasry.simpletwitterclient.common.models.AccessToken;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/***
 * this fragment role is to complete 2 steps authentication process
 * first, it obtains RequestToken
 * Second, it obtains AccessToken
 */
public class AuthFragment extends Fragment implements AuthContract.View {

    private final String LOG_TAG = getClass().getSimpleName();
    @Inject
    AuthPresenter presenter;
    @BindView(R.id.login_button)
    Button loginButton;
    @BindView(R.id.auth_progress_bar)
    ProgressBar progressBar;

    private OnAuthFinishListener mListener;

    public AuthFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_auth, container, false);

        // Setup Butterknife
        ButterKnife.bind(this, view);

        // Inject this fragment to singleton AuthComponent
        SimpleClientApp simpleClientApp = (SimpleClientApp) getActivity().getApplication();
        simpleClientApp.getAuthComponent().inject(this);

        // Register this view for AuthPresenter
        presenter.registerView(this);
        return view;
    }

    @OnClick(R.id.login_button)
    public void login() {
        loginButton.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        presenter.retrieveRequestToken();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAuthFinishListener) {
            mListener = (OnAuthFinishListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnAuthFinishListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onRequestTokenRetrieved(String url) {
        // open browser for Authentication
        if (mListener != null) {
            mListener.openBrowserForAuth(url);
        }
    }


    @Override
    public void onAccessTokenRetrieved(AccessToken accessToken) {
        // save access token in SharedPreferences
        if (mListener != null && !accessToken.isEmpty()) {
            mListener.saveAccessToken(accessToken);
        }
    }

    @Override
    public void onRequestTokenError(Throwable throwable) {
        // failed to authenticate
        if (mListener != null) {
            mListener.showAuthError();
        }
        Log.e(LOG_TAG, throwable.getMessage());
    }

    @Override
    public void onAccessTokenError(Throwable throwable) {
        // failed to authenticate
        if (mListener != null) {
            mListener.showAuthError();
        }
        Log.e(LOG_TAG, throwable.getMessage());
    }

    public void onCallbackUrlReceived(Uri uri) {
        presenter.retrieveAccessToken(uri);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnAuthFinishListener {

        void openBrowserForAuth(String url);

        void showAuthError();

        void saveAccessToken(AccessToken accessToken);
    }
}
