package com.oyeafrica.kwizzer.Repositories;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.oyeafrica.kwizzer.Models.Kwiz;
import com.oyeafrica.kwizzer.interfaces.KwizByCategoryInterface;

import java.util.Objects;

public class KwizByCategoryRepository {
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference kwizref = firebaseFirestore.collection("Kwiz");
    private KwizByCategoryInterface kwizByCategoryInterface;

    public KwizByCategoryRepository(KwizByCategoryInterface kwizByCategoryInterface) {
        this.kwizByCategoryInterface = kwizByCategoryInterface;
    }

    public void getKwizByCategory(String category){
        Query query = kwizref.whereEqualTo("category", category)
                .whereEqualTo("visibility", "public")
                .orderBy("date", Query.Direction.DESCENDING);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    kwizByCategoryInterface.kwizListByCategory(Objects.requireNonNull(task.getResult()).toObjects(Kwiz.class));
                }else {
                    kwizByCategoryInterface.kwizListByCategoryError(task.getException());

                }
            }
        });
    }
}
