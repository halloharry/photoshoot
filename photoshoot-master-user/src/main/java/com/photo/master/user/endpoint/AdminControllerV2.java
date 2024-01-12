package com.photo.master.user.endpoint;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminControllerV2 {

    @GetMapping
    public String getAdmin() {

        return "get admin role";
    }

    @PutMapping
    public String putAdmin() {
        return "put admin role";
    }

    @PostMapping
    public String postAdmin() {
        return "post admin role";
    }

    @DeleteMapping
    public String deleteAdmin() {
        return "delete admin role";
    }


}
