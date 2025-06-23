package com.alex.banking.service.app.controller;

import com.alex.banking.service.app.models.BankUser;
import com.alex.banking.service.app.service.userService.BankUserDataBaseInterface;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class BankControllerTest {


    @Mock
    private BankUserDataBaseInterface bankUserDataBaseConnector;


    @InjectMocks
    private BankController bankController;

    @Test
    void getAccountInfo_userFound_returnsOkWithUser() {

        String username = "john";
        BankUser mockUser = new BankUser();
        mockUser.setAccountName(username);
        when(bankUserDataBaseConnector.getUserForInfo(username))
                .thenReturn(Optional.of(mockUser));


        ResponseEntity<BankUser> response = bankController.getAccountInfo(username);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockUser, response.getBody());
    }

    @Test
    void getAccountInfo_userNotFound_returnsNotFound() {

        String username = "unknown";
        when(bankUserDataBaseConnector.getUserForInfo(username))
                .thenReturn(Optional.empty());


        ResponseEntity<BankUser> response = bankController.getAccountInfo(username);


        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void getAllUsersWithTransactions_whenUsersExist_returnsListOfUsers() {

        BankUser bankUser1 = new BankUser();
        bankUser1.setFirstName("john");
        bankUser1.setPassword("password1");

        BankUser bankUser2 = new BankUser();
        bankUser1.setFirstName("john");
        bankUser1.setPassword("password2");


        String username = "john";
        List<BankUser> userList = List.of(
              bankUser1,bankUser2
        );

        ResponseEntity<List<BankUser>> mockResponse = ResponseEntity.ok(userList);
        when(bankUserDataBaseConnector.getAllBankUsersByAccountName(username)).thenReturn(mockResponse);


        ResponseEntity<List<BankUser>> response = bankController.getAllUsersWithTransactions(username);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userList, response.getBody());
    }

    @Test
    void getAllUsersWithTransactions_whenNoUsers_returnsEmptyList() {

        String username = "no_user";
        List<BankUser> emptyList = List.of();

        ResponseEntity<List<BankUser>> mockResponse = ResponseEntity.ok(emptyList);
        when(bankUserDataBaseConnector.getAllBankUsersByAccountName(username)).thenReturn(mockResponse);


        ResponseEntity<List<BankUser>> response = bankController.getAllUsersWithTransactions(username);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void changePassword_whenSuccess_returnsOk() {

        String username = "john";
        String oldPassword = "1234";
        String newPassword = "5678";

        when(bankUserDataBaseConnector.changePassword(username, oldPassword, newPassword))
                .thenReturn(ResponseEntity.ok().build());


        ResponseEntity<Void> response = bankController.changePassword(username, oldPassword, newPassword);


        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void changePassword_whenFailure_returnsBadRequest() {

        String username = "john";
        String oldPassword = "wrongOld";
        String newPassword = "newPass";

        when(bankUserDataBaseConnector.changePassword(username, oldPassword, newPassword))
                .thenReturn(ResponseEntity.badRequest().build());


        ResponseEntity<Void> response = bankController.changePassword(username, oldPassword, newPassword);


        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}