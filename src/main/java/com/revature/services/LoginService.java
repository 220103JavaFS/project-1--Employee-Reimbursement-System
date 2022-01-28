package com.revature.services;

import com.revature.models.UserDTO;
import com.revature.repos.UserDAO;
import com.revature.repos.UserDAOImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginService {

    private UserDAO userDAO;
    private Logger logger = LoggerFactory.getLogger("Login Service Logger");

    public LoginService() {
        userDAO = new UserDAOImpl();
    }

    public LoginService(UserDAO mockedDAO) {
        this.userDAO = mockedDAO;
    }

    public UserDTO login(String username, String password){
        if (username == null){
            logger.debug("The passed in username is null");
        }
        if (password == null){
            logger.debug("The passed in password is null");
        }

        UserDTO userFromDb = userDAO.login(username);

        if (userFromDb == null){
            logger.debug("userFromDb is null; login failed");
            return null;
        }else{
            if(password.equals(userFromDb.password)){
                logger.info("Input password and returned password match");
                return userFromDb;
            }else{
                return null;
            }
        }
    }

    

}