package com.electric.business.util;

import io.jsonwebtoken.*;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {

    public static String generateToken(String userid) {
        Map<String, Object> map = new HashMap<>();
        map.put("sub", userid);
        return generateToken(map);
    }

    public static String generateToken(Map<String, Object> map) {
        try {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.MILLISECOND, Calendar.MILLISECOND + 180 * 1000);
            String token = Jwts.builder().setHeaderParam("typ","JWT").
                    setClaims(map).setExpiration(c.getTime()).
                    setIssuedAt(new Date()).setIssuer("su").
                    signWith(SignatureAlgorithm.HS256, getSign("secret","sdasdeffffffffffff")).compact();
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public  static byte[] getSign(String txt , String secret){
        try{
            SecretKey secretKey = new SecretKeySpec(secret.getBytes("UTF-8"),"HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(secretKey);
            byte[] bs = txt.getBytes();
            return bs;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static boolean verifyToken(String token){
        try{
            Jwts.parser().require("iss","su").
                    setSigningKey(getSign("secret","sdasdeffffffffffff")).parseClaimsJws(token).getBody();
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
