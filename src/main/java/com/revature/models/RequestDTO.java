package com.revature.models;

import java.util.Date;

public class RequestDTO {
    public double amount;
    public String description;
    Date submitted;
    public String type;
    public int authorId;

    public RequestDTO(double amount, String description, String type, Date submitted) {
        this.amount = amount;
        this.description = description;
        this.type = type;
        this.submitted = submitted;
    }
}
