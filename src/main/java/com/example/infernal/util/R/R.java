package com.example.infernal.util.R;

import lombok.Data;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: khr
 * \* Date: 2022/8/5
 * \* Time: 14:23
 * \* Description:
 * \
 */
@Data
public class R<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer code;

    private String message;

    private T data;
    private String time;

    private R() {
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.time=dateFormat.format(new Date());
    }

    public R(RCode rCode, T data) {
        this.code = rCode.code();
        this.message = rCode.message();
        this.data = data;
    }

    private void setResultCode(RCode resultCode) {
        this.code = resultCode.code();
        this.message = resultCode.message();
    }

    private void setResultDta(T data) {
        this.data=data;
    }

    // 返回成功
    public static R success() {
        R result = new R();
        result.setResultCode(RCode.SUCCESS);
        return result;
    }
    /**
     * 成功
     */
    public static <T> R<T> success(T data) {
        R<T> result = new R<T>();
        result.setResultCode(RCode.SUCCESS);
        result.setData(data);
        return result;
    }

    public static <T> R<T> successed(T data,String message) {
        R<T> result = new R<T>();
        result.setResultCode(RCode.SUCCESS);
        result.setData(data);
        result.setMessage(message);
        return result;
    }

    /**
     * 失败
     */
    public static <T> R<T> fail(int code, String message) {
        R<T> result=new R<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
    public static <T> R<T> faild(T data) {
        R<T> result=new R<>();
//        result.setMessage();
//        result.setResultCode()
        result.setData(data);
        return result;
    }
}


