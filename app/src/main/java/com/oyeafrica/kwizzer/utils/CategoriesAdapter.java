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
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.oyeafrica.kwizzer.Models.Category;
import com.oyeafrica.kwizzer.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoriesAdapter extends ListAdapter<Category,CategoriesAdapter.ViewHolder> {
    private static final String TAG = "CategoriesAdapter";

   Handler handler;
   Context context;

    public CategoriesAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
    }
    public static final DiffUtil.ItemCallback<Category> DIFF_CALLBACK = new DiffUtil.ItemCallback<Category>() {
        @Override
        public boolean areItemsTheSame(@NonNull Category oldItem, @NonNull Category newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Category oldItem, @NonNull Category newItem) {
            return oldItem.getImage().equals(newItem.getImage())&&oldItem.getName().equals(newItem.getName())
                    &&oldItem.getDescription().equals(newItem.getDescription())&&oldItem.getVisibility().equals(newItem.getVisibility());
        }
    };



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_single_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Category category = getItem(position);

        Log.d(TAG, "onBindViewHolder: " + category.getImage());
        holder.title.setText(category.getName());
        holder.description.setText(category.getDescription());
        holder.open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("category", category.getName());
                Navigation.findNavController(v).navigate(R.id.kwizListFragmant, bundle);
            }
        });

      handler = new Handler(context.getMainLooper());
      new Thread(new Runnable() {
          @Override
          public void run() {
              handler.post(new Runnable() {
                  @Override
                  public void run() {
                      Picasso.get().load(category.getImage())
                              .fit()
                              .centerCrop()
                              .placeholder(R.drawable.auth_background)
                              .into(holder.imageView);
                      Log.d(TAG, "run: here" );
                  }
              });
          }
      }).start();


    }




    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView title;
        TextView description, length;
        Button open;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.quiz_image);
            title = itemView.findViewById(R.id.quiz_title);
            description = itemView.findViewById(R.id.quiz_description);
            length = itemView.findViewById(R.id.quiz_size);
            open = itemView.findViewById(R.id.quiz_button);
        }
    }
}
