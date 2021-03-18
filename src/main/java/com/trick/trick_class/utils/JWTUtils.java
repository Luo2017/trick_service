package com.trick.trick_class.utils;

import com.trick.trick_class.model.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JWTUtils {

    /**
     * 过期时间，一周,60000表示60s
     */
    private  static final long EXPIRE = 60000 * 60 * 24 * 7;


    /**
     * 加密秘钥
     */
    private  static final String SECRET = "trick_class_key";


    /**
     * 令牌前缀
     */
    private  static final String TOKEN_PREFIX = "trick_class";


    /**
     * subject
     */
    private  static final String SUBJECT = "trick_class";


    /**
     * 根据用户信息，生成令牌
     * @param user
     * @return
     */
    public static String geneJsonWebToken(User user){

        String token = Jwts.builder().setSubject(SUBJECT)
                .claim("head_img",user.getHeadImg())
                .claim("id",user.getId())
                .claim("name",user.getName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .signWith(SignatureAlgorithm.HS256,SECRET).compact();

        token = TOKEN_PREFIX + token;

        return token;
    }


    /**
     * 校验token的方法
     * @param token
     * @return
     */
    public static Claims checkJWT(String token){

        try{

            final  Claims claims = Jwts.parser().setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX,"")).getBody();

            return claims;

        }catch (Exception e){
            return null;
        }

    }
}
