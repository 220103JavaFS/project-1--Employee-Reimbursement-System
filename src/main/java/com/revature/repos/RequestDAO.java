package com.revature.repos;

import com.revature.models.Request;

import java.util.List;

public interface RequestDAO {
    public List<Request> showAllRequests();
    public List<Request> showByStatus(String status);


    public int addReimbStatus();
    public int addReimbType(String type);
    public boolean addRequest(Request request);
    public boolean approveRequest(int reimbId);
    public boolean denyRequest(int reimbId);
}