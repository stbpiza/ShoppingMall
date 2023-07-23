package com.shop.application;

import com.shop.exceptions.EmailAlreadyTaken;
import com.shop.repositories.UserRepository;
import com.shop.security.AccessTokenGenerator;
import com.shop.security.AuthUserDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class SignupServiceTest {
    private AuthUserDao authUserDao;

    private PasswordEncoder passwordEncoder;

    private AccessTokenGenerator accessTokenGenerator;

    private UserRepository userRepository;

    private SignupService signupService;

    @BeforeEach
    void setUp() {
        authUserDao = mock(AuthUserDao.class);

        passwordEncoder = mock(PasswordEncoder.class);

        accessTokenGenerator = new AccessTokenGenerator("SECRET");

        userRepository = mock(UserRepository.class);

        signupService = new SignupService(
                authUserDao, passwordEncoder, accessTokenGenerator,
                userRepository);
    }

    @Test
    @DisplayName("signup - when signup is successful")
    void signupSuccess() {
        String email = "tester@example.com";
        String name = "Tester";
        String password = "password";
        String encodedPassword = "ENCODED-PASSWORD";

        given(userRepository.existsByEmail(email)).willReturn(false);

        given(passwordEncoder.encode(password)).willReturn(encodedPassword);

        String accessToken = signupService.signup(email, name, password);

        assertThat(accessToken).isNotBlank();

        verify(authUserDao).addAccessToken(any(), eq(accessToken));
    }

    @Test
    @DisplayName("signup - when email has already been taken")
    void signupEmailAlreadyTaken() {
        String email = "tester@example.com";
        String name = "Tester";
        String password = "password";

        given(userRepository.existsByEmail(email)).willReturn(true);

        assertThatThrownBy(() -> {
            signupService.signup(email, name, password);
        }).isInstanceOf(EmailAlreadyTaken.class);
    }


}