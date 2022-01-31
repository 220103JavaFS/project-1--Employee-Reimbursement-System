package com.revature.controllers;

import com.revature.models.Request;
import com.revature.models.RequestDTO;
import com.revature.models.ResolveDTO;
import com.revature.models.UserDTO;
import com.revature.services.RequestService;
import io.javalin.Javalin;
import io.javalin.http.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class RequestController implements Controller{

    RequestService requestService = new RequestService();
    Logger logger = LoggerFactory.getLogger("Request Controller Logger");

    private Handler getAllRequests = ctx -> {
        if (ctx.req.getSession(false) != null){
            int userId = ctx.cookieStore("userID");
            String userRole = ctx.cookieStore("userRole");
            List<Request> requestList = requestService.showAllRequests(userId, userRole);
            if (requestList != null){
                logger.debug("The list of reimbursement requests was retrieved");
                ctx.json(requestList);
                ctx.status(200);
            }else{
                logger.debug("The list of reimbursement requests was not retrieved");
                ctx.status(400);
            }
        }else{
            logger.debug("There isn't a session in progress");
        }
    };

    private Handler getByStatus = ctx -> {

        if(ctx.req.getSession(false)!=null){
            String reimbStatus = ctx.pathParam("reimbStatus");
            List<Request> requestByStatus = requestService.showByStatus(reimbStatus);
            if (requestByStatus != null) {
                ctx.json(requestByStatus);
                ctx.status(200);
            }else{
                logger.debug("The returned list was empty or there was a problem");
            }
        }else {
            ctx.status(401);
        }

    };

    private Handler addRequest = (ctx) -> {
        if (ctx.req.getSession(false) != null) {
            RequestDTO requestDTO = ctx.bodyAsClass(RequestDTO.class);
            requestDTO.authorId = ctx.cookieStore("userID");

            if (requestService.addRequest(requestDTO)) {
                logger.info("requestDTO was successfully created");
                ctx.status(200);
            }
             else {
                logger.error("There was a problem creating the DTO from the form input");
                ctx.status(401);
            }
        }else{
            logger.debug("There isn't a session in progress");
        }
    };

    private Handler approveRequest = ctx -> {
        if (ctx.req.getSession(false) != null) {
            logger.debug("Made it inside the approveRequest Handler");
            logger.debug("Before ResolveDTO object is created");
            ResolveDTO resolveDTO = ctx.bodyAsClass(ResolveDTO.class);
            resolveDTO.authorId = ctx.cookieStore("userID");
            logger.debug("After ResolveDTO object is created");
            logger.debug("resolveDTO.authorID: " + resolveDTO.authorId);

            if (requestService.resolveRequest(resolveDTO)) {
                logger.info("requestDTO was successfully created");
                ctx.status(200);
            }
            else {
                logger.error("There was a problem creating the DTO from the form input");
                ctx.status(401);
            }
        }else{
            logger.debug("There isn't a session in progress");
        }
    };

    @Override
    public void addRoutes(Javalin app) {
        app.get("/getAllRequests", getAllRequests);
        app.get("/getByStatus/{reimbStatus}", getByStatus);
        app.post("/addRequest", addRequest);
        app.post("/approveRequest", approveRequest);
    }
}

