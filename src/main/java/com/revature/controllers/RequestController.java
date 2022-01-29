package com.revature.controllers;

import com.revature.models.RequestDTO;
import com.revature.models.UserDTO;
import com.revature.services.RequestService;
import io.javalin.Javalin;
import io.javalin.http.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestController implements Controller{

    RequestService requestService = new RequestService();
    Logger logger = LoggerFactory.getLogger("Request Controller Logger");

    private Handler getAllRequests = ctx -> {

    };

    private Handler getByStatus = ctx -> {

    };

    private Handler addRequest = (ctx) -> {
        logger.debug("Made it inside addRequest Handler");
        if (ctx.req.getSession(false) != null) {
            logger.debug("Made it inside if/else in the Handler");
            RequestDTO requestDTO = ctx.bodyAsClass(RequestDTO.class);
            if (requestService.addRequest(requestDTO)) {
                if (requestService.addRequest(requestDTO)) {
                    logger.info("requestDTO was successfully created");
                    ctx.status(200);
                }
            } else {
                logger.error("There was a problem creating the DTO from the form input");
                ctx.status(401);
            }
        }else{
            logger.debug("There isn't a session in progress");
        }
    };

    private Handler approveRequest = ctx -> {
        if (ctx.req.getSession(false) != null) {

        }else{
            logger.debug("There isn't a session in progress");
        }
    };

    private Handler denyRequest = ctx -> {
        if (ctx.req.getSession(false) != null) {

        }else{
            logger.debug("There isn't a session in progress");
        }
    };

    @Override
    public void addRoutes(Javalin app) {
        app.get("/getAllRequests", getAllRequests);
        app.get("/getByStatus", getByStatus);
        app.post("/addRequest", addRequest);
        app.patch("/approveRequest", approveRequest);
        app.patch("/denyRequest", denyRequest);
    }
}

