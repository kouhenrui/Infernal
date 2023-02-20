package com.example.infernal.dto.reqDto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: khr
 * \* Date: 2022/8/8
 * \* Time: 14:41
 * \* Description:
 * \
 */
@Data
public class ManagerLoginReq {
    @NotEmpty(message = "密码不能为空")
    String password;

    @NotBlank(message = "用户名不能为空")
    String account;

    String salt;
}
