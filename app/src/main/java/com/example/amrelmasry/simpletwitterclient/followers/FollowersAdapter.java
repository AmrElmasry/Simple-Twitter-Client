package com.example.amrelmasry.simpletwitterclient.followers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.amrelmasry.simpletwitterclient.R;
import com.example.amrelmasry.simpletwitterclient.common.models.User;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FollowersAdapter extends RecyclerView.Adapter<FollowersAdapter.ViewHolder> {

    private List<User> mFollowers;
    private Context mContext;

    public FollowersAdapter(List<User> followers, Context mContext) {
        this.mFollowers = followers;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the item layout
        View followerView = inflater.inflate(R.layout.follower_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(followerView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        User follower = mFollowers.get(position);
        viewHolder.nameTextView.setText(follower.getFullName());
        viewHolder.bioTextView.setText(follower.getBio());
        viewHolder.screenNameTextView.setText("@" + follower.getScreenName());
        Picasso.with(mContext).load(follower.getBiggerProfileImageUrl()).into(viewHolder.profileImageView);
    }

    @Override
    public int getItemCount() {
        return mFollowers.size();
    }

    public void addFollowers(List<User> followers) {
        mFollowers.addAll(followers);
    }

    public User getFollower(int position) {
        return mFollowers.get(position);
    }

    public void clear() {
        mFollowers.clear();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.follower_full_name)
        TextView nameTextView;
        @BindView(R.id.follower_screen_name)
        TextView screenNameTextView;
        @BindView(R.id.follower_bio)
        TextView bioTextView;
        @BindView(R.id.follower_profile_image)
        ImageView profileImageView;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
