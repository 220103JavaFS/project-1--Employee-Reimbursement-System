package com.revature.services;

import com.revature.models.Request;
import com.revature.models.RequestDTO;
import com.revature.models.ResolveDTO;
import com.revature.repos.RequestDAO;
import com.revature.repos.RequestDAOImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class RequestService {

    private RequestDAO requestDAO;
    private static final Logger logger = LoggerFactory.getLogger("Request Service Logger");

    public RequestService(){
        requestDAO = new RequestDAOImpl();
    }

    public RequestService(RequestDAO requestDAO) {
        this.requestDAO = requestDAO;
    }

    public List<Request> showAllRequests(int userId, String userRole){
        if (userRole.equalsIgnoreCase("Manager")){
            logger.info("The user is a manager.  Returning all requests.");
            return requestDAO.showAllRequests();
        }else {
            logger.info("The user is an associate.  Returning only requests associated with the current user.");
            logger.info("The userId is: " + userId);
            return requestDAO.showUserRequests(userId);
        }
    }

    public List<Request> showByStatus(String status, String userRole, int userId){
        if (userRole.equalsIgnoreCase("Manager")){
            logger.info("The user is a manager.  Returning all requests.");
            return requestDAO.showByStatus(status);
        }else {
            logger.info("The user is an associate.  Returning only requests associated with the current user.");
            return requestDAO.showUserRequestsByStatus(status, userId);
        }
    }

    public boolean addRequest(RequestDTO requestDTO){
        if (requestDTO.amount <=0 || requestDTO.description == null || requestDTO.type == null || requestDTO.authorId <=0){
            logger.error("One or more fields provided are empty");
            return false;
        }else {
            return requestDAO.addRequest(requestDTO);
        }
    }

    public boolean resolveRequest(ResolveDTO resolveDTO, String userRole){
        if (userRole.equalsIgnoreCase("Manager")) {
            logger.info("The user has permission to resolve this request");
            return requestDAO.resolveRequest(resolveDTO);
        }else{
            logger.info("The user does not have permission to resolve this request");
            return false;
        }
    }
}
