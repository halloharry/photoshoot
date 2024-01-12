package com.photo.master.data.enumeration;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ApplicationConstant {

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ContextPath {
        public static final String API_V1 = "/api/v1";
        public static final String API_AUTH = "/api/v0/auth";
        public static final String USERS = "/users";
        public static final String REGISTER_USER = "/register";
        public static final String LOGIN = "/authenticated";
        public static final String REFRESH_TOKEN = "/refresh/token";
        public static final String VERIFY = "/verify/user";
    }

}
