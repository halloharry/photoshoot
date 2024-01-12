package com.photo.auth.security.endpoint;

import com.photo.auth.security.ChangesPasswordRequest;
import com.photo.auth.security.service.AuthenticationService;
import com.photo.master.data.enumeration.ApplicationConstant;
import com.photo.master.data.util.IResultDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(name = ApplicationConstant.ContextPath.API_V1 + ApplicationConstant.ContextPath.USERS,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public interface UserEndpoint {

    @PatchMapping("/change-password")
    IResultDto<Boolean> changesPassword(@RequestBody ChangesPasswordRequest request, Principal connectedUser, HttpServletRequest httpServletRequest);

}
