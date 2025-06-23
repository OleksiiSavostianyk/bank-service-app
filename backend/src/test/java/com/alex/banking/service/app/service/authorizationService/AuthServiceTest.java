package com.alex.banking.service.app.service.authorizationService;

import com.alex.banking.service.app.models.BankUser;
import com.alex.banking.service.app.service.userService.BankUserDataBaseInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {



    @Mock
    private BankUserDataBaseInterface bankUserDataBaseConnector;

    private AuthService authService;

    @BeforeEach
    void setUp() {
        authService = new AuthService(bankUserDataBaseConnector);
    }

    @Test
    void shouldReturnEmptyIfUserNotFound() {

        String name = "john";
        String password = "password";
        when(bankUserDataBaseConnector.findByAccountName(name)).thenReturn(Optional.empty());


        Optional<String> result = authService.authenticate(name, password);


        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnEmpty_IfPasswordDoesNotMatch() {

        String name = "john";
        String password = "wrong-password";
        BankUser user = new BankUser();
        user.setAccountName(name);
        user.setPassword(new BCryptPasswordEncoder().encode("real-password"));

        when(bankUserDataBaseConnector.findByAccountName(name)).thenReturn(Optional.of(user));


        Optional<String> result = authService.authenticate(name, password);


        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnAccountName_IfAuthenticated() {

        String name = "john";
        String rawPassword = "secret";
        String encodedPassword = new BCryptPasswordEncoder().encode(rawPassword);
        BankUser user = new BankUser();
        user.setAccountName(name);
        user.setPassword(encodedPassword);

        when(bankUserDataBaseConnector.findByAccountName(name)).thenReturn(Optional.of(user));


        Optional<String> result = authService.authenticate(name, rawPassword);


        assertTrue(result.isPresent());
        assertEquals(name, result.get());
    }




}