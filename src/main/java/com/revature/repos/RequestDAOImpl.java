package com.revature.repos;

import com.revature.models.Request;

import java.util.List;

public class RequestDAOImpl implements RequestDAO {


    @Override
    public List<Request> showAllRequests() {
        return null;
    }

    @Override
    public List<Request> showByStatus(String status) {
        return null;
    }

    @Override
    public boolean addRequest(Request request) {
        return false;
    }

    @Override
    public boolean approveRequest(int reimbId) {
        return false;
    }

    @Override
    public boolean denyRequest(int reimbId) {
        return false;
    }
}
