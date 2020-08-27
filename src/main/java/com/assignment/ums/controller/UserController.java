package com.assignment.ums.controller;

import com.assignment.ums.entity.UserActivityLog;
import com.assignment.ums.model.login.LoginDTO;
import com.assignment.ums.model.user.create.Request;
import com.assignment.ums.model.user.create.UserResponse;
import com.assignment.ums.model.user.update.Response;
import com.assignment.ums.model.user.update.UpdateDetails;
import com.assignment.ums.service.UserService;
import com.assignment.ums.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/changePassword", method = RequestMethod.GET)
    @ResponseBody
    Response updatePassword(@RequestParam(name="oldPassword",required = true) String oldPassword, @RequestParam(name="newPassword",required = true) String newPassword,@RequestParam(name="token" ,required = true) String token){
        Response response= new Response();
        if(!validToken(token,"changing password")){
            response.setCode("Unauthorized Access");
            response.setMessage("You are not authorized for this api");
            return  response;
        }
        if(!Validator.validatePassword(newPassword)){
            response.setCode("Invalid Password");
            response.setMessage("Please set password with atleast 8 chars including 1 alphabet,1 number and 1 special char");
            return response;
        }
        return userService.updatePassword(oldPassword,newPassword,token);
    }

    @RequestMapping(value = "/updateDetails",method=RequestMethod.POST)
    @ResponseBody
    Response updateUserDetails(@RequestBody UpdateDetails details){
        Response response= new Response();
        if(!validToken(details.getToken(),"updating details "+details.toString())){
            response.setCode("Unauthorized Access");
            response.setMessage("You are not authorized for this api");
            return  response;
        }
        return userService.updateDetails(details);
    }

    boolean validToken(String token,String activity){
        LoginDTO loginDTO=userService.validateToken(token);
        if(loginDTO==null){
            return false;
        }
        UserActivityLog userActivityLog=new UserActivityLog();
        userActivityLog.setActivity(activity);
        userActivityLog.setUserId(loginDTO.getUserId());
        userService.persistActivity(userActivityLog);
        return true;
    }
}
