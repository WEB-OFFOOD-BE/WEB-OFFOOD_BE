package com.example.websitedatmon.controllers;

import com.example.websitedatmon.domain.Role;
import com.example.websitedatmon.domain.User;
import com.example.websitedatmon.serviceImpls.RoleServiceImpl;
import com.example.websitedatmon.serviceImpls.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/user")
public class EmployeeController {
    @Autowired
    UserServiceImpl userService;

    @Autowired
    RoleServiceImpl roleService;

    @GetMapping({ "/employee"})
    public ModelAndView index(String msg)
    {
        List<User> list = userService.listEmployee();
        ModelAndView mv = new ModelAndView("employee");
        mv.addObject("msg",msg);
        mv.addObject("list",list);
        return mv;
    }

    @PostMapping(value = "/employee-add")
    public ModelAndView add(HttpServletRequest request){
        ModelAndView mv = new ModelAndView("redirect:employee");
        String fullname = request.getParameter("fullname");
        String email = request.getParameter("email");
        String sdt = request.getParameter("sdt");
        String taikhoan = request.getParameter("taikhoan");
        String password =request.getParameter("matkhau");
        User user = new User();
        user.setEmail(email);
        user.setFullName(fullname);
        user.setPhoneNumber(sdt);
        user.setUserName(taikhoan);
        user.setPassword(password);
        Role role = roleService.findRoleById(3);
        user.setRole(role);
        userService.save(user);
        mv.addObject("msg","success");
        return mv;
    }
}
