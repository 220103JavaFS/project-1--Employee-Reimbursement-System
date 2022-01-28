package com.revature.controllers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.revature.models.UserDTO;
import com.revature.services.LoginService;
import io.javalin.Javalin;
import io.javalin.http.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginController implements Controller{

   LoginService loginService = new LoginService();
   Logger logger = LoggerFactory.getLogger("Login Controller Logger");

    private Handler loginAttempt = (ctx) -> {
        UserDTO user = ctx.bodyAsClass(UserDTO.class);
        UserDTO returnedUser = loginService.login(user.username, user.password);
        if(returnedUser != null){
            logger.info("Login attempt was successful");
            ctx.header("Access-Control-Allow-Origin", "http://revature-220103-zach.s3-website-us-east-1.amazonaws.com");
            ctx.header("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
            ctx.header("Access-Control-Allow-Credentials", "true");
            ctx.req.getSession();
            ctx.cookieStore("userRole", returnedUser.userRole);
            ctx.status(200);
        }else {
            logger.error("Login attempt was unsuccessful");
            ctx.req.getSession().invalidate();
            ctx.status(401);
        }
    };

    private Handler logout = (ctx) -> {
        ctx.req.getSession().invalidate();

        if(ctx.req.getSession(false) == null){
            logger.info("Logout successful");
            ctx.status(200);
        }else {
            logger.error("Logout unsuccessful");
            ctx.status(401);
        }
    };

    @Override
    public void addRoutes(Javalin app) {
        app.post("/login", this.loginAttempt);
        app.post("/logout", this.logout);
    }

}
