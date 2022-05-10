package org.csu.store.util;

import org.csu.store.common.CommonResponse;
import org.csu.store.common.ResponseCode;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandle {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResponse<String> handleValidatedException(ConstraintViolationException exception) {
        return CommonResponse.createForError(ResponseCode.ARGUMENTILLEGAL.getCode(), exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public CommonResponse<String> handleValidException(MethodArgumentNotValidException exception) {
        return CommonResponse.createForError(
                ResponseCode.ARGUMENTILLEGAL.getCode(), formatValidErrorsMessage(exception.getAllErrors()));
    }

    private String formatValidErrorsMessage(List<ObjectError> errors) {
        StringBuffer errorMessage = new StringBuffer();
        errors.forEach(error -> errorMessage.append(error.getDefaultMessage()).append(","));
        errorMessage.deleteCharAt(errorMessage.length() - 1);
        return errorMessage.toString();
    }
}
