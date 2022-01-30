package com.revature.DAOTest;

import com.revature.models.Request;
import com.revature.models.RequestDTO;
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
            "1/1/2022"
    );
    private static Request testRequest = new Request(2, 33,"2022-01-29 16:24:20.356", null, "Lunch", 1, 0, "pending", "food");

    @Test
    void testGetRequest(){
        List<Request> returnedList = requestDAO.showByStatus("pending");
        Request request = returnedList.get(0);
        assertEquals(testRequest, request);
    }

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
        assertTrue(requestDAO.addRequest(testRequestDTO));
    }

    @Test
    @Order(4)
    void testApproveRequest(){
        assertTrue(requestDAO.approveRequest(1));
        //assertFalse(requestDAO.approveRequest(testRequest.getRequestId()));
    }

    @Test
    @Order(5)
    void testDenyRequest(){
        assertTrue(requestDAO.denyRequest(1));
        //assertFalse(requestDAO.denyRequest(testRequest.getRequestId()));
    }
}

