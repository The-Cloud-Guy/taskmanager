package com.techguy.task.domain.dto;

import lombok.*;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class ApiErrorResponse {
    private int status;
    private String message;
}
