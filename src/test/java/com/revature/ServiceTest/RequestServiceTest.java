package com.revature.ServiceTest;

import com.revature.models.RequestDTO;
import com.revature.models.ResolveDTO;
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
    private RequestDTO requestDTO = new RequestDTO(1.1, "description", "type", 2);
    private ResolveDTO resolveDTO = new ResolveDTO();
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
        Mockito.when(mockedRequestDAO.resolveRequest(resolveDTO)).thenReturn(true);
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
    void testResolveRequest(){
        assertFalse(testRequestService.resolveRequest(resolveDTO, associateRole));
        assertTrue(testRequestService.resolveRequest(resolveDTO, managerRole));
    }

    @Test
    void testShowAllRequests(){
        assertNull(testRequestService.showAllRequests(1, managerRole));
        assertNull(testRequestService.showAllRequests(2, associateRole));
    }

    @Test
    void testShowByStatus(){
        assertNull(testRequestService.showByStatus(pendingStatus, "Associate", 2));
    }



}
