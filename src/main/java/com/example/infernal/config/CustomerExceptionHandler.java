package com.example.infernal.config;

import com.alibaba.fastjson.JSONObject;
import com.example.infernal.util.R.R;
import com.example.infernal.util.R.RCode;
import com.example.infernal.util.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.naming.AuthenticationException;
import javax.security.auth.message.AuthException;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: khr
 * \* Date: 2022/8/9
 * \* Time: 11:09
 * \* Description:
 * \
 */
@RestControllerAdvice
@Slf4j
public class CustomerExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = AuthenticationException.class)
    @ResponseStatus(HttpStatus.OK)
    public R AuthenticationException(AuthenticationException e) {
        log.info("401", e);
        return R.fail(RCode.AUTH_ERROR.code(), RCode.AUTH_ERROR.message());
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public R Execption(Exception ex) {
        if (ex instanceof BindException) {
            System.out.println("格式错误");
            return R.fail(RCode.RESPONSE_CODE_TYPE_NOT_ACCEPTABLE.code(), RCode.RESPONSE_CODE_TYPE_NOT_ACCEPTABLE.message());
        }else if (ex instanceof HttpRequestMethodNotSupportedException) {
            System.out.println("方法错误");
            return R.fail(RCode.METHOD_NOT_ALLOWED.code(), RCode.METHOD_NOT_ALLOWED.message());
        } else if (ex instanceof PersistenceException) {
            return R.fail(RCode.DATABASE_OPERATION_FAILED.code(),RCode.DATABASE_OPERATION_FAILED.message());
        } else {
            System.out.println("普通错误"+ex);
            return R.fail(RCode.SYSTEM_ERROR.code(), RCode.SYSTEM_ERROR.message());
        }

    }

    /**
     * 捕获业务异常
     *
     * @param
     * @return
     */
    @ExceptionHandler(value = BusinessException.class)
    @ResponseStatus(HttpStatus.OK)
    public R handleBusinessException(BusinessException e, Exception ex) {
        System.out.println(
                ex + "ex"
        );
        return R.fail(e.getCode(), e.getMessage());
    }



    /**
     * 方法参数校验异常
     *
     * @param bindException
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    public R methodArgumentNotValidException(MethodArgumentNotValidException bindException) {
        BindingResult bindingResult = bindException.getBindingResult();
        System.out.println(bindingResult);

        return R.fail(RCode.RESPONSE_CODE_PARAM_ERR.code(), RCode.RESPONSE_CODE_PARAM_ERR.message());
    }





    /**
     * 405错误
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.OK)
    public R request405(HttpServletRequest request, HttpRequestMethodNotSupportedException e) {
        System.out.println("HttpRequestMethodNotSupportedException1211111");
        return R.fail(RCode.METHOD_NOT_ALLOWED.code(),RCode.METHOD_NOT_ALLOWED.message());
//        return R(request, RCode.RESPONSE_CODE_METHOD_NOT_SUPPORT, e);

    }


    /**
     * 406错误
     */
    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    @ResponseStatus(HttpStatus.OK)
    public R request406(HttpServletRequest request, HttpMediaTypeNotAcceptableException e) {
        System.out.println("HttpMediaTypeNotAcceptableException000000000000000000000000000000000000000000000000");
        return R.success();
//        return R(request, RCode.RESPONSE_CODE_TYPE_NOT_ACCEPTABLE, e);

    }


}


