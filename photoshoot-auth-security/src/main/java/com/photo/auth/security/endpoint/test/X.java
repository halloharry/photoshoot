package com.photo.auth.security.endpoint.test;

import com.photo.auth.security.service.AuthenticationService;
import com.photo.master.data.dto.request.user.AuthenticationRequestDto;
import com.photo.master.data.dto.response.user.AuthenticationResponseDto;
import com.photo.master.data.enumeration.ApplicationConstant;
import com.photo.master.data.util.exception.security.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApplicationConstant.ContextPath.API_AUTH)
@RequiredArgsConstructor
public class X {
    private final AuthenticationService authService;
    @PostMapping("/authenticate1")
    public ResponseEntity<AuthenticationResponseDto> authenticate(@RequestBody AuthenticationRequestDto request) throws ServiceException {

        return ResponseEntity.ok(authService.authenticate(request));

    }
}
