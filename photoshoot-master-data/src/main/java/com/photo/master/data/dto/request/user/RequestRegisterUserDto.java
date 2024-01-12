package com.photo.master.data.dto.request.user;

import com.photo.master.data.enumeration.Role;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestRegisterUserDto {
    private Long id;
    private RequestUserProfileDto userProfile;
    private String username;
    private String email;
    private String password;
    private Role role;
    private boolean mfaEnabled;
}
