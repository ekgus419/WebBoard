//package com.dh.webservice.web;
//
//import com.dh.webservice.domain.User;
//import com.dh.webservice.service.UserService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.ModelAndView;
//
//
//@Controller
//@RequestMapping(value="/user")
//@Slf4j
//public class UserController {
//
//    @Autowired
//    private UserService userService;
//
//
//
//    @RequestMapping(value = "/index", method = RequestMethod.GET)
//    public ModelAndView index() {
//        ModelAndView modelAndView = new ModelAndView();
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        log.debug(auth.toString());
//        User user = userService.findUserByUserEmail(auth.getName());
//        log.debug("user : " + user.toString());
//        modelAndView.addObject("user", user);
//        modelAndView.setViewName("home");
//        return modelAndView;
//    }
//
//}