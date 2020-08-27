package com.assignment.ums.model.admin;

import com.assignment.ums.model.service.ServiceResponse;

import java.util.Date;


public class User extends ServiceResponse {
    private String email;
    private String firstName;
    private String lastName;
    private Date dob;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

}
