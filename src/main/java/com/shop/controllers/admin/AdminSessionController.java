package com.shop.controllers.admin;

import com.shop.application.LoginService;
import com.shop.application.LogoutService;
import com.shop.dtos.LoginRequestDto;
import com.shop.dtos.LoginResultDto;
import com.shop.security.AuthUser;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/admin/session")
public class AdminSessionController {

    private final LoginService loginService;

    private final LogoutService logoutService;

    public AdminSessionController(LoginService loginService,
                             LogoutService logoutService) {
        this.loginService = loginService;
        this.logoutService = logoutService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LoginResultDto login(@RequestBody LoginRequestDto loginRequestDto) {
        String accessToken = loginService.loginAdmin(
                loginRequestDto.email(), loginRequestDto.password());

        return new LoginResultDto(accessToken);
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String loginFailed() {
        return "Bad Request";
    }

    @DeleteMapping
    public String logout(Authentication authentication) {
        AuthUser authUser = (AuthUser) authentication.getPrincipal();

        logoutService.logout(authUser.accessToken());

        return "Logout";
    }

}
