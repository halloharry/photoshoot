package com.photo.auth.security.endpoint.test;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/demo")
public class DemoController {

    @GetMapping
    public ResponseEntity<String> helo() {
        return ResponseEntity.ok("helo");
    }
}
