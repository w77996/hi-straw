package com.w77996.straw.core.jwt;

import com.w77996.straw.core.constant.Constant;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.UUID;

/**
 * @description: jwt工具
 * @author: straw
 **/
@Component
public class JwtHelper {

    /**
     * 解析jwt
     *
     * @param jsonWebToken
     * @param base64Security
     * @return
     */
    public static Claims parseJWT(String jsonWebToken, String base64Security) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(base64Security))
                    .parseClaimsJws(jsonWebToken).getBody();
            return claims;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 生成jwt
     *
     * @param userId
     * @param audience
     * @param issuer
     * @param ttlMillis
     * @param base64Security
     * @return
     */
    public static String createJWT(String userId, String audience, String issuer, long ttlMillis, String base64Security) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        //生成签名密钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(base64Security);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        String uuid = UUID.randomUUID().toString();
        //添加构成JWT的参数
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .claim("project", "Rearm")
                .claim("salt", uuid)
                .setIssuer(issuer)
                .setSubject(userId)
                .setAudience(audience)
                .signWith(signatureAlgorithm, signingKey);
        //添加Token过期时间
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis * 1000;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp).setNotBefore(now);
        }
        //生成JWT
        return builder.compact();
    }

    /**
     * 生成jwt
     *
     * @param userId
     * @return
     */
    public static String createJWT(String userId) {
        String token = JwtHelper.createJWT(userId, Constant.JWT_CLIENT_ID,
                Constant.JWT_NAME, Constant.JWT_EXPIRES_SECOND, Constant.JWT_BASE64_SECRET);
        return token;
    }

}
