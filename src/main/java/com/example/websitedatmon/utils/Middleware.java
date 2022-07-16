package com.example.websitedatmon.utils;


import com.example.websitedatmon.constans.CommonConstants;
import com.example.websitedatmon.domain.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

public class Middleware {

    public  static boolean middleware( HttpServletRequest request){;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(CommonConstants.SESSION_USER);
        if (Objects.nonNull(user)) {
            return true;
        } else {
            return false;
        }
    }
    public static User middlewareUser( HttpServletRequest request){;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(CommonConstants.SESSION_USER);
        if (Objects.nonNull(user)) {
            return user;
        } else {
            return null;
        }
    }
}
