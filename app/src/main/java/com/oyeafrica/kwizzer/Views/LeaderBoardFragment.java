package com.oyeafrica.kwizzer.Views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.oyeafrica.kwizzer.Models.User;
import com.oyeafrica.kwizzer.R;
import com.oyeafrica.kwizzer.ViewModels.LeaderBoardViewModel;
import com.oyeafrica.kwizzer.utils.LeaderAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class LeaderBoardFragment extends Fragment {
    private static final String TAG = "LeaderBoardFragment";
    private LeaderBoardViewModel leaderBoardViewModel;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String userId;

    private LeaderAdapter leaderAdapter;
    Animation fade_in, fade_out;
    Handler handler;

    public LeaderBoardFragment() {
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
        return inflater.inflate(R.layout.fragment_leader_board, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottom_nav);
        bottomNavigationView.setVisibility(View.VISIBLE);
        recyclerView = view.findViewById(R.id.leader_recycler);



        progressBar = view.findViewById(R.id.progressBar);

        leaderAdapter = new LeaderAdapter(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(leaderAdapter);
        fade_in = AnimationUtils.loadAnimation(getContext(),R.anim.fade_in);
        fade_out = AnimationUtils.loadAnimation(getContext(),R.anim.fade_out);
        handler = new Handler(requireContext().getMainLooper());
        userId = user.getUid();


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        leaderBoardViewModel = new ViewModelProvider(requireActivity()).get(LeaderBoardViewModel.class);




        leaderBoardViewModel.getLeaders();

        leaderBoardViewModel.getUserList().observe(getViewLifecycleOwner(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                progressBar.startAnimation(fade_out);
                progressBar.setVisibility(View.INVISIBLE);
                recyclerView.startAnimation(fade_in);



                leaderAdapter.setUserList(users);

                for(int i = 0; i<users.size();i++){
                    if(users.get(i).getUid().equals(userId)){

                        Toast.makeText(getContext(),"Congratulations\n you are position " + (i + 1),Toast.LENGTH_LONG ).show();

                    }

                }





            }
        });



    }

}