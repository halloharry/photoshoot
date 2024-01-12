package com.photo.auth.security.endpoint.test;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/management")
public class ManagementController {

    @GetMapping
    public String getAdmin() {
        return "get management role";
    }

    @PutMapping
    public String putmanagement() {
        return "put management role";
    }

    @PostMapping
    public String postmanagement() {
        return "post management role";
    }

    @DeleteMapping
    public String deletemanagement() {
        return "delete management role";
    }


}
