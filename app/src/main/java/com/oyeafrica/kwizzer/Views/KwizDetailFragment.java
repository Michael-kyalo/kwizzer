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
import android.widget.ImageView;
import android.widget.TextView;

import com.oyeafrica.kwizzer.Models.Kwiz;
import com.oyeafrica.kwizzer.R;
import com.squareup.picasso.Picasso;

public class KwizDetailFragment extends Fragment {
    private static final String TAG = "KwizDetailFragment";
    ImageView quiz_image;
    TextView title,description,difficulty,size;
    Button start;


    public KwizDetailFragment() {
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
        return inflater.inflate(R.layout.fragment_kwiz_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ImageView back = view.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).popBackStack();
            }
        });
        quiz_image = view.findViewById(R.id.quiz_image);
        title = view.findViewById(R.id.quiz_title);
        description = view.findViewById(R.id.quiz_description);
        difficulty= view.findViewById(R.id.quiz_size);
        size = view.findViewById(R.id.quiz_questions);
        start = view.findViewById(R.id.start_button);


        assert getArguments() != null;

        final Kwiz kwiz = (Kwiz) getArguments().getSerializable("kwiz");
        if(kwiz!=null){
            Picasso.get().load(kwiz.getImage()).fit().centerCrop().into(quiz_image);
            title.setText(kwiz.getTitle());
            description.setText(kwiz.getDescription());
            difficulty.setText(kwiz.getDifficulty());
            size.setText(String.valueOf(kwiz.getLength()));



            start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("kwiz", kwiz);

                    Navigation.findNavController(view).navigate(R.id.kwizFragment,bundle);
                }
            });

        }

    }
}