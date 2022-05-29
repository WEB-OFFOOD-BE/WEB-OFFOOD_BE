package com.web.offood.controller;

import com.web.offood.dto.account.SignInRequest;
import com.web.offood.dto.base.BaseResponse;
import com.web.offood.dto.base.ResponseBuilder;
import com.web.offood.dto.user.RegisterRequest;
import com.web.offood.service.ServiceImpl.AccountService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/account")
@Api(tags = "account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<String>> login(
            @RequestBody SignInRequest signInRequest) {
        return ResponseEntity.ok(
                ResponseBuilder.ok().with(accountService.signIn(signInRequest)).build());
    }

    @PostMapping("/signup")
    public ResponseEntity<BaseResponse<String>> signUp(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(
                ResponseBuilder.ok().with(accountService.signUp(registerRequest)).build());
    }
    @PostMapping("/sendMail")
    public ResponseEntity<BaseResponse<String>> sendMail(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(
                ResponseBuilder.ok().with(accountService.sendMail(registerRequest)).build());
    }
}
