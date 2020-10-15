package com.oyeafrica.kwizzer.Views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.oyeafrica.kwizzer.R;


public class ResultFragment extends Fragment {
    TextView correct_text, wrong_text, missing_text, score_text,score_percent;
    ProgressBar progressBar;
    Button home;


    public ResultFragment() {
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
        return inflater.inflate(R.layout.fragment_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        assert bundle != null;
        int wrong = bundle.getInt("wrong", 0);
        int correct = bundle.getInt("correct",0);
        int unanswered = bundle.getInt("unAnswered", 0);

        progressBar = view.findViewById(R.id.progressBar2);
        correct_text = view.findViewById(R.id.results_correct);
        wrong_text = view.findViewById(R.id.results_wrong);
        missing_text = view.findViewById(R.id.results_missing);
        score_text = view.findViewById(R.id.score_text);
        score_percent = view.findViewById(R.id.score_percent);
        home = view.findViewById(R.id.button);

        correct_text.setText(String.valueOf(correct));
        wrong_text.setText(String.valueOf(wrong));
        missing_text.setText(String.valueOf(unanswered));

        int total = wrong + correct + unanswered;
        int progress= (correct * 100)/total;
        progressBar.setProgress(progress);

        score_percent.setText(String.valueOf(progress));
        score_text.setText(String.valueOf(correct));


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.categoriesFragment);
            }
        });
    }
}