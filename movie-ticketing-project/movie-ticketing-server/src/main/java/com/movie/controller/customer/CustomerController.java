package com.movie.controller.customer;

import com.movie.common.resp.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    @PostMapping("/register")
    public Result register() {
        return null;
    }
    @PostMapping("/email/verification/code")
    public Result emailVerificationCode() {
        return null;
    }
    @PostMapping("/login")
    public Result login() {
        return null;
    }
}
