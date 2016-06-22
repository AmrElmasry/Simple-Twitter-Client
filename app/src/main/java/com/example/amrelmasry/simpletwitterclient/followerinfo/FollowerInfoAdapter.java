package com.example.amrelmasry.simpletwitterclient.followerinfo;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.amrelmasry.simpletwitterclient.R;
import com.example.amrelmasry.simpletwitterclient.common.models.Tweet;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FollowerInfoAdapter extends RecyclerView.Adapter<FollowerInfoAdapter.ViewHolder> {

    private List<Tweet> mTweets;

    public FollowerInfoAdapter(List<Tweet> mTweets) {
        this.mTweets = mTweets;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the item layout
        View tweetView = inflater.inflate(R.layout.tweet_item, parent, false);
        return new ViewHolder(tweetView);
    }
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Tweet tweet = mTweets.get(position);
        viewHolder.tweetText.setText(tweet.getText());
    }

    @Override
    public int getItemCount() {
        return mTweets.size();
    }

    public void addTweets(List<Tweet> followers) {
        mTweets.addAll(followers);
    }

    public Tweet getTweet(int position) {
        return mTweets.get(position);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tweet_text)
        TextView tweetText;



        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
