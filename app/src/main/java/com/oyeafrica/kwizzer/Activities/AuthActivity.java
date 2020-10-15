package com.oyeafrica.kwizzer.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.oyeafrica.kwizzer.Models.User;
import com.oyeafrica.kwizzer.R;

import java.util.Objects;

public class AuthActivity extends AppCompatActivity {
    private static final String TAG = "AuthActivity";
    private static final int RC_SIGN_IN = 1 ;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser firebaseUser;

    private FirebaseAuth mAuth;
    LinearLayout signin;
    ProgressBar progressBar;
    String status = " ";
    TextView viewStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        signin = findViewById(R.id.signin);
        progressBar = findViewById(R.id.progressBar);
        viewStatus = findViewById(R.id.status);
        viewStatus.setText(status);




        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client))
                .requestEmail()
                .build();


        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);


        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        if(firebaseUser!=null){
            gotomain();
        }

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signinWithGoogle();
            }
        });
        checkFirstRun();

    }



    private void signinWithGoogle() {
        startSignupUi();
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void startSignupUi() {
        status = "Signing in...";
        viewStatus.setText(status);
        signin.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

    }

    private void checkFirstRun() {
        boolean isfirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isFirstRun", true);
        if(isfirstRun){
            Log.d(TAG, "checkFirstRun: " + true);
            startOnboarding();
        }
        getSharedPreferences("PREFERENCE",MODE_PRIVATE).edit().putBoolean("isFirstRun",false).apply();
    }

    private void startOnboarding() {
        Intent Onboarding = new Intent(this, com.oyeafrica.kwizzer.Activities.Onboarding.class);
        startActivity(Onboarding);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN&& resultCode == RESULT_OK) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
        else {
            failedSignUpUi();
        }
    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            firebaseUser = mAuth.getCurrentUser();
                            final User user = new User();
                            user.setUsername(account.getDisplayName());
                            user.setImage(Objects.requireNonNull(account.getPhotoUrl()).toString());
                            assert firebaseUser != null;
                            user.setUid(firebaseUser.getUid());
                            user.setScore(0);
                            //check user exists
                            DocumentReference documentReference = db.collection("Users").document(user.getUid());
                            documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if(task.isSuccessful()){
                                        DocumentSnapshot documentSnapshot = task.getResult();
                                        assert documentSnapshot != null;
                                        if(documentSnapshot.exists()){

                                            Toast.makeText(getApplicationContext(), "Welcome Back",Toast.LENGTH_LONG).show();
                                            gotomain();

                                        }
                                        else {
                                            db.collection("Users").document(user.getUid()).set(user)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if(task.isSuccessful()){
                                                                gotomain();
                                                            }
                                                            else {
                                                                firebaseUser= null;
                                                                Log.d(TAG, "onComplete: task failed" );
                                                                failedSignUpUi();
                                                            }

                                                        }
                                                    });
                                        }

                                    }
                                    else {
                                        Log.d(TAG, "onComplete: " + task.getException());
                                        firebaseUser= null;
                                        Snackbar.make(Objects.requireNonNull(getCurrentFocus()), "Account creation failed.", Snackbar.LENGTH_SHORT).show();
                                    }

                                }
                            });






                        } else {
                            // If sign in fails, display a message to the user.
                            failedSignUpUi();
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Snackbar.make(Objects.requireNonNull(getCurrentFocus()), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }

    private void gotomain() {
        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);
        finish();
    }

    private void failedSignUpUi() {
        status = "Something went wrong!";
        viewStatus.setText(status);
        progressBar.setVisibility(View.GONE);
        signin.setVisibility(View.VISIBLE);

    }
}