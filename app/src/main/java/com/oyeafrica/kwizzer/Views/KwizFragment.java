package com.oyeafrica.kwizzer.Views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.oyeafrica.kwizzer.Models.Kwiz;
import com.oyeafrica.kwizzer.Models.Question;
import com.oyeafrica.kwizzer.Models.User;
import com.oyeafrica.kwizzer.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class KwizFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "KwizFragment";
    FirebaseFirestore firebaseFirestore;
    TextView quiz_title,fetching, feedback,timeLeft,number;
    private List<Question> questions = new ArrayList<>();
    private List<Question> finalList = new ArrayList<>();
    Button next, option_a,option_b,option_c;
    ProgressBar progressBar;
    CountDownTimer countDownTimer;
    private Boolean canAnswer = false;
    private int currentQuestion = 0;
    private int correct =0;
    private int wrong = 0;
    private int not_answered;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String uid;
    Kwiz kwiz;
    private ImageButton close;





    public KwizFragment() {
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


        return inflater.inflate(R.layout.fragment_kwiz, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottom_nav);
        bottomNavigationView.setVisibility(View.GONE);
        firebaseFirestore = FirebaseFirestore.getInstance();
        quiz_title = view.findViewById(R.id.quiz_title);
        questions.clear();
        finalList.clear();
        option_a = view.findViewById(R.id.quiz_question_option_one);
        option_b = view.findViewById(R.id.quiz_question_option_two);
        option_c = view.findViewById(R.id.quiz_question_option_three);
        next = view.findViewById(R.id.next_question);
        close = view.findViewById(R.id.quiz_close_button);
        progressBar = view.findViewById(R.id.question_progress);
        timeLeft = view.findViewById(R.id.question_progress_number);
        number = view.findViewById(R.id.question_number);
        fetching = view.findViewById(R.id.quiz_question);
        feedback = view.findViewById(R.id.question_feedback);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        assert firebaseUser != null;
        uid = firebaseUser.getUid();

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).popBackStack();
            }
        });



        assert getArguments() != null;
        kwiz = (Kwiz) getArguments().getSerializable("kwiz");
        if(kwiz!=null){
            CollectionReference questionRef = firebaseFirestore.collection("Kwiz").document(kwiz.getId()).collection("Question");
            questionRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        questions =Objects.requireNonNull(task.getResult()).toObjects(Question.class);
                        getRandomQuestions(kwiz);

                        showQuestion();

                        Log.d(TAG, "onComplete: " + questions.get(0).getQuestion());
                        

                    }
                    else {
                       quiz_title.setText("Error Loading Quiz");
                    }

                }
            });
        }
        option_a.setOnClickListener(this);
        option_b.setOnClickListener(this);
        option_c.setOnClickListener(this);



    }

    private void showQuestion() {
      quiz_title.setText("Question loaded");
        enableOptions();
        loadQuestion(0);
    }

    private void loadQuestion(int i) {
        currentQuestion = i;
        Question question = finalList.get(i);
        fetching.setText(question.getQuestion());
        number.setText(String.valueOf(i+1));

        option_a.setText(question.getA());
        option_b.setText(question.getB());
        option_c.setText(question.getC());

        canAnswer = true;

        startCountDown(question.getTime());

    }

    private void startCountDown(final long time) {
        timeLeft.setText(String.valueOf(time));
        progressBar.setVisibility(View.VISIBLE);
      countDownTimer=  new CountDownTimer(time*1000,10){

            @Override
            public void onTick(long millisUntilFinished) {
                  timeLeft.setText(millisUntilFinished/1000+"");
                  long progressVal = millisUntilFinished/(time*10);
                  int progress = (int) progressVal;
                  progressBar.setProgress(progress);
            }

            @Override
            public void onFinish() {
                canAnswer = false;
                not_answered = 0;
                feedback.setText("Time has Ran out");
                showNext();

            }
        };
      countDownTimer.start();
    }

    private void enableOptions() {
        option_a.setVisibility(View.VISIBLE);
        option_b.setVisibility(View.VISIBLE);
        option_c.setVisibility(View.VISIBLE);

        option_a.setEnabled(true);
        option_b.setEnabled(true);
        option_c.setEnabled(true);

        next.setVisibility(View.INVISIBLE);
        next.setEnabled(false);
        feedback.setVisibility(View.INVISIBLE);
    }

    private void getRandomQuestions(Kwiz kwiz) {
       for (int i = 0; i < kwiz.getLength();i++){
           int randomIndex = getRadomIndex(questions.size(),0);
           finalList.add(questions.get(randomIndex));
           questions.remove(randomIndex);
           Log.d(TAG, "getRandomQuestions: i " + finalList.get(i).getQuestion());
       }
    }

    private static int getRadomIndex(int size, int i) {
        return ((int) (Math.random() *(size-i))) + i ;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.quiz_question_option_one:
                choosenAnswer(option_a);
                break;
            case R.id.quiz_question_option_two:
                choosenAnswer(option_b);
                break;
            case R.id.quiz_question_option_three:
                choosenAnswer(option_c);
                break;
            case R.id.next_question:
                 if(currentQuestion< finalList.size()-1){
                     currentQuestion++;
                     loadQuestion(currentQuestion);
                     resetButton();
                 }
                 else {
                     next.setVisibility(View.GONE);
                     next.setEnabled(false);
                     submitResults();
                 }



        }

    }

    private void submitResults() {

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("correct", correct);
        resultMap.put("wrong", wrong);
        resultMap.put("not_answered", not_answered);

        firebaseFirestore.collection("Kwiz").document(kwiz.getId()).collection("Results")
                .document(uid).set(resultMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    firebaseFirestore.collection("Users").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful()){
                                User user = Objects.requireNonNull(task.getResult()).toObject(User.class);
                                assert user != null;
                                long score = user.getScore() + correct;

                                HashMap<String, Object> scoremap = new HashMap<>();
                                scoremap.put("score", score);
                                firebaseFirestore.collection("Users").document(uid).update(scoremap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                           // Toast.makeText(getContext(), "Set", Toast.LENGTH_LONG).show();
                                            Bundle bundle = new Bundle();
                                            bundle.putInt("correct", correct);
                                            bundle.putInt("wrong", wrong);
                                            bundle.putInt("unAnswered", not_answered);

                                            Navigation.findNavController(requireView()).navigate(R.id.resultFragment, bundle);
                                        }
                                        else {
                                            Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
                                            Navigation.findNavController(requireView()).navigate(R.id.kwizDetailFragment);
                                        }
                                    }
                                });

                            }
                            else {
                                Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
                                Navigation.findNavController(requireView()).navigate(R.id.kwizDetailFragment);
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
                    Navigation.findNavController(requireView()).navigate(R.id.kwizDetailFragment);
                }
            }
        });
    }

    private void resetButton() {
        option_a.setBackground(getResources().getDrawable(R.drawable.rounded_blue_stroke_background,null));
        option_b.setBackground(getResources().getDrawable(R.drawable.rounded_blue_stroke_background,null));
        option_c.setBackground(getResources().getDrawable(R.drawable.rounded_blue_stroke_background,null));

        feedback.setVisibility(View.INVISIBLE);
        next.setEnabled(false);
        next.setVisibility(View.INVISIBLE);
        feedback.setTextColor(getResources().getColor(R.color.colorAccent));
    }

    private void choosenAnswer(Button option) {
        if(canAnswer){
            if(finalList.get(currentQuestion).getAnswer().contentEquals(option.getText())){

                correct++;

                option.setBackground(getResources().getDrawable(R.drawable.rounded_green_stroke_background,null));


                Log.d(TAG, "choosenAnswer: correct");
                feedback.setText("Correct answer!");
                feedback.setTextColor(getResources().getColor(R.color.colorGreen));

            }
            else {
                wrong++;
                option.setBackground(getResources().getDrawable(R.drawable.rounded_orange_stroke_background,null));
                Log.d(TAG, "choosenAnswer: wrong");
                feedback.setText("Wrong Answer!\n\nCorrect answer is: " + finalList.get(currentQuestion).getAnswer());
                feedback.setTextColor(getResources().getColor(R.color.colorOrange));
            }
            canAnswer= false;

            countDownTimer.cancel();

            showNext();
        }
    }

    private void showNext() {

        if(currentQuestion < finalList.size()){
            next.setVisibility(View.VISIBLE);
            next.setEnabled(true);
            feedback.setVisibility(View.VISIBLE);
            next.setOnClickListener(this);
        }
        else {
            next.setText("Show Results");
        }


    }
}