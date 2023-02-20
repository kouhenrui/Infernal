package com.example.infernal.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Data
@TableName("manager")
public class Manager {
    @Id()
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    private String account;
    private String password;
    private String salt;

    private Integer role;
    @TableField("access_token")
    private String accessToken;
    @CreatedDate()
    @TableField("created_at")
    private Date createdAt;
    @LastModifiedDate()
    @TableField("updated_at")
    private Date updatedAt;
}
