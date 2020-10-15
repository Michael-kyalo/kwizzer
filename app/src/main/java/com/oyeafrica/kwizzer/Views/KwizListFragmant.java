package com.oyeafrica.kwizzer.Views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.oyeafrica.kwizzer.Models.Kwiz;
import com.oyeafrica.kwizzer.R;
import com.oyeafrica.kwizzer.ViewModels.KwizByCategoryViewModel;
import com.oyeafrica.kwizzer.utils.KwizAdapter;

import java.util.List;
import java.util.Objects;

public class KwizListFragmant extends Fragment {
    private static final String TAG = "KwizListFragmant";
    RecyclerView recyclerView;
    KwizAdapter kwizAdapter;
    KwizByCategoryViewModel kwizByCategoryViewModel;
    ProgressBar progressBar;
    Animation fade_in,fade_out;
    TextView textView;


    public KwizListFragmant() {
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
        View view =  inflater.inflate(R.layout.fragment_kwiz_list_fragmant, container, false);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottom_nav);
        bottomNavigationView.setVisibility(View.GONE);
        kwizAdapter = new KwizAdapter(getContext());
        fade_in = AnimationUtils.loadAnimation(getContext(),R.anim.fade_in);
        fade_out = AnimationUtils.loadAnimation(getContext(),R.anim.fade_out);
        textView = view.findViewById(R.id.category_text);
        progressBar = view.findViewById(R.id.progressBar);

        ImageView back = view.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).popBackStack();
            }
        });
        recyclerView = view.findViewById(R.id.quiz_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(kwizAdapter);

        kwizByCategoryViewModel = new ViewModelProvider(requireActivity()).get(KwizByCategoryViewModel.class);
        assert getArguments() != null;
        String category = getArguments().getString("category");

        if(category!=null){
            textView.setText(category);
            kwizByCategoryViewModel.getKwizByCategory(category);
            kwizByCategoryViewModel.getKwizByCategoryData().observe(getViewLifecycleOwner(), new Observer<List<Kwiz>>() {
                @Override
                public void onChanged(List<Kwiz> kwizList) {
                    if(!kwizList.isEmpty()) {
                        kwizAdapter.setKwizList(kwizList);
                        progressBar.startAnimation(fade_out);
                        recyclerView.startAnimation(fade_in);
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                    else {
                        Toast.makeText(getContext(),"No Kwizz in this Category", Toast.LENGTH_SHORT).show();

                        Navigation.findNavController(requireView()).popBackStack();
                    }

                }
            });
        }

    }
}