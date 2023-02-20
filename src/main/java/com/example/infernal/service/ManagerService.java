package com.example.infernal.service;

import com.example.infernal.pojo.Manager;
import com.example.infernal.pojo.User;

import java.util.List;

public interface ManagerService {
    Manager LoginByAccount(String account);
    void updateToken(String token,String id);

    List<User> findAllUser();
}
