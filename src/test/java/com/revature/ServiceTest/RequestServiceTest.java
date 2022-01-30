package com.revature.ServiceTest;

import com.revature.models.Request;
import com.revature.models.RequestDTO;
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
    private RequestDTO requestDTO = new RequestDTO(1.1, "description", "type", "1-1-2022");
    private String pendingStatus = "Pending";
    private String approvedStatus = "Approved";
    private String deniedStatus = "Denied";
    private String associateRole = "Associate";
    private String managerRole = "Manager";

    @BeforeEach
    public void setUp(){

        MockitoAnnotations.openMocks(this);
        testRequestService = new RequestService(mockedRequestDAO);
        Mockito.when(mockedRequestDAO.addRequest(requestDTO)).thenReturn(false);
        Mockito.when(mockedRequestDAO.denyRequest(1)).thenReturn(true);
        Mockito.when(mockedRequestDAO.approveRequest(1)).thenReturn(true);
        Mockito.when(mockedRequestDAO.showAllRequests()).thenReturn(null);
        Mockito.when(mockedRequestDAO.showByStatus(pendingStatus)).thenReturn(null);
        Mockito.when(mockedRequestDAO.showByStatus(approvedStatus)).thenReturn(null);
        Mockito.when(mockedRequestDAO.showByStatus(deniedStatus)).thenReturn(null);
    }

    @Test
    void testAddRequest(){
        assertFalse(testRequestService.addRequest(requestDTO));
    }

    @Test
    void testDenyRequest(){
        assertFalse(testRequestService.denyRequest(1, associateRole));
        assertTrue(testRequestService.denyRequest(1, managerRole));
    }

    @Test
    void testApproveRequest(){
        assertFalse(testRequestService.approveRequest(1, associateRole));
        assertTrue(testRequestService.approveRequest(1, managerRole));
    }

    @Test
    void testShowAllRequests(){
        assertNull(testRequestService.showAllRequests(1, managerRole));
        assertNull(testRequestService.showAllRequests(2, associateRole));
    }

    @Test
    void testShowByStatus(){
        assertNull(testRequestService.showByStatus(pendingStatus));
    }



}
