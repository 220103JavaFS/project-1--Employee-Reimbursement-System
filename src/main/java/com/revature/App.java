package com.revature;

import com.revature.controllers.Controller;
import com.revature.controllers.LoginController;
import com.revature.controllers.RequestController;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

public class App {

    private static Javalin app;

    public static void main(String[] args) {
        app = Javalin.create((config)->{
//            config.addStaticFiles("C:\\Users\\zbyrs\\Desktop\\project-1--zachivo\\Project1-Frontend\\",
//                    Location.EXTERNAL);
            config.addStaticFiles("C:\\Users\\ivo00\\Documents\\revature\\project-1--zachivo\\Project1-Frontend",
                    Location.EXTERNAL);
        });
        configure(new LoginController(), new RequestController());
        app.start();
    }

    private static void configure(Controller... controllers){
        for(Controller c: controllers){
            c.addRoutes(app);
        }
    }
}
