package com.shop.application;

import com.shop.models.User;
import com.shop.models.UserId;
import com.shop.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Optional;

import static com.shop.models.Role.ROLE_USER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class GetUserServiceTest {
    private UserRepository userRepository;

    private GetUserService getUserService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);

        getUserService = new GetUserService(userRepository);
    }

    @Test
    @DisplayName("getUser - when the user exists")
    void getUserSuccess() {
        UserId userId = UserId.generate();

        User user = new User(userId, "tester@example.com", "Tester", ROLE_USER);

        given(userRepository.findById(userId)).willReturn(Optional.of(user));

        User found = getUserService.getUser(userId);

        assertThat(found).isEqualTo(user);
    }

    @Test
    @DisplayName("getUser - when the user doesn't exist")
    void getUserNotFound() {
        UserId userId = UserId.generate();

        given(userRepository.findById(userId)).willReturn(Optional.empty());

        assertThatThrownBy(() -> {
            getUserService.getUser(userId);
        }).isInstanceOf(NoSuchElementException.class);
    }
}