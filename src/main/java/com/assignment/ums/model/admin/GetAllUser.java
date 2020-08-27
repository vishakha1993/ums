package com.assignment.ums.model.admin;

import com.assignment.ums.model.service.ServiceResponse;

import java.util.List;

public class GetAllUser extends ServiceResponse {
    private List<String> users;

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "GetAllUser{" +
                "users=" + users +
                '}';
    }
}
