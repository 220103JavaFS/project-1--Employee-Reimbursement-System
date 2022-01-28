package com.revature.ServiceTest;

import com.revature.models.Request;
import com.revature.repos.RequestDAO;
import com.revature.services.RequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class RequestServiceTest {

    private RequestService testRequestService;


    @Mock
    private RequestDAO mockedRequestDAO;
    private Request request = new Request(1, 1.1, "description", "statis", 1, 1, "type");


    @BeforeEach
    public void setUp(){

        MockitoAnnotations.openMocks(this);
        testRequestService = new RequestService(mockedRequestDAO);
        Mockito.when(mockedRequestDAO.addRequest(request)).thenReturn(false);
        Mockito.when(mockedRequestDAO.denyRequest(request.getRequestId())).thenReturn(false);
        Mockito.when(mockedRequestDAO.approveRequest(request.getRequestId())).thenReturn(false);
        Mockito.when(mockedRequestDAO.showAllRequests()).thenReturn(null);
        Mockito.when(mockedRequestDAO.showByStatus(request.getStatus())).thenReturn(null);
    }

    @Test
    void testAddRequest(){
        assertFalse(testRequestService.addRequest(request));
    }

    @Test
    void testDenyRequest(){
        assertFalse(testRequestService.denyRequest(request.getRequestId()));
    }

    @Test
    void testApproveRequest(){
        assertFalse(testRequestService.approveRequest(request.getRequestId()));
    }

    @Test
    void testShowAllRequests(){assertNull(testRequestService.showAllRequests());}

    @Test
    void testShowByStatus(){
        assertNull(testRequestService.showByStatus(request.getStatus()));
    }



}