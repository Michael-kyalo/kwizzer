package com.oyeafrica.kwizzer.ViewModels;

import android.widget.ListAdapter;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.oyeafrica.kwizzer.Models.User;
import com.oyeafrica.kwizzer.Repositories.LeaderBoardRepository;
import com.oyeafrica.kwizzer.interfaces.LeaderBoardInterface;

import java.util.List;

public class LeaderBoardViewModel extends ViewModel implements LeaderBoardInterface {
    private MutableLiveData<List<User>> userList = new MutableLiveData<>();


    public LiveData<List<User>> getUserList(){
        return userList;
    }

    private LeaderBoardRepository leaderBoardRepository= new LeaderBoardRepository(this);

    @Override
    public void LeaderBoard(List<User> users) {
        userList.setValue(users);
    }

    @Override
    public void LeaderError(Exception e) {

    }


    public void getLeaders(){
        leaderBoardRepository.getLeaderBoard();
    }



}
