package com.revature.models;

public class ResolveDTO {

    public String resolveChoice;
    public int requestID;
    public int authorId;
    public String dateResolved;

    public ResolveDTO(String resolveChoice, int requestID, int authorId, String dateResolved) {
        this.resolveChoice = resolveChoice;
        this.requestID = requestID;
        this.authorId = authorId;
        this.dateResolved = dateResolved;
    }

    public ResolveDTO() {
    }

    public ResolveDTO(String resolveChoice, int requestID) {
        this.resolveChoice = resolveChoice;
        this.requestID = requestID;

    }
}
