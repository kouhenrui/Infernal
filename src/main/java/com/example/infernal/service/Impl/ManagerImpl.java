package com.example.infernal.service.Impl;

import com.example.infernal.dao.ManagerDao;
import com.example.infernal.pojo.Manager;
import com.example.infernal.pojo.User;
import com.example.infernal.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerImpl implements ManagerService {

    @Autowired
    ManagerDao managerDao;
    @Override
    public Manager LoginByAccount(String account) {
        return managerDao.LoginByAccount(account);
    }

    public void updateToken(String access_token, String id) {
         managerDao.updateToken(access_token, id);
    }

    @Override
    public List<User> findAllUser() {
        return managerDao.findAllUser();
    }
}
