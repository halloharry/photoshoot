package com.photo.master.data.dto.request.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChangesPasswordRequest {

    private String currentPassword;
    private String newPassword;
    private String confirmPassword;
}
