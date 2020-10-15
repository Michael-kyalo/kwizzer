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

import com.oyeafrica.kwizzer.Models.Category;
import com.oyeafrica.kwizzer.Models.Kwiz;
import com.oyeafrica.kwizzer.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class KwizAdapter extends RecyclerView.Adapter<KwizAdapter.ViewHolder> {
    private static final String TAG = "CategoriesAdapter";
   private List<Kwiz> kwizList;
   Handler handler;
   Context context;

    public KwizAdapter(Context context) {
        this.context = context;
    }

    public void setKwizList(List<Kwiz> kwizList) {
        this.kwizList = kwizList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_single_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Kwiz kwiz = kwizList.get(position);

        Log.d(TAG, "onBindViewHolder: " + kwiz.getImage());
        holder.title.setText(kwiz.getTitle());
        holder.description.setText(kwiz.getDescription());
        String size = String.valueOf(kwiz.getLength()) + " questions";
        holder.length.setText(size);
        holder.difficulty.setText(kwiz.getDifficulty());




      handler = new Handler(context.getMainLooper());
      new Thread(new Runnable() {
          @Override
          public void run() {
              handler.post(new Runnable() {
                  @Override
                  public void run() {
                      Picasso.get().load(kwiz.getImage())
                              .fit()
                              .centerCrop()
                              .placeholder(R.drawable.auth_background)
                              .into(holder.imageView);
                      Log.d(TAG, "run: here" );
                  }
              });
          }
      }).start();
      holder.open.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Bundle bundle = new Bundle();
              bundle.putSerializable("kwiz", kwiz );
              Navigation.findNavController(v).navigate(R.id.kwizDetailFragment, bundle);
          }
      });


    }

    @Override
    public int getItemCount() {
        return kwizList==null ?  0 :kwizList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView title;
        TextView description, length,difficulty;
        Button open;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.quiz_image);
            title = itemView.findViewById(R.id.quiz_title);
            description = itemView.findViewById(R.id.quiz_description);
            length = itemView.findViewById(R.id.quiz_size);
            open = itemView.findViewById(R.id.quiz_button);
            difficulty = itemView.findViewById(R.id.quiz_difficulty);
        }
    }
}
