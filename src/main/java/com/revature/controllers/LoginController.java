package com.revature.controllers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.revature.models.UserDTO;
import com.revature.services.LoginService;
import io.javalin.Javalin;
import io.javalin.http.Handler;

public class LoginController implements Controller{

   LoginService loginService = new LoginService();

    private Handler loginAttempt = (ctx) -> {
        UserDTO user = ctx.bodyAsClass(UserDTO.class); //A DTO (Data transfer object) is a temporary object used just to communicate information.

        if(loginService.login(user.username, user.password)){
            ctx.req.getSession(); //This will return an HttpSession object. If none exists then a new one will be created
            //and a cookie will be added to the response for the client to store.
            ctx.status(200);
        }else {
            ctx.req.getSession().invalidate(); //invalidates any open session that is attached to the client that sent invalid credentials.
            ctx.status(401);
        }
    };


    @Override
    public void addRoutes(Javalin app) {
        app.post("/login", this.loginAttempt);
    }

    //this is a test comment
}
