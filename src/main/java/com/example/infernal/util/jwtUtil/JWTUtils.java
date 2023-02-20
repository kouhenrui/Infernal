package com.example.infernal.util.jwtUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import com.example.infernal.pojo.Info;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.naming.AuthenticationException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: khr
 * \* Date: 2022/8/5
 * \* Time: 15:15
 * \* Description:
 * \
 */

@Component
@ConfigurationProperties(prefix = "jwt")
public class JWTUtils {
    /**
     * 定义 token 返回头部
     */
    public static String header;

    /**
     * token 前缀
     */
    public static String tokenPrefix;

    /**
     * 签名密钥
     */
    public static String secret;

    /**
     * 有效期
     */
    public static long expireTime;

    /**
     * 存进客户端的 token 的 key 名
     */
    public static final String USER_LOGIN_TOKEN = "token";

    /**
     * 设置 token 头部
     *
     * @param header token 头部
     */
    public void setHeader(String header) {
        JWTUtils.header = header;
    }

    /**
     * 设置 token 前缀
     *
     * @param tokenPrefix token 前缀
     */
    public void setTokenPrefix(String tokenPrefix) {
        JWTUtils.tokenPrefix = tokenPrefix;
    }

    /**
     * 设置 token 密钥
     *
     * @param secret token 密钥
     */
    public void setSecret(String secret) {
        JWTUtils.secret = secret;
    }

    /**
     * 设置 token 有效时间
     *
     * @param expireTimeInt token 有效时间
     */
    public void setExpireTime(int expireTimeInt) {
        JWTUtils.expireTime = expireTimeInt;
    }

    /**
     * 获取token
     *
     * @return token
     */
    public static Object getToken(Info map) {
        Calendar instance = Calendar.getInstance();
        //默认令牌过期时间7天
        instance.add(Calendar.DATE, 7);

        JWTCreator.Builder builder = JWT.create();
        builder.withClaim("id",map.getId());
        builder.withClaim("name",map.getName());
        builder.withClaim("role",map.getRole());
//     单个值存放token   builder.withSubject(k,(String)v);

        String token = tokenPrefix + builder.withExpiresAt(instance.getTime())
                .withExpiresAt(instance.getTime())//1000*60*60*24 new Date(System.currentTimeMillis() +expireTime )
                .sign(Algorithm.HMAC512(secret));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        JSONObject createToken = new JSONObject();
        createToken.put("token", token);
        createToken.put("exp", dateFormat.format(instance.getTime()));

        return createToken;
//      return tokenPrefix + JWT.create()
//                .withSubject(sub)
//                .withExpiresAt(new Date(System.currentTimeMillis() + expireTime))
//                .sign(Algorithm.HMAC512(secret));

    }

    /**
     * 验证token合法性 成功返回token
     */
    public static String validateToken(String token) {
        try {
            Verification verification = JWT.require(Algorithm.HMAC512(secret));
            JWTVerifier jwtVerifier = verification.build();
            // 去除 token 的前缀
            String noPrefixToken = token.replace(tokenPrefix, "");
            DecodedJWT decodedJwt = jwtVerifier.verify(noPrefixToken);
            System.out.print(decodedJwt+"00000000000000000000");
            if (decodedJwt != null) {
                return noPrefixToken;
            }
            return "";
        } catch (TokenExpiredException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return "";
    }


    /**
     * 验证token，返回用户信息
     */
    public static Object getTokenInfo(String token) {
        Verification verification = JWT.require(Algorithm.HMAC512(secret));
        JWTVerifier jwtVerifier = verification.build();
        // 去除 token 的前缀
        String noPrefixToken = token.replace(tokenPrefix, "");
//        System.out.println(noPrefixToken);
        DecodedJWT decodedJwt = jwtVerifier.verify(noPrefixToken);
        if (decodedJwt != null) {
            Info info=new Info();
            info.setId(String.valueOf(decodedJwt.getClaim("id")));
            info.setName(String.valueOf(decodedJwt.getClaim("name")));
            info.setRole(decodedJwt.getClaim("role").asInt());
            return info;
        }
        return null;
    }

    /**
     * 检查 token 是否需要更新
     *
     * @param token token 值
     * @return 过期时间
     */
    public static boolean isNeedUpdate(String token) throws Exception {
        // 获取 token 过期时间
        Date expiresAt = null;
        try {
            expiresAt = JWT.require(Algorithm.HMAC512(secret))
                    .build()
                    .verify(token.replace(tokenPrefix, ""))
                    .getExpiresAt();
        } catch (TokenExpiredException e) {
            return true;
        } catch (Exception e) {
            throw new Exception(("token 验证失败"));
        }
        // 需要更新
        return (expiresAt.getTime() - System.currentTimeMillis()) < (expireTime >> 1);
    }
}
