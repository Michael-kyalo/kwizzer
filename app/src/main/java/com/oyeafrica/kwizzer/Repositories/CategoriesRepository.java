package com.oyeafrica.kwizzer.Repositories;

import android.app.DownloadManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.oyeafrica.kwizzer.Models.Category;
import com.oyeafrica.kwizzer.interfaces.Categoryinterface;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class CategoriesRepository {
    private Categoryinterface categoryinterface;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference categoryRef = firebaseFirestore.collection("Categories");

    public CategoriesRepository(Categoryinterface categoryinterface) {
        this.categoryinterface = categoryinterface;
    }

   public void getCategories(){
       Query query = categoryRef.whereEqualTo("visibility", "public");
       query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
         @Override
         public void onComplete(@NonNull Task<QuerySnapshot> task) {
             if(task.isSuccessful()){
                 categoryinterface.categoriesList(Objects.requireNonNull(task.getResult()).toObjects(Category.class));
             }
             else {
                 categoryinterface.categoriesError(task.getException());

             }
         }
     });
   }
}
