package com.alex.banking.service.app.service.authorizationService;

import java.util.Optional;

public interface AuthServiceInterface {
    public Optional<String> authenticate(String name, String password);
}
