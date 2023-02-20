package com.example.infernal.util.exception;

import com.example.infernal.util.R.R;
import com.example.infernal.util.R.RCode;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @auther khr
 * @date 2022/8/19 14:51
 */

@RestController
public class SystemExceptionController implements ErrorController {

    private static final String ERROR_PATH = "/error";

    @RequestMapping(value=ERROR_PATH)
    public Object handleError(HttpServletRequest request, HttpServletResponse response){
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if(Objects.equals(HttpStatus.NOT_FOUND.value(),statusCode)){
            return R.fail(RCode.RESPONSE_CODE_NOT_FOUND.code(),RCode.RESPONSE_CODE_PARAM_ERR.message());
        }else {
            return R.fail(RCode.SERVER_BUSY.code(),RCode.SERVER_BUSY.message());
        }
    }

}
