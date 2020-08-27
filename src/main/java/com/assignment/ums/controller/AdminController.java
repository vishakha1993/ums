package com.assignment.ums.controller;

import com.assignment.ums.entity.UserActivityLog;
import com.assignment.ums.model.admin.GetAllUser;
import com.assignment.ums.model.admin.User;
import com.assignment.ums.model.login.LoginDTO;
import com.assignment.ums.model.service.ServiceResponse;
import com.assignment.ums.model.user.create.Request;
import com.assignment.ums.model.user.create.UserResponse;
import com.assignment.ums.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    @ResponseBody
    UserResponse createUser(@RequestBody  Request request){
        UserResponse response= new UserResponse();
        if(!validTokenForAdmin(request.getToken(),"create user")){
            response.setCode("Unauthorized Access");
            response.setMessage("You are not authorized for this api");
            return  response;
        }
        response=userService.createUser(request);
        return  response;
    }

    @RequestMapping(value = "/getAllUsers", method = RequestMethod.GET)
    @ResponseBody
    GetAllUser getAllUser(@RequestParam(name="token",required = true) String token){
        GetAllUser response= new GetAllUser();
        if(!validTokenForAdmin(token,"get all users")){
            response.setCode("Unauthorized Access");
            response.setMessage("You are not authorized for this api");
            return  response;
        }
        response.setUsers(userService.getAllUsers());
        return response;
    }

    @RequestMapping(value = "/getUserDetail", method = RequestMethod.GET)
    @ResponseBody
    User getUserDetail(@RequestParam(name = "username",required = true)String username, @RequestParam(name="token",required = true) String token){
        User response= new User();
        if(!validTokenForAdmin(token,"get user detail for user:"+username)){
            response.setCode("Unauthorized Access");
            response.setMessage("You are not authorized for this api");
            return  response;
        }
        response=userService.getUserDetail(username);
        return response;
    }

    boolean validTokenForAdmin(String token,String activity){
        LoginDTO loginDTO=userService.validateToken(token);
        if(loginDTO==null||!loginDTO.isAdmin()){
            return false;
        }
        UserActivityLog  userActivityLog=new UserActivityLog();
        userActivityLog.setActivity(activity);
        userActivityLog.setUserId(loginDTO.getUserId());
        userService.persistActivity(userActivityLog);
        return true;
    }

}
