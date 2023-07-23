package com.shop.controllers;

import com.shop.application.GetUserService;
import com.shop.application.SignupService;
import com.shop.dtos.SignupRequestDto;
import com.shop.dtos.SignupResultDto;
import com.shop.dtos.UserDto;
import com.shop.models.User;
import com.shop.models.UserId;
import com.shop.security.AuthUser;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final SignupService signupService;
    private final GetUserService getUserService;

    public UserController(SignupService signupService,
                          GetUserService getUserService) {
        this.signupService = signupService;
        this.getUserService = getUserService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SignupResultDto signup(@Valid @RequestBody SignupRequestDto signupRequestDto) {
        String accessToken = signupService.signup(
                signupRequestDto.email().trim(),
                signupRequestDto.name().trim(),
                signupRequestDto.password().trim()
        );

        return new SignupResultDto(accessToken);
    }

    @GetMapping("/me")
    public UserDto me(Authentication authentication) {
        AuthUser authUser = (AuthUser) authentication.getPrincipal();
        UserId id = new UserId(authUser.id());
        User user = getUserService.getUser(id);
        return UserDto.of(user);
    }
}
