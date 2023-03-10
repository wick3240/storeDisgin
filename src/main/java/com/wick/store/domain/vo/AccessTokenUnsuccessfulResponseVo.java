package com.wick.store.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
@Data

public class AccessTokenUnsuccessfulResponseVo implements Serializable {

        private ErrorCode error;
        @JsonProperty("error_description")
        private String errorDescription;

        public AccessTokenUnsuccessfulResponseVo() {
        }

        public AccessTokenUnsuccessfulResponseVo(ErrorCode error, String errorDescription) {
            this.error = error;
            this.errorDescription = errorDescription;
        }

        public enum ErrorCode {
            invalid_request
        }
    }


