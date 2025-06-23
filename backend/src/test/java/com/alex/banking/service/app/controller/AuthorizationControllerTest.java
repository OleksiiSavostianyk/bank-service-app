package com.alex.banking.service.app.controller;

import com.alex.banking.service.app.models.BankUser;
import com.alex.banking.service.app.models.json_classes.LoginRequest;
import com.alex.banking.service.app.service.authorizationService.AuthServiceInterface;
import com.alex.banking.service.app.service.userService.BankUserDataBaseInterface;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorizationControllerTest {

    @Mock
    private BankUserDataBaseInterface bankUserDataBaseConnector;

    @Mock
    private AuthServiceInterface authService;

    @InjectMocks
    private AuthorizationController authorizationController;

    @Test
    void registerUser_successfulRegistration_returnsOkResponse() {

        BankUser user = new BankUser();
        ResponseEntity<String> expectedResponse = ResponseEntity.ok("success");

        when(bankUserDataBaseConnector.createBankUser(user)).thenReturn(expectedResponse);


        ResponseEntity<String> actualResponse = authorizationController.registerUser(user);


        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals("success", actualResponse.getBody());
        verify(bankUserDataBaseConnector).createBankUser(user);
    }

    @Test
    void registerUser_whenExceptionThrown_returnsBadRequest() {

        BankUser user = new BankUser();
        when(bankUserDataBaseConnector.createBankUser(user))
                .thenThrow(new RuntimeException("User already exists"));


        ResponseEntity<String> response = authorizationController.registerUser(user);


        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("User already exists", response.getBody());
        verify(bankUserDataBaseConnector).createBankUser(user);
    }




    @Test
    void login_successfulAuthentication_returnsOk() {

        LoginRequest request = new LoginRequest();
        request.setUsername("john");
        request.setPassword("1234");
        when(authService.authenticate("john", "1234")).thenReturn(Optional.of("john"));


        ResponseEntity<String> response = authorizationController.login(request);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("john", response.getBody());
    }

    @Test
    void login_failedAuthentication_returnsUnauthorized() {

        LoginRequest request = new LoginRequest();
        request.setUsername("john");
        request.setPassword("wrongpass");
        when(authService.authenticate("john", "wrongpass")).thenReturn(Optional.empty());


        ResponseEntity<String> response = authorizationController.login(request);


        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void login_authServiceThrowsException_throwsException() {

        LoginRequest request = new LoginRequest();
        request.setUsername("john");
        request.setPassword("1234");
        when(authService.authenticate("john", "1234")).thenThrow(new RuntimeException("Service failed"));


        assertThrows(RuntimeException.class, () -> authorizationController.login(request));
    }


}