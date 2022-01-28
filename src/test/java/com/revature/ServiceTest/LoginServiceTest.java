package com.revature.ServiceTest;

import com.revature.models.UserDTO;
import com.revature.repos.UserDAO;
import com.revature.services.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;


class LoginServiceTest {

    private LoginService testInstance;

    @Mock
    private UserDAO mockedDAO;
    private UserDTO testUser = new UserDTO();

    @BeforeEach
    public void setUp() throws NoSuchAlgorithmException {
        testUser.username = "agent";

        testUser.password = LoginService.toHexString(LoginService.getSHA("password"));
        MockitoAnnotations.openMocks(this);
        testInstance = new LoginService(mockedDAO);
        Mockito.when(mockedDAO.login("agent")).thenReturn(testUser);
    }

    @Test
    void testLoginSuccess(){
        //First test when mockedDAO returned null
        //assertNull(testInstance.login("agent", "password"));

        //Second test after mockedDAO returned testUser
        assertEquals(testUser.username, testInstance.login(testUser.username, "password").username);
    }

    @Test
    void testLoginFailUsername(){
        assertEquals(testUser, testInstance.login("notAgent", "password"));
    }

    @Test
    void testLoginFailPassword(){
        assertNull(testInstance.login("agent", "word"));
    }

    @Test
    void testLoginFailBoth(){
        assertNull(testInstance.login("notAgent", "word"));
    }
}