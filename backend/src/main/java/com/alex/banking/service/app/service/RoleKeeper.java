package com.alex.banking.service.app.service;


import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class RoleKeeper {
    private final String ROLE_ADMIN = "ADMIN";
    private final String ROLE_USER = "USER";
}
