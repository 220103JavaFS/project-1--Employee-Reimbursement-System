package com.revature.services;

import com.revature.models.Request;
import com.revature.models.RequestDTO;
import com.revature.repos.RequestDAO;
import com.revature.repos.RequestDAOImpl;

import java.util.List;

public class RequestService {

    private RequestDAO requestDAO;

    public RequestService(){
        requestDAO = new RequestDAOImpl();
    }

    public RequestService(RequestDAO RequestDAO) {
        this.requestDAO = RequestDAO;
    }

    public List<Request> showAllRequests(){return requestDAO.showAllRequests();}
    public List<Request> showByStatus(String status){return requestDAO.showByStatus(status);}

    public boolean addRequest(RequestDTO requestDTO){
        return requestDAO.addRequest(requestDTO);
    }

    public boolean approveRequest(int reimbId){return  requestDAO.approveRequest(reimbId);}
    public boolean denyRequest(int reimbId){return  requestDAO.denyRequest(reimbId);}


}
