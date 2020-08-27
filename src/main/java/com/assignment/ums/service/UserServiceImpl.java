package com.assignment.ums.service;

import com.assignment.ums.DAO.UserDAO;
import com.assignment.ums.entity.UserActivityLog;
import com.assignment.ums.entity.UserLoginDetails;
import com.assignment.ums.model.admin.User;
import com.assignment.ums.model.login.LoginDTO;
import com.assignment.ums.model.login.LoginResponse;
import com.assignment.ums.model.user.create.Request;
import com.assignment.ums.model.user.create.UserResponse;
import com.assignment.ums.model.user.update.Response;
import com.assignment.ums.model.user.update.UpdateDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{
    @Autowired
    UserDAO userDAO;

    @Override
    public LoginResponse login(String username, String password) {
            UserLoginDetails user=userDAO.login(username,password);
            LoginResponse response= new LoginResponse();
            if(user!=null){
                response.setToken(user.getToken());
                response.setAdmin(user.isAdmin());
                response.setCode("SUCCESS");
                response.setMessage("Login Successfully");
            }else{
                response.setCode("UNAUTHORIZED");
                response.setMessage("Invalid Credentials/User not exists");
            }
            return response;
    }

    @Override
    public LoginDTO validateToken(String token) {
        UserLoginDetails user=userDAO.loginUsingToken(token);
        if(user==null){
            return null;
        }
        LoginDTO loginDetail=new LoginDTO();
        loginDetail.setAdmin(user.isAdmin());
        loginDetail.setToken(token);
        loginDetail.setUserId(user.getUserId());
        return  loginDetail;
    }

    @Override
    public UserResponse createUser(Request request) {
        com.assignment.ums.entity.User userEntity=userDAO.getUserDetails(request.getUserName());
        UserResponse response=new UserResponse();
        if(userEntity!=null){
            response.setCode("Error");
            response.setMessage("User with username already exists");
            return  response;
        }
        UserLoginDetails details=userDAO.createUser(request);
        response.setPassword(details.getPassword());
        response.setUserName(details.getUserName());
        response.setMessage("User created successfully");
        response.setCode("Success");
        return response;
    }

    @Override
    public Response updatePassword(String oldPassword, String newPassword, String token) {
        int res=userDAO.updatePassword(oldPassword,newPassword,token);
        Response response=new Response();
        if(res==0){
            response.setCode("Error");
            response.setMessage("Old Password is incorrect");
        }else{
            response.setCode("SUCESS");
            response.setMessage("Password updated successfully");
        }
        return response;
    }

    @Override
    public Response updateDetails(UpdateDetails details) {
        int res=userDAO.edit(details);
        Response response=new Response();
        if(res==0){
            response.setCode("Error");
            response.setMessage("Internal serval error.Please try again later");
        }else{
            response.setCode("SUCESS");
            response.setMessage("details updated successfully");
        }
        return response;
    }

    @Override
    public List<String> getAllUsers() {
        List<UserLoginDetails> users= userDAO.getAllUsers();
        List<String> usernames=new ArrayList<>();
        for(UserLoginDetails details:users)
            usernames.add(details.getUserName());
       return usernames;
    }

    @Override
    public User getUserDetail(String userName) {
        User user=new User();
        com.assignment.ums.entity.User userEntity=userDAO.getUserDetails(userName);
        if(userEntity==null){
            user.setCode("No Data Available");
            user.setMessage("User does not exists");
            return user;
        }
        user.setDob(userEntity.getDateOfBirth());
        user.setEmail(userEntity.getEmail());
        user.setFirstName(userEntity.getFirstName());
        user.setLastName(userEntity.getLastName());
        user.setCode("SUCCESS");
        return user;
    }

    @Override
    public void persistActivity(UserActivityLog activityLog) {
        userDAO.persitActivity(activityLog);
    }
}
