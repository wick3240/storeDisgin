package com.wick.store.domain.entiey;

import lombok.Data;


public class AuthResponse {
        private String token;
        private UserEntity user;

        public AuthResponse(String token, UserEntity user) {
        }

        public String getToken() {
                return token;
        }

        public void setToken(String token) {
                this.token = token;
        }

        public UserEntity getUser() {
                return user;
        }

        public void setUser(UserEntity user) {
                this.user = user;
        }
}
