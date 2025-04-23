package com.movie.controller.global;

import com.movie.common.resp.Result;
import com.movie.entity.EmailVerify;
import com.movie.entity.User;
import com.movie.entity.groups.SendEmail;
import com.movie.service.EmailVerifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/global")
@RequiredArgsConstructor
public class GlobalController {
    private final EmailVerifyService emailVerifyService;
    @PostMapping("/email/verification/code")
    public Result emailVerificationCode(@Validated(SendEmail.class) @RequestBody EmailVerify emailVerify) {
        return emailVerifyService.sentEmail(emailVerify);
    }
}
