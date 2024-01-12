package com.photo.master.data.dto.response.user;

import com.photo.master.data.model.user.AuthUser;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseUserProfileDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
    private LocalDate birtDate;
    private String gender;
    private AuthUser user;
}
