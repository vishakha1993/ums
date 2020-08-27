package com.assignment.ums.validator;

public class Validator {

    public static Boolean validatePassword(String password) {
        if (password == null)
            return false;
        Boolean flag = false;
        if (password.length() >= 8) {
            if (password.matches(".*[A-Z a-z]+.*"))
                if (password.matches(".*[0-9]+.*"))
                    if (password.matches(".*(\\W|_)+.*")) {
                        flag = true;
                    }
        }
        return flag;
    }
}
