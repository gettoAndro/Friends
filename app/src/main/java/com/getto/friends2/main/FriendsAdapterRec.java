package com.getto.friends2.main;


import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.getto.friends2.R;
import com.getto.friends2.User;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Getto on 24.06.2017.
 */

public class FriendsAdapterRec  extends RecyclerView.Adapter<FriendsAdapterRec.FriendViewHolder>{

    private MainView mainView;
    private List<User> userList;
    private Context context;
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }


    static class FriendViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{

        public ImageView imageView;
        public TextView textName;

        public FriendViewHolder(View itemView, final MainView mainView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image_friend);
            textName = (TextView) itemView.findViewById(R.id.name_friend);
            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mainView != null)
                        mainView.showInfoFriend(getAdapterPosition());
                }
            });
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(Menu.NONE, R.id.ctx_item_del, Menu.NONE, R.string.delete_friend);
        }
    }

    public FriendsAdapterRec(Context context,MainView mainView) {
        this.context = context;
        this.mainView = mainView;
        this.userList = new ArrayList<>();
    }

    public void setFriend(@NonNull List<User> users) {
        userList = users;
        notifyDataSetChanged();
    }

    public User getFriend(int position){
        return userList.get(position);
    }

    @Override
    public FriendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friends, parent, false);
        return new FriendViewHolder(view, mainView);
    }

    @Override
    public void onBindViewHolder(final FriendViewHolder holder, int position) {
        User user = userList.get(position);
        File file = new File(user.getImage());
        Picasso.with(context).load(file).into(holder.imageView);
        holder.textName.setText(user.getName());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setPosition(holder.getAdapterPosition());
                return false;
            }
        });
    }

    @Override
    public void onViewRecycled(FriendViewHolder holder) {
        holder.itemView.setOnLongClickListener(null);
        super.onViewRecycled(holder);

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
