package com.movie.controller.customer;

import com.movie.common.resp.Result;
import com.movie.entity.EmailVerify;
import com.movie.entity.groups.SendEmail;
import com.movie.service.EmailVerifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {
    private final EmailVerifyService emailVerifyService;
    @PostMapping("/register")
    public Result register() {
        return null;
    }
    @PostMapping("/email/verification/code")
    public Result emailVerificationCode(@Validated(SendEmail.class) @RequestBody EmailVerify emailVerify) {
        return emailVerifyService.sentEmail(emailVerify);
    }
    @PostMapping("/login")
    public Result login() {
        return null;
    }
}
