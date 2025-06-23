package com.alex.banking.service.app.service.userService;

import com.alex.banking.service.app.exception.UserNotFoundException;
import com.alex.banking.service.app.models.BankUser;
import com.alex.banking.service.app.models.Payment;
import com.alex.banking.service.app.repository.PaymentRepository;
import com.alex.banking.service.app.repository.UserRepository;
import com.alex.banking.service.app.service.RoleKeeper;
import com.alex.banking.service.app.service.authorizationService.BCryptPasswordEncoder;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BankUserDataBaseConnectorTest {


    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleKeeper roleKeeper;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private BankUserDataBaseConnector bankUserDataBaseConnector;

    private BankUser testUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bankUserDataBaseConnector = new BankUserDataBaseConnector(userRepository, roleKeeper, bCryptPasswordEncoder, paymentRepository);

        testUser = new BankUser();
        testUser.setAccountName("user1");
        testUser.setPassword("plainPassword");
    }

    @Test
    void createBankUser_shouldReturnConflict_ifAccountNameExists() {
        when(userRepository.findByAccountName("user1")).thenReturn(Optional.of(new BankUser()));

        ResponseEntity<String> response = bankUserDataBaseConnector.createBankUser(testUser);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Account name already used", response.getBody());
    }

    @Test
    void createBankUser_shouldCreateUserSuccessfully() {
        when(userRepository.findByAccountName("user1")).thenReturn(null);
        when(roleKeeper.getROLE_USER()).thenReturn("USER");
        when(bCryptPasswordEncoder.encode("plainPassword")).thenReturn("encodedPassword");
        when(userRepository.save(any())).thenReturn(testUser);

        ResponseEntity<String> response = bankUserDataBaseConnector.createBankUser(testUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("{\"status\":\"success\"}", response.getBody());
        verify(userRepository).save(any(BankUser.class));
    }


    @Test
    void deleteBankUser_shouldDeleteUser() {
        doNothing().when(userRepository).delete(testUser);

        boolean result = bankUserDataBaseConnector.deleteBankUser(testUser);

        assertFalse(result);
        verify(userRepository).delete(testUser);
    }




    @Test
    void findById_userExists_returnsUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        BankUser result = bankUserDataBaseConnector.findById(1L);

        assertEquals(testUser, result);
    }

    @Test
    void findById_userNotFound_throwsException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> bankUserDataBaseConnector.findById(1L));
    }



    @Test
    void updateBankUser_success() {
        when(userRepository.save(testUser)).thenReturn(testUser);

        boolean result = bankUserDataBaseConnector.updateBankUser(testUser);

        assertTrue(result);
    }

    @Test
    void getUserForInfo_userExists_returnsSanitizedUser() {
        testUser.setPassword("securePassword");
        when(userRepository.findByAccountName("sender")).thenReturn(Optional.of(testUser));

        Optional<BankUser> result = bankUserDataBaseConnector.getUserForInfo("sender");

        assertTrue(result.isPresent());
        assertEquals("", result.get().getPassword()); // password should be blanked out
        assertEquals("sender", result.get().getAccountName());
    }

    @Test
    void getUserForInfo_userNotFound_returnsEmptyOptional() {
        when(userRepository.findByAccountName("sender")).thenReturn(Optional.empty());

        Optional<BankUser> result = bankUserDataBaseConnector.getUserForInfo("sender");

        assertTrue(result.isEmpty());
    }




    @Test
    void getAllBankUsersByAccountName_userFound_returnsUsers() {
        Payment payment = new Payment();
        payment.setRecipientUsername("recipient");
        List<Payment> payments = List.of(payment);
        List<BankUser> relatedUsers = List.of(new BankUser());

        when(userRepository.findByAccountName("user1")).thenReturn(Optional.of(testUser));
        when(paymentRepository.findBySenderID(1L)).thenReturn(payments);
        doReturn(relatedUsers).when(bankUserDataBaseConnector).getAllBankUsersByPayments(payments);

        ResponseEntity<List<BankUser>> response = bankUserDataBaseConnector.getAllBankUsersByAccountName("user1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(relatedUsers, response.getBody());
    }




    @Test
    void getAllBankUsersByAccountName_userNotFound_returnsNotFound() {
        when(userRepository.findByAccountName("unknown")).thenReturn(Optional.empty());

        ResponseEntity<List<BankUser>> response = bankUserDataBaseConnector.getAllBankUsersByAccountName("unknown");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }






    @Test
    void getAllBankUsersByAccountName_emptyResult_returnsOkWithEmptyBody() {
        when(userRepository.findByAccountName("user1")).thenReturn(Optional.of(testUser));
        when(paymentRepository.findBySenderID(1L)).thenReturn(Collections.emptyList());
        doReturn(Collections.emptyList()).when(bankUserDataBaseConnector).getAllBankUsersByPayments(Collections.emptyList());

        ResponseEntity<List<BankUser>> response = bankUserDataBaseConnector.getAllBankUsersByAccountName("user1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
    }





    @Test
    void changePassword_success() {
        when(userRepository.findByAccountName("user1")).thenReturn(Optional.of(testUser));

        ResponseEntity<Void> response = bankUserDataBaseConnector.changePassword("user1", "oldpass", "newpass");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userRepository).save(any(BankUser.class));
    }





    @Test
    void changePassword_userNotFound_returnsNotFound() {
        when(userRepository.findByAccountName("unknown")).thenReturn(Optional.empty());

        ResponseEntity<Void> response = bankUserDataBaseConnector.changePassword("unknown", "oldpass", "newpass");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }





    @Test
    void changePassword_wrongOldPassword_returnsUnauthorized() {
        testUser.setPassword(new BCryptPasswordEncoder().encode("differentPassword"));
        when(userRepository.findByAccountName("user1")).thenReturn(Optional.of(testUser));

        ResponseEntity<Void> response = bankUserDataBaseConnector.changePassword("user1", "wrongpass", "newpass");

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }






    @Test
    void changePassword_exceptionThrown_returnsUnauthorized() {
        when(userRepository.findByAccountName("user1")).thenReturn(Optional.of(testUser));
        doThrow(new RuntimeException()).when(userRepository).save(any(BankUser.class));

        ResponseEntity<Void> response = bankUserDataBaseConnector.changePassword("user1", "oldpass", "newpass");

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
}