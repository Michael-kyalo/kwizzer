package com.oyeafrica.kwizzer.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.oyeafrica.kwizzer.Models.Kwiz;
import com.oyeafrica.kwizzer.Repositories.KwizByCategoryRepository;
import com.oyeafrica.kwizzer.interfaces.KwizByCategoryInterface;

import java.util.List;

public class KwizByCategoryViewModel extends ViewModel implements KwizByCategoryInterface {

    private MutableLiveData<List<Kwiz>> kwizlist = new MutableLiveData<>();

    public LiveData<List<Kwiz>> getKwizByCategoryData(){
        return kwizlist;
    }
    private KwizByCategoryRepository kwizByCategoryRepository = new KwizByCategoryRepository(this);

    @Override
    public void kwizListByCategory(List<Kwiz> kwizList) {
        kwizlist.setValue(kwizList);

    }

    @Override
    public void kwizListByCategoryError(Exception e) {

    }

    public void getKwizByCategory(String category){
        kwizByCategoryRepository.getKwizByCategory(category);
    }
}
