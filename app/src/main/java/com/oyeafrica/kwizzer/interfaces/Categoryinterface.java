package com.oyeafrica.kwizzer.interfaces;

import com.oyeafrica.kwizzer.Models.Category;

import java.util.List;

public interface Categoryinterface {
    void categoriesList(List<Category> categoryList);
    void categoriesError(Exception e);
}
