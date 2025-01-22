package com.korit.servlet_study.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseDto<T> {
    private int status;
    private String message;
    private T data;

    public static <T> ResponseDto<T> success(T data) {
        return new ResponseDto<>(200, "success", data);
    }
    public static <T> ResponseDto<T> fail(T data) {
        return new ResponseDto<>(400, "fail", data);
    }
}
