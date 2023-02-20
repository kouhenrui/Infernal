package com.example.infernal.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.infernal.pojo.Manager;
import com.example.infernal.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ManagerDao {
    @Select("select * from manager where account=#{account}")
    Manager LoginByAccount(String account);

    @Update("update manager set access_token=#{access_token} where id=#{id}")
    void updateToken(String access_token,String id);

    @Select("select * from user")
    List<User> findAllUser();
}
