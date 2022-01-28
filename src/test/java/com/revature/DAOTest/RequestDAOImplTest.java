package com.revature.DAOTest;

import com.revature.models.Request;
import com.revature.repos.RequestDAO;
import com.revature.repos.RequestDAOImpl;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RequestDAOImplTest {

    private static RequestDAO requestDAO = new RequestDAOImpl();
    private static Request testRequest = new Request(
            1,
            500,
            "Business trip to New York",
            "Pending",
            1,
            1,
            "Travel"
    );

    @Test
    @Order(2)
    void testGetAllRequests(){
        //assertEquals(null, requestDAO.showAllRequests());

        List<Request> requestList = requestDAO.showAllRequests();
        assertEquals(5, requestList.size());

        //assertNull(requestDAO.showAllRequests());
    }

    @Test
    @Order(3)
    void testGetByStatus(){
        List<Request> returnedList = requestDAO.showByStatus("pending");
        assertEquals(4, returnedList.size());
    }

    @Test
    @Order(6)
    void testAddRequest(){
        assertTrue(requestDAO.addRequest(testRequest));
    }

    @Test
    @Order(4)
    void testApproveRequest(){
        assertTrue(requestDAO.approveRequest(testRequest.getRequestId()));
        //assertFalse(requestDAO.approveRequest(testRequest.getRequestId()));
    }

    @Test
    @Order(5)
    void testDenyRequest(){
        assertTrue(requestDAO.denyRequest(testRequest.getRequestId()));
        //assertFalse(requestDAO.denyRequest(testRequest.getRequestId()));
    }
}

