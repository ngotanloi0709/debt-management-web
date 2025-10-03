package com.ngtnl1.dmw.dto.authentication;

import java.time.Instant;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseDTO {
    Instant timestamp;
    int status;
    String message;
    String path;
    List<ErrorItem> errors;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ErrorItem {
        String message;
        String code;
    }
}
