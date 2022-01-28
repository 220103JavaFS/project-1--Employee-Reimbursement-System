package com.revature.models;

import java.sql.Date;

public class Request {

    private Date resolved;
    private Date submitted;
    private int requestId;
    private double amount;
    private String description;
    private String status;
    private int author;
    private int resolver;
    private String type;

    public Request(int requestId, double amount, String description, String status, int authorId, int resolverId, String type) {
        this.requestId = requestId;
        this.amount = amount;
        this.description = description;
        this.status = status;
        this.author = authorId;
        this.resolver = resolverId;
        this.type = type;
    }

    public Request(int requestId, double amount, Date submitted, Date resolved, String description, int author, int resolver, String status, String type) {
        this.requestId = requestId;
        this.amount = amount;
        this.description = description;
        this.status = status;
        this.author = author;
        this.resolver = resolver;
        this.type = type;
        this.submitted = submitted;
        this.resolved = resolved;
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
        return author;
    }

    public void setAuthorId(int authorId) {
        this.author = authorId;
    }

    public int getResolver() {
        return resolver;
    }

    public void setResolver(int resolver) {
        this.resolver = resolver;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
