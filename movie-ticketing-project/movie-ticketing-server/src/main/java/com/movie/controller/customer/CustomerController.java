package com.movie.controller.customer;

import com.movie.common.resp.Result;
import com.movie.entity.EmailVerify;
import com.movie.entity.User;
import com.movie.entity.groups.CustomerLogin;
import com.movie.entity.groups.SendEmail;
import com.movie.entity.groups.UpdateUserMarket;
import com.movie.entity.groups.VerifyEmail;
import com.movie.service.EmailVerifyService;
import com.movie.service.MarketService;
import com.movie.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {
    private final EmailVerifyService emailVerifyService;
    private final UserService userService;
    private final MarketService marketService;
    @PostMapping("/register")
    public Result register(@Validated(VerifyEmail.class) @RequestBody EmailVerify emailVerify) {
       return userService.register(emailVerify);
    }
    @PostMapping("/email/verification/code")
    public Result emailVerificationCode(@Validated(SendEmail.class) @RequestBody EmailVerify emailVerify) {
        return emailVerifyService.sentEmail(emailVerify);
    }
    @PostMapping("/login")
    public Result login(@Validated(CustomerLogin.class) @RequestBody User user) {
        return userService.login(user);
    }
    @GetMapping("/market")
    public Result selectAllMarket(){
        return marketService.selectAllMarket();
    }
    @PutMapping("/user")
    public Result updateUserByUserId(@Validated(UpdateUserMarket.class)@RequestBody User user){
        return userService.updateUserByUserId(user);
    }
}
