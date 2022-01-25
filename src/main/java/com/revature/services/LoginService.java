package com.revature.services;

import com.revature.models.UserDTO;
import com.revature.repos.UserDAO;

public class LoginService {

    private UserDAO userDAO;

    public LoginService() {}

    public LoginService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public boolean login(String username, String password){
        UserDTO userFromDb = userDAO.login(username);
        if(userFromDb != null && password.equals(userFromDb.password)){
            return true;
        }else{
            return false;
        }
    }

}
