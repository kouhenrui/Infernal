package com.example.infernal.dto.reqDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;


/**
 * \* Created with IntelliJ IDEA.
 * \* User: khr
 * \* Date: 2022/8/8
 * \* Time: 14:41
 * \* Description:
 * \
 */
@Data
//@JsonIgnoreProperties(ignoreUnknown = true)//忽略未知字段
public class AddShopReq {
    @NotNull(message = "参数不为空")
    @Length(min = 1,max=2)
    private Integer password;
    @NotNull(message = "参数不为空")
    private String salt;
    @NotNull(message = "参数不为空")
    @NotBlank(message = "参数不为空")
    private String account;
}
