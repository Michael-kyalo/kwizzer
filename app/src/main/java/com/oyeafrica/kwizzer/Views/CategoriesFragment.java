package com.oyeafrica.kwizzer.Views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.oyeafrica.kwizzer.Models.Category;
import com.oyeafrica.kwizzer.R;
import com.oyeafrica.kwizzer.ViewModels.CategoryViewModel;
import com.oyeafrica.kwizzer.utils.CategoriesAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class CategoriesFragment extends Fragment {
    private static final String TAG = "CategoriesFragment";

   RecyclerView recyclerView;
   CategoriesAdapter categoriesAdapter;
   CategoryViewModel categoryViewModel;
   ProgressBar progressBar;
   Animation fade_in, fade_out;

    public CategoriesFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kwiz_categories, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottom_nav);
        bottomNavigationView.setVisibility(View.VISIBLE);
        categoriesAdapter = new CategoriesAdapter(getContext());
        progressBar = view.findViewById(R.id.progressbar);
        fade_in = AnimationUtils.loadAnimation(getContext(),R.anim.fade_in);
        fade_out = AnimationUtils.loadAnimation(getContext(),R.anim.fade_out);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView = view.findViewById(R.id.categories_rec);
        recyclerView.setAdapter(categoriesAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);



    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        categoryViewModel = new ViewModelProvider(requireActivity()).get(CategoryViewModel.class);
        categoryViewModel.getCategories();
        categoryViewModel.getCategoriesData().observe(getViewLifecycleOwner(), new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categoryList) {

                progressBar.startAnimation(fade_out);
                progressBar.setVisibility(View.INVISIBLE);
                recyclerView.startAnimation(fade_in);
                Log.d(TAG, "onChanged: " + categoryList.size());
                categoriesAdapter.submitList(categoryList);
            }
        });
    }
}