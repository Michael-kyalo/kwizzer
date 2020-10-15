package com.oyeafrica.kwizzer.Repositories;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.oyeafrica.kwizzer.Models.User;
import com.oyeafrica.kwizzer.interfaces.LeaderBoardInterface;

import java.util.Objects;

public class LeaderBoardRepository {
    private LeaderBoardInterface leaderBoardInterface;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference categoryRef = firebaseFirestore.collection("Users");

    public LeaderBoardRepository(LeaderBoardInterface leaderBoardInterface) {
        this.leaderBoardInterface = leaderBoardInterface;
    }
   public void getLeaderBoard(){
       Query query= categoryRef.orderBy("score", Query.Direction.DESCENDING).limit(100);
       query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
           @Override
           public void onComplete(@NonNull Task<QuerySnapshot> task) {
               if(task.isSuccessful()){
                   leaderBoardInterface.LeaderBoard(Objects.requireNonNull(task.getResult()).toObjects(User.class));
               }
               else {
                   leaderBoardInterface.LeaderError(task.getException());
               }
           }
       });

    }

}
