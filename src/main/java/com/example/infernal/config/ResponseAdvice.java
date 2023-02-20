package com.example.infernal.config;

import com.example.infernal.util.R.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: khr
 * \* Date: 2022/8/9
 * \* Time: 11:06
 * \* Description:
 * \
 */
@RestControllerAdvice
public class  ResponseAdvice implements ResponseBodyAdvice<Object> {
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 是否开启功能 true：是
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, org.springframework.http.server.ServerHttpRequest request, org.springframework.http.server.ServerHttpResponse response) {

        System.out.println(body+"统一返回body");
        //处理字符串类型数据，如果Controller返回String的话，SpringBoot是直接返回.
        if(body instanceof String){
            try {
                System.out.println("字符串类型body");
                return objectMapper.writeValueAsString(R.success(body));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        //返回类型是否已经封装
        if(body instanceof R){
            System.out.println("封装完毕body");
            return body;
        }
        System.out.println("统一返回body不是字符串也没封装号");
        return R.success(body);
    }

    /**
     * 处理返回结果
     */

}
