package com.revature.models;

public class Request {

    private int requestId;
    private double amount;
    private String description;
    private String status;
    private int authorId;
    private int resolverId;
    private String type;

    public Request(int requestId, double amount, String description, String status, int authorId, int resolverId, String type) {
        this.requestId = requestId;
        this.amount = amount;
        this.description = description;
        this.status = status;
        this.authorId = authorId;
        this.resolverId = resolverId;
        this.type = type;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getResolverId() {
        return resolverId;
    }

    public void setResolverId(int resolverId) {
        this.resolverId = resolverId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
