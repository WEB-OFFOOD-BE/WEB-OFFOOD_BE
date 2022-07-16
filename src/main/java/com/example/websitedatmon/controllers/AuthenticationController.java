package com.example.websitedatmon.controllers;

import com.example.websitedatmon.constans.CommonConstants;
import com.example.websitedatmon.domain.User;
import com.example.websitedatmon.services.UserService;
import com.example.websitedatmon.utils.Middleware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("user")
public class AuthenticationController {

    @Autowired
    UserService userService;

    Middleware middleware = new Middleware();

    @GetMapping({ "/login"})
    public ModelAndView login(HttpServletRequest request)
    {
        ModelAndView mv = new ModelAndView("login");
        User user = middleware.middlewareUser(request);
        if(user != null){
            mv = new ModelAndView("redirect:/user/home");
        }
        return mv;
    }


    @PostMapping({ "/login"})
    public ModelAndView login(HttpServletRequest request, User user, RedirectAttributes rd)
    {
        ModelAndView mv = new ModelAndView("redirect:/user/login");
        User checkAuth = userService.findUserByUserNameAndPassword(user.getUserName(),user.getPassword());
        if(checkAuth != null){
            HttpSession session = request.getSession();
            session.setAttribute(CommonConstants.SESSION_USER, checkAuth);
            mv = new ModelAndView("redirect:/user/home");
        }else{
            rd.addFlashAttribute(CommonConstants.MSG, "Thông tin hoặc tài khoản không chính xác");
            mv = new ModelAndView("redirect:/user/login");
        }
        return mv;
    }

}
