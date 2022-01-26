package com.revature.services;

import com.revature.models.UserDTO;
import com.revature.repos.UserDAO;
import com.revature.repos.UserDAOImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginService {

    private UserDAO userDAO = new UserDAOImpl();
    private Logger logger = LoggerFactory.getLogger("Login Service Logger");

    public LoginService() {}

    public LoginService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public boolean login(String username, String password){
        if (username == null){
            logger.debug("The passed in username is null");
        }
        if (password == null){
            logger.debug("The passed in password is null");
        }
        UserDTO userFromDb = userDAO.login(username);
        if (userFromDb.username == null){
            logger.debug("The username from the returned DTO is null");
        }else{
            logger.debug("The username from the returned DTO is not null");
        }
        if(userFromDb != null && password.equals(userFromDb.password)){
            logger.info("Input password and returned password match");
            return true;
        }else{
            return false;
        }
    }

}