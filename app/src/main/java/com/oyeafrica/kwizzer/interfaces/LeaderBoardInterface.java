package com.oyeafrica.kwizzer.interfaces;

import com.oyeafrica.kwizzer.Models.User;

import java.util.List;

public interface LeaderBoardInterface {
    void LeaderBoard(List<User> users);
    void LeaderError(Exception e);

}
