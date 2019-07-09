/**
 * @author cdh
 * @since 2019-07-01
 * @copyright  Copyright dh-0419(https://github.com/ekgus419/WebBoard)
 *
 */
package com.dh.webservice.web;

import com.dh.webservice.domain.User;
import com.dh.webservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * @title Web 컨트롤러 파일
 * @author cdh
 * @FileName : WebController
 *
 */
@Controller
public class WebController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/", "/login", "/signin"}, method = RequestMethod.GET)
    public String home() {
        return "/signin";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @PostMapping("/signup")
    @ResponseBody
    public User signup(@RequestBody User user) {
        System.out.println("signup post loaded");
        userService.saveUser(user);
        return new User();
    }

}
