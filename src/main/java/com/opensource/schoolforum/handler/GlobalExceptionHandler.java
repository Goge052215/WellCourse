package com.opensource.schoolforum.handler;

import com.opensource.schoolforum.enums.ResultCode;
import com.opensource.schoolforum.model.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 分割符
     */
    private static final String SEP_COMMA = ",";


    @ResponseBody
    @ExceptionHandler(Exception.class)
    public R<?> handleException(Exception e) {
        log.error(e.getMessage());
        String message = e.getMessage();
        if(message.equals(String.valueOf(ResultCode.UNLOGIN.code()))){
            return R.failure(ResultCode.UNLOGIN);
        }
        return R.failure(ResultCode.ERROR,e.getMessage());
    }

    /**
     * bean参数验证 带requestbody的方式.
     *
     * @param ex RuntimeException
     * @return String
     */
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public  R<?> validExceptionHandler(MethodArgumentNotValidException ex) {
        return R.failure(ResultCode.PARAMETERERROR, ex.getBindingResult().getAllErrors().stream().map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining(",")));
    }

    /**
     * bean参数验证.
     *
     * @param ex RuntimeException
     * @return String
     */
    @ResponseBody
    @ExceptionHandler(BindException.class)
    public  R<?> validExceptionHandler(BindException ex) {
        return  R.failure(ResultCode.PARAMETERERROR, ex.getAllErrors().stream().map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining(SEP_COMMA)));
    }

    /**
     * 单个参数验证.
     *
     * @param ex RuntimeException
     * @return String
     */
    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    public  R<?> validExceptionHandler(ConstraintViolationException ex) {
        return  R.failure(ResultCode.PARAMETERERROR, ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(SEP_COMMA)));
    }
}
