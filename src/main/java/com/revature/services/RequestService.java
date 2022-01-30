package com.revature.services;

import com.revature.models.Request;
import com.revature.models.RequestDTO;
import com.revature.repos.RequestDAO;
import com.revature.repos.RequestDAOImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class RequestService {

    private RequestDAO requestDAO;
    Logger logger = LoggerFactory.getLogger("Request Service Logger");

    public RequestService(){
        requestDAO = new RequestDAOImpl();
    }

    public RequestService(RequestDAO requestDAO) {
        this.requestDAO = requestDAO;
    }

    public List<Request> showAllRequests(int userId, String userRole){
        /*
        if (userRole.equalsIgnoreCase("Manager")){
            return requestDAO.showAllRequests();
        }else {
            return requestDAO.showUserRequests(userId);
        }
         */

        return requestDAO.showAllRequests();
    }
    public List<Request> showByStatus(String status){
        return requestDAO.showByStatus(status);
    }

    public boolean addRequest(RequestDTO requestDTO){

//        if (requestDTO.amount <=0 || requestDTO.description == null || requestDTO.type == null || requestDTO.submitted == null
//        || requestDTO.authorId <=0){
//            return false;
//        }

        return requestDAO.addRequest(requestDTO);
    }

    public boolean approveRequest(int reimbId, String userRole){
        if (userRole.equalsIgnoreCase("Manager")) {
            logger.info("The user has permission to deny this request");
            return requestDAO.denyRequest(reimbId);
        }else{
            logger.info("The user does not have permission to deny this request");
            return false;
        }
    }
    public boolean denyRequest(int reimbId, String userRole){
        if (userRole.equalsIgnoreCase("Manager")) {
            logger.info("The user has permission to deny this request");
            return requestDAO.denyRequest(reimbId);
        }else{
            logger.info("The user does not have permission to deny this request");
            return false;
        }
    }


}
