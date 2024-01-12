package com.photo.master.data.dto.request.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VerificationRequestDto {

    private String email;
    private String code;
}
