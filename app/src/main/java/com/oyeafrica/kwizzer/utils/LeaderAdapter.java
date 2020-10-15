package com.oyeafrica.kwizzer.utils;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.oyeafrica.kwizzer.Models.Kwiz;
import com.oyeafrica.kwizzer.Models.User;
import com.oyeafrica.kwizzer.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class LeaderAdapter extends RecyclerView.Adapter<LeaderAdapter.ViewHolder> {
    private static final String TAG = "LeaderAdapter";
   private List<User> userList;
   Handler handler;
   Context context;

    public LeaderAdapter(Context context) {
        this.context = context;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_leader_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final User user = userList.get(position);

        holder.username.setText(user.getUsername());
        holder.position.setText("Position: " +String.valueOf(position+1));
        holder.points.setText(String.valueOf(user.getScore()) +" points");
        handler = new Handler(context.getMainLooper());
        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "run: here");
                        Picasso.get().load(user.getImage()).fit().centerCrop().into(holder.profile);
                    }
                });
            }
        }).start();


    }

    @Override
    public int getItemCount() {
        return userList==null ?  0 :userList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
          TextView username, points, position;
          CircleImageView profile;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
          username = itemView.findViewById(R.id.username);
          points = itemView.findViewById(R.id.points);
          position = itemView.findViewById(R.id.number);

          profile = itemView.findViewById(R.id.profileImage);
        }
    }
}
