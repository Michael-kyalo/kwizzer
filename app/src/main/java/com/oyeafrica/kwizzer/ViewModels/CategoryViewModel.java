package com.oyeafrica.kwizzer.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.oyeafrica.kwizzer.Models.Category;
import com.oyeafrica.kwizzer.Repositories.CategoriesRepository;
import com.oyeafrica.kwizzer.interfaces.Categoryinterface;

import java.util.List;

public class CategoryViewModel extends ViewModel implements Categoryinterface {

    private MutableLiveData<List<Category>> categoriesData= new MutableLiveData<>();

    public LiveData<List<Category>> getCategoriesData() {
        return categoriesData;
    }
    private CategoriesRepository categoriesRepository = new CategoriesRepository(this);

    public CategoryViewModel() {
    }

    @Override
    public void categoriesList(List<Category> categoryList) {
         categoriesData.setValue(categoryList);
    }

    @Override
    public void categoriesError(Exception e) {

    }
    public void getCategories(){
        categoriesRepository.getCategories();
    }
}
