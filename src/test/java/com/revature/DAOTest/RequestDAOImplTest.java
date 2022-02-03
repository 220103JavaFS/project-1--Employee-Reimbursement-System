package com.revature.DAOTest;

import com.revature.models.Request;
import com.revature.models.RequestDTO;
import com.revature.models.ResolveDTO;
import com.revature.repos.RequestDAO;
import com.revature.repos.RequestDAOImpl;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RequestDAOImplTest {

    private static RequestDAO requestDAO = new RequestDAOImpl();
    private static RequestDTO testRequestDTO = new RequestDTO(
            500,
            "Business trip to New York",
            "Travel",
            2
    );
    private static ResolveDTO testResolveDTO1 = new ResolveDTO("Approved", 15, 1);
    private static Request testRequest = new Request(2, 33,"2022-01-29 16:24:20.356", null, "Lunch", 1, 0, "pending", "food");

    //Refer to database records and update assertEquals expected value each test
    //since the "get" methods will return a different number of results as requests are added
    @Test
    @Order(1)
    void testGetAllRequests(){
        List<Request> requestList = requestDAO.showAllRequests();
        assertEquals(1, requestList.size());
    }

    @Test
    @Order(2)
    void testGetUserRequests(){
        List<Request> requestList = requestDAO.showUserRequests(2);
        assertEquals(1, requestList.size());
    }

    @Test
    @Order(3)
    void testGetByStatus(){
        List<Request> returnedList = requestDAO.showByStatus("Pending");
        assertEquals(1, returnedList.size());
    }

    @Test
    @Order(4)
    void testGetUserStatusRequests(){
        List<Request> requestList = requestDAO.showUserRequestsByStatus("Approved", 2);
        assertEquals(0, requestList.size());
    }

    @Test
    @Order(5)
    void testAddRequest(){
        assertTrue(requestDAO.addRequest(testRequestDTO));
    }

    @Test
    @Order(6)
    void testResolveRequest(){
        assertFalse(requestDAO.resolveRequest(testResolveDTO1));
    }


}

