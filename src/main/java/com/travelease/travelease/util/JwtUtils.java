package com.travelease.travelease.util;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.travelease.travelease.model.adminmodel.Admin;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtUtils {
    
    // @Autowired
    // com.travelease.travelease.repository.TokenRepository tokenRepository;

    private static String secret="this_is_secret";

    private static long expiryDuration = 60 * 60;

    public String generateJwtAdmin(Admin admin){

        
        // long millisec = System.currentTimeMillis();
        // long expiryTime = millisec + expiryDuration * 1000;

        // Date issueAt = new Date(millisec);
        // Date expirDate = new Date(expiryTime);

        Claims claims=(Claims) Jwts.claims()
        .setIssuer(admin.getAdminEmail().toString());
        
        
        
        // .setExpiration(expirDate);

        // .setIssuedAt(issueAt)
        // //optional claims
        // claims.put("type", employee.getFirstName());

        return Jwts.builder()
        .setClaims(claims)
        .compact();

    }
    
    public Boolean verify(String authorization) throws Exception{
        try{
            Jwts.parser().setSigningKey(secret).parseClaimsJws(authorization);  
            return true; 
        }catch(Exception exception){
            //tokenRepository.deleteToken(authorization);
            throw new TimeoutException("Your Time is Over Please Login again");
        }
    }
}
