package com.revature.DAOTest;

import com.revature.repos.UserDAO;
import com.revature.repos.UserDAOImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;


public class UserDaoImplTest {

    private static UserDAO userDAO = new UserDAOImpl();

    @Test
    void loginTest(){
        assertNull(userDAO.login(""));
    }
}
