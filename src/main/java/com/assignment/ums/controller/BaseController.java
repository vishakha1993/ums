package com.assignment.ums.controller;

import com.assignment.ums.model.login.LoginResponse;
import com.assignment.ums.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.print.DocFlavor;

@RestController
public class BaseController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    LoginResponse login(@RequestParam(value="username",required = true) String userName, @RequestParam(value="password",required = true) String password){
        return userService.login(userName,password);
    }
}
