package org.csu.store.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponse<T> {
    private int status;
    private String message;
    private T data;

    @JsonIgnore
    public boolean isSuccess() {
        // 构造CommonResponse时，status赋值只能是ResponseCode中的值
        return this.status == ResponseCode.SUCCESS.getCode();
    }


    // 返回成功信息
    // 1.code
    // 2.code + message
    // 3.code + data
    // 4.code + message + data

    public static <T> CommonResponse<T> createForSuccess() {
        return new CommonResponse<T>(ResponseCode.SUCCESS.getCode());
    }

    public static <T> CommonResponse<T> createForSuccessMessage(String message) {
        return new CommonResponse<>(ResponseCode.SUCCESS.getCode(), message);
    }

    public static <T> CommonResponse<T> createForSuccess(T data) {
        return new CommonResponse<>(ResponseCode.SUCCESS.getCode(), data);
    }

    public static <T> CommonResponse<T> createForSuccess(String message, T data) {
        return new CommonResponse<T>(ResponseCode.SUCCESS.getCode(), message, data);
    }


    // 返回错误信息
    // 1.code + description
    // 2.code + message  *
    // 3.自定义code + message

    public static <T> CommonResponse<T> createForError() {
        return new CommonResponse<T>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDescription());
    }

    public static <T> CommonResponse<T> createForError(String message) {
        return new CommonResponse<T>(ResponseCode.ERROR.getCode(), message);
    }

    public static <T> CommonResponse<T> createForError(int code, String message) {
        return new CommonResponse<T>(code, message);
    }

    // 构造函数 1 x 2 x 2
    private CommonResponse(int status) {
        this.status = status;
    }

    private CommonResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    private CommonResponse(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    private CommonResponse(int status, T data) {
        this.status = status;
        this.data = data;
    }
}