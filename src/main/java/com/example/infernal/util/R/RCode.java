package com.example.infernal.util.R;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: khr
 * \* Date: 2022/8/5
 * \* Time: 14:20
 * \* Description:
 * \
 */
public enum RCode {

    /* 成功状态码 */
    SUCCESS(20000, "成功"),
    USER_LOGIN_ERROR(2002, "账号不存在或密码错误"),


    RESPONSE_CODE_PARAM_ERR(400, "参数格式错误"),
    AUTH_ERROR(401, "没有通过权限验证！"),
    METHOD_NOT_ALLOWED(403, "请求方式出错！"),
    RESPONSE_CODE_NOT_FOUND(404, "页面未找到"),
    RESPONSE_CODE_TYPE_NOT_ACCEPTABLE(406, "参数类型错误"),
    SERVER_BUSY(503, "服务器正忙，请稍后再试!"),
    DATABASE_OPERATION_FAILED(504, "数据库操作失败"),
    SYSTEM_ERROR(10000, "系统异常，请稍后重试"),
    UPLOAD_TYPE_FILED(10004, "文件类型为空，不符合要求");


    private Integer code;
    private String message;

    private RCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private RCode() {
    }

    public Integer code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }
}


