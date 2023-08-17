package com.shop.controllers.admin;

import com.shop.application.GetUserListService;
import com.shop.application.GetUserService;
import com.shop.dtos.UserDto;
import com.shop.dtos.admin.AdminUserListDto;
import com.shop.models.User;
import com.shop.models.UserId;
import com.shop.security.AdminRequired;
import com.shop.security.AuthUser;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AdminRequired
@RestController
@RequestMapping("/admin/users")
public class AdminUserController {
    private final GetUserService getUserService;
    private final GetUserListService getUserListService;

    public AdminUserController(GetUserService getUserService,
                               GetUserListService getUserListService) {
        this.getUserService = getUserService;
        this.getUserListService = getUserListService;
    }

    @GetMapping("/me")
    public UserDto me(Authentication authentication) {
        AuthUser authUser = (AuthUser) authentication.getPrincipal();
        UserId id = new UserId(authUser.id());
        User user = getUserService.getAdminUser(id);
        return UserDto.of(user);
    }

    @GetMapping
    public AdminUserListDto list() {
        List<User> users = getUserListService.getUserList();
        return AdminUserListDto.of(users);
    }
}
