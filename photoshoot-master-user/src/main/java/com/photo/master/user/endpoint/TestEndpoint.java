package com.photo.master.user.endpoint;


import com.photo.master.data.model.user.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class TestEndpoint {

    @GetMapping({"/get/{page}/{size}",
            "public/get"})
    private String data(@PathVariable(name = "page", required = false) Integer page,
                        @PathVariable(name = "size", required = false) Integer size) {

        AuthUser authUser = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return "halo";
    }


    @GetMapping({"/get/{page}/{size}",
            "api/get"})
    private String datadata(@PathVariable(name = "page", required = false) Integer page,
                        @PathVariable(name = "size", required = false) Integer size) {


        return "joko";
    }
}
