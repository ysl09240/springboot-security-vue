package com.sykean.hmhc.config;

import com.sykean.hmhc.common.ResponseCode;
import com.sykean.hmhc.common.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

/**
 * 全局异常捕获
 */
@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {

    @ExceptionHandler(value = Exception.class)
    public Object handle(Exception e, HttpServletRequest request, HttpServletResponse response) {
        if (e instanceof MethodArgumentNotValidException) {
            //参数验证异常
            MethodArgumentNotValidException manve = (MethodArgumentNotValidException) e;
            BindingResult bindingResult = manve.getBindingResult();
            ObjectError objectError = bindingResult.getAllErrors().get(0);
            String message = objectError.getDefaultMessage();
            log.error("数据校验失败:{}", message);
            return new RestResponse(ResponseCode.ERROR.getCode(), message);
        } else if (e instanceof ConstraintViolationException) {
            ConstraintViolationException constraintViolationException = (ConstraintViolationException) e;
            ConstraintViolationImpl<?> constraintViolation = ((ConstraintViolationImpl) constraintViolationException.getConstraintViolations().iterator().next());
            String message = constraintViolation.getMessageTemplate();
            log.error("数据校验失败:{}", message);
            return new RestResponse(ResponseCode.ERROR.getCode(), message);
        }else{
            log.error("exception:", e);
            return RestResponse.error(ResponseCode.UN_KNOW, null);
        }
    }
}