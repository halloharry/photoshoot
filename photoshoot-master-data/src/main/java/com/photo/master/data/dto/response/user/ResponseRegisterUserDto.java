package com.photo.master.data.dto.response.user;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.photo.master.data.dto.request.user.RequestUserProfileDto;
import com.photo.master.data.enumeration.Role;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseRegisterUserDto {
    private Long id;
    private RequestUserProfileDto userProfileDto;
    private String username;
    private String email;
    private String password;
    private Role role;
    private boolean mfaEnabled;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String accessToken;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String refreshToken;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String secretImageUri;
}
