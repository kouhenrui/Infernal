package com.example.infernal.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.infernal.dto.reqDto.AddShopReq;
import com.example.infernal.dto.reqDto.ManagerLoginReq;
import com.example.infernal.pojo.Info;
import com.example.infernal.pojo.Manager;
import com.example.infernal.pojo.User;
import com.example.infernal.service.Impl.ManagerImpl;
import com.example.infernal.util.R.RCode;
import com.example.infernal.util.exception.BusinessException;
import com.example.infernal.util.jwtUtil.JWTUtils;
import com.example.infernal.util.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.util.List;
import static com.example.infernal.util.Md5Corpy.generateUUID;
import static com.example.infernal.util.Md5Corpy.md5;
import static com.example.infernal.util.jwtUtil.JWTUtils.getToken;

/**
 * @Author: khr
 * @Description:
 * @DateTime: 2022/8/12 17:14
 * @Params:
 * @Return
 */
@Slf4j
@RestController
@RequestMapping("/manager")
public class ManagerController {
    @Autowired
    ManagerImpl managerService;
    @Autowired
    RedisUtil redisUtil;
//    @Value("${uploadPath}")
//    private String path;

    @PostMapping("/login")
    public Object login(@RequestBody ManagerLoginReq managerLoginReq) {
        Manager manager = managerService.LoginByAccount(managerLoginReq.getAccount());
        if (manager == null) throw new BusinessException(RCode.USER_LOGIN_ERROR);
        Boolean access = redisUtil.exists("manager:" + manager.getAccessToken());
        String token = "";
        JSONObject RR = new JSONObject();
        if (access) {
            JSONObject info = (JSONObject) redisUtil.get("manager:" + manager.getAccessToken());
            RR.put("token", info.get("token"));
            RR.put("exp", info.get("exp"));
            return RR;
        } else {
            String salt = manager.getSalt();
            String md5pwd = md5(salt + managerLoginReq.getPassword());
            if (md5pwd.equals(manager.getPassword())) {
                Info in = new Info();
                in.setId(manager.getId());
                in.setRole(manager.getRole());
                RR = (JSONObject) getToken(in);
                token = (String) RR.get("token");
                String exp = (String) RR.get("exp");
                String access_token = generateUUID();
                managerService.updateToken(access_token, manager.getId());
                JSONObject redisCach = new JSONObject();
                redisCach.put("token", token);
                redisCach.put("id", manager.getId());
                redisCach.put("exp", exp);
                redisUtil.setManager("manager:" + access_token, redisCach);
                return RR;
            } else {
                throw new BusinessException(RCode.USER_LOGIN_ERROR);//RCode.USER_LOGIN_ERROR);
            }
        }
    }

    @GetMapping("/info")
    public Object info(HttpServletRequest req) {
        String token = req.getHeader("authorization");
        return JWTUtils.getTokenInfo(token);
    }

    @GetMapping("/get/all/users")
    public List<User> allManager() {
        List<User> userList = managerService.findAllUser();
        System.out.println(userList);
        return userList;
    }

//    /**
//     * 1.文件保存在服务器，url地址保存在数据库
//     * 上传成功之后返回成功保存的url地址
//     */
//    @PostMapping("/upload")
//    public @ResponseBody Object upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
//        System.out.println(path);
//        if (!file.isEmpty()) {
////            try {
//                // 如果目录不存在则创建
//                File uploadDir = new File(path);
//                if (!uploadDir.exists()) {
//                    uploadDir.mkdir();
//                }
//                String OriginalFilename = file.getOriginalFilename();//获取原文件名
//                String suffixName = OriginalFilename.substring(OriginalFilename.lastIndexOf("."));//获取文件后缀名
//                System.out.println(suffixName);
//                //重新随机生成名字
////            String filename = UUID.randomUUID().toString() +suffixName;
//                File localFile = new File(path + "\\" + OriginalFilename);
//
//                //localFile.transferTo(uploadDir); //把上传的文件保存至本地
//                return OriginalFilename;//上传成功，返回保存的文件地址
////            } catch (Exception e) {
////                throw new BusinessException(RCode.SERVER_BUSY);
////            }
//
//        } else {
//            System.out.println("文件为空");
//            return RCode.SERVER_BUSY;
//        }
//    }

    @PostMapping("/add/shop")
    public Object addShop(@Valid AddShopReq addShopReq) {
//        log.info(addShopReq);
        System.out.println(addShopReq);
        return RCode.SUCCESS;
    }
    @PostMapping("/error")
    public Object error()throws Exception{
        throw new Error("错误");
    }
}
