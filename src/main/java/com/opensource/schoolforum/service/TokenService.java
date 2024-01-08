package com.opensource.schoolforum.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.opensource.schoolforum.entity.User;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {
    /**
     * 过期时间12小时
     */
    private static final long EXPIRE_TIME = 601 * 60 * 1000 * 12*30*12;

    public String getToken(User user) {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        String token="";
        token= JWT.create().withAudience(user.getUseremail()) // 将 user id 保存到 token 里面
                .withExpiresAt(date) //五分钟后token过期
                .sign(Algorithm.HMAC256(user.getPassword())); // 以 password 作为 token 的密钥
        return token;
    }
}