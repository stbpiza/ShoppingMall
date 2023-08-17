package com.shop.controllers;

import com.shop.application.GetUserService;
import com.shop.application.LoginService;
import com.shop.application.LogoutService;
import com.shop.controllers.admin.AdminSessionController;
import com.shop.models.User;
import com.shop.models.UserId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.web.servlet.MockMvc;

import static com.shop.models.Role.ROLE_ADMIN;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminSessionController.class)
class AdminSessionControllerTest extends ControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoginService loginService;

    @MockBean
    private LogoutService logoutService;

    @MockBean
    private GetUserService getUserService;

    @BeforeEach
    void setUp() {
        given(loginService.loginAdmin("admin@example.com", "password"))
                .willReturn("Admin.Access.Token");

        given(loginService.loginAdmin("tester@example.com", "password"))
                .willThrow(new BadCredentialsException("Login failed"));

        given(loginService.loginAdmin("xxx", "password"))
                .willThrow(new BadCredentialsException("Login failed"));

        given(loginService.loginAdmin("tester@example.com", "xxx"))
                .willThrow(new BadCredentialsException("Login failed"));
    }

    @Test
    @DisplayName("DELETE /session - with incorrect access token")
    void logoutWithIncorrectAccessToken() throws Exception {
        mockMvc.perform(delete("/session")
                        .header("Authorization", "Bearer XXX"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("DELETE /session - without access token")
    void logoutWithoutAccessToken() throws Exception {
        mockMvc.perform(delete("/session"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("GET /admin/users/me - with admin access token")
    void detailAdmin() throws Exception {
        UserId userId = new UserId(ADMIN_ID);

        User user = new User(userId, "admin@example.com", "Admin", ROLE_ADMIN);

        given(getUserService.getAdminUser(userId)).willReturn(user);

        mockMvc.perform(get("/admin/users/me")
                        .header("Authorization", "Bearer " + adminAccessToken))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("name")));
    }

    @Test
    @DisplayName("GET /admin/users/me - with non-admin access token")
    void detailNonAdmin() throws Exception {
        mockMvc.perform(get("/admin/users/me")
                        .header("Authorization", "Bearer " + userAccessToken))
                .andExpect(status().isForbidden());
    }
}