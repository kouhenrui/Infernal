package com.example.infernal.pojo;

import lombok.Data;

/**
 * @auther khr
 * @date 2022/8/18 11:42
 */
@Data
public class Info {
    private String id;
    private String name;
    private Integer role;
    private String account;
    private String phone;

    public String getId(String id) {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName(String name) {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRole(Integer role) {
        return this.role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
