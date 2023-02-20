package com.example.infernal.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Data
@TableName("user")
public class User {
    @Id()
    @TableId(type = IdType.ASSIGN_UUID)
    String id;
    String account;
    String password;
    String salt;
    Integer role;
    String access_token;
    @CreatedDate()
    Date created_at;
    @LastModifiedDate()
    Date updated_at;
}
