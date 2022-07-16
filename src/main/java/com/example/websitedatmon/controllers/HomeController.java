package com.example.websitedatmon.controllers;

import com.example.websitedatmon.domain.User;
import com.example.websitedatmon.utils.Middleware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("user")
public class HomeController {

    Middleware middleware = new Middleware();

    @GetMapping({ "/home"})
    public ModelAndView home(HttpServletRequest request)
    {
        ModelAndView mv = new ModelAndView("index");
        User user = middleware.middlewareUser(request);
        if(user == null){
            mv = new ModelAndView("redirect:/user/login");
        }
        return mv;
    }
}
