package com.revature.ServiceTest;

import com.revature.models.Request;
import com.revature.models.RequestDTO;
import com.revature.models.ResolveDTO;
import com.revature.repos.RequestDAO;
import com.revature.services.RequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RequestServiceTest {

    private RequestService testRequestService;
    List<Request> requestList = new ArrayList<>();

    @Mock
    private RequestDAO mockedRequestDAO;
    private RequestDTO requestDTO = new RequestDTO(1.1, "description", "type", 2);
    private ResolveDTO resolveDTO = new ResolveDTO("Approve", 42, 1);
    private String pendingStatus = "Pending";
    private String approvedStatus = "Approved";
    private String deniedStatus = "Denied";
    private String associateRole = "Associate";
    private String managerRole = "Manager";

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        testRequestService = new RequestService(mockedRequestDAO);
        Mockito.when(mockedRequestDAO.addRequest(requestDTO)).thenReturn(true);
        Mockito.when(mockedRequestDAO.resolveRequest(resolveDTO)).thenReturn(true);
        Mockito.when(mockedRequestDAO.showAllRequests()).thenReturn(new ArrayList<>());
        Mockito.when(mockedRequestDAO.showByStatus(pendingStatus)).thenReturn(new ArrayList<>());
        Mockito.when(mockedRequestDAO.showByStatus(approvedStatus)).thenReturn(new ArrayList<>());
        Mockito.when(mockedRequestDAO.showByStatus(deniedStatus)).thenReturn(new ArrayList<>());
    }

    @Test
    void testAddRequest(){
        assertTrue(testRequestService.addRequest(requestDTO));
        assertFalse(testRequestService.addRequest(new RequestDTO(0, "description2", "", 1)));
    }

    @Test
    void testResolveRequest(){
        assertFalse(testRequestService.resolveRequest(resolveDTO, associateRole));
        assertTrue(testRequestService.resolveRequest(resolveDTO, managerRole));
    }

    @Test
    void testShowAllRequests(){
        assertEquals(requestList, testRequestService.showAllRequests(1, managerRole));
        assertEquals(requestList, testRequestService.showAllRequests(2, associateRole));
    }

    @Test
    void testShowByStatus(){
        assertEquals(requestList, testRequestService.showByStatus(approvedStatus, managerRole, 1));
        assertEquals(requestList, testRequestService.showByStatus(pendingStatus, associateRole, 2));
    }

}
