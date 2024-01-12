package com.photo.auth.security.endpoint.test;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/seller")
@PreAuthorize("hasRole('SELLER')")
public class SellerController {

    @GetMapping
    @PreAuthorize("hasAuthority('seller:read')")
    public String getAdmin() {
        return "get seller role";
    }

    @PutMapping
    @PreAuthorize("hasAuthority('seller:update')")
    public String putAdmin() {
        return "put seller role";
    }

    @PostMapping
    @PreAuthorize("hasAuthority('seller:create')")
    public String postAdmin() {
        return "post seller role";
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('seller:delete')")
    public String deleteAdmin() {
        return "delete seller role";
    }


}
