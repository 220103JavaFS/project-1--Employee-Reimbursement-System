package com.revature.repos;

import com.revature.models.Request;
import com.revature.models.RequestDTO;
import com.revature.models.ResolveDTO;

import java.util.List;

public interface RequestDAO {
    public List<Request> showAllRequests();
    public List<Request> showByStatus(String status);


    public int addReimbStatus();
    public int addReimbType(String type);
    public boolean addRequest(RequestDTO requestDTO);
    public boolean approveRequest(int reimbId);
    public boolean denyRequest(int reimbId);
    public boolean resolveRequest(ResolveDTO resolveDTO);
}