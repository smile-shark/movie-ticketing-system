package com.movie.controller.global;

import com.movie.common.resp.Result;
import com.movie.entity.EmailVerify;
import com.movie.entity.User;
import com.movie.entity.groups.SendEmail;
import com.movie.service.EmailVerifyService;
import com.movie.service.MarketService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/global")
@RequiredArgsConstructor
public class GlobalController {
    private final EmailVerifyService emailVerifyService;
    private final MarketService marketService;
    @PostMapping("/email/verification/code")
    public Result emailVerificationCode(@Validated(SendEmail.class) @RequestBody EmailVerify emailVerify) {
        return emailVerifyService.sentEmail(emailVerify);
    }
    @GetMapping("/market")
    public Result selectAllMarket() {
        Result result = marketService.selectAllMarket();
        System.out.println(result);
        return result;
    }
}
