package com.assignment.ums.service;

import com.assignment.ums.entity.UserActivityLog;
import com.assignment.ums.model.admin.User;
import com.assignment.ums.model.login.LoginDTO;
import com.assignment.ums.model.login.LoginResponse;
import com.assignment.ums.model.user.create.Request;
import com.assignment.ums.model.user.create.UserResponse;
import com.assignment.ums.model.user.update.Response;
import com.assignment.ums.model.user.update.UpdateDetails;

import java.util.List;

public interface UserService {
    LoginResponse login(String username,String password);
    LoginDTO validateToken(String token);
    UserResponse createUser(Request request);
    Response updatePassword(String oldPassword,String newPassword,String token);
    Response updateDetails(UpdateDetails details);
    List<String> getAllUsers();
    User getUserDetail(String userName);
    void persistActivity(UserActivityLog activityLog);
}
