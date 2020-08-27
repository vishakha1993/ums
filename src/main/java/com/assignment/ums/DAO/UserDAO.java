package com.assignment.ums.DAO;

import com.assignment.ums.entity.User;
import com.assignment.ums.entity.UserActivityLog;
import com.assignment.ums.entity.UserLoginDetails;
import com.assignment.ums.model.user.create.Request;
import com.assignment.ums.model.user.update.UpdateDetails;

import java.util.List;

public interface UserDAO {

    public Integer updatePassword(String oldPassword,String newPassword,String token);
    public Integer edit(UpdateDetails details);
    public UserLoginDetails login(String Username, String Password);
    public  UserLoginDetails loginUsingToken(String token);
    public  UserLoginDetails createUser(Request request);
    List<UserLoginDetails> getAllUsers();
    User getUserDetails(String userName);
    void persitActivity(UserActivityLog activityLog);
}
