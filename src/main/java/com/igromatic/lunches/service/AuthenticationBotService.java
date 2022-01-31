package com.igromatic.lunches.service;

import com.igromatic.lunches.dto.RequestDTO;
import com.igromatic.lunches.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationBotService {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationBotService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    public ResponseDTO authentication(RequestDTO requestDTO) {
        return authenticationService.authentication(requestDTO);
    }
}
