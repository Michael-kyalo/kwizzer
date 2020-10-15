package com.oyeafrica.kwizzer.interfaces;

import com.oyeafrica.kwizzer.Models.Kwiz;

import java.util.List;

public interface KwizByCategoryInterface {
    void kwizListByCategory(List<Kwiz> kwizList);
    void kwizListByCategoryError(Exception e);
}
