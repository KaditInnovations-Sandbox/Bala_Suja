package com.travelease.travelease.util;
import java.util.Date;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.travelease.travelease.model.adminmodel.Admin;
import com.travelease.travelease.model.hubmodel.Driver;
import com.travelease.travelease.model.passengermodel.Passenger;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtils {
    
    private static String secret="this_is_secret";

    private static long expiryDuration =30 * 24 * 60 * 60;

    public String generateJwtAdmin(Admin admin){

        long millisec = System.currentTimeMillis();
        long expiryTime = millisec + expiryDuration * 1000;

        Date issueAt = new Date(millisec);
        Date expirDate = new Date(expiryTime);

        Claims claims=(Claims) Jwts.claims()
        .setIssuer(admin.getAdminEmail().toString())
        .setIssuedAt(issueAt)
        .setExpiration(expirDate);
        
        //optional claims
        // claims.put("type", employee.getFirstName());

        return Jwts.builder()
        .setClaims(claims)
        .signWith(SignatureAlgorithm.HS512, secret)
        .compact();

    }

    public String generateJwtDriver(Driver driver){ 
        long millisec = System.currentTimeMillis();
        long expiryTime = millisec + expiryDuration * 1000;

        Date issueAt = new Date(millisec);
        Date expirDate = new Date(expiryTime);

        Claims claims=(Claims) Jwts.claims()
        .setIssuer(driver.getDriverPhoneNumber().toString())
        .setIssuedAt(issueAt)
        .setExpiration(expirDate);
        
        return Jwts.builder()
        .setClaims(claims)
        .signWith(SignatureAlgorithm.HS512, secret)
        .compact();
    
    }

    public String generateJwtPassenger(Passenger passenger){ 
        long millisec = System.currentTimeMillis();
        long expiryTime = millisec + expiryDuration * 1000;

        Date issueAt = new Date(millisec);
        Date expirDate = new Date(expiryTime);

        Claims claims=(Claims) Jwts.claims()
        .setIssuer(passenger.getPassengerPhone().toString())
        .setIssuedAt(issueAt)
        .setExpiration(expirDate);
        
        return Jwts.builder()
        .setClaims(claims)
        .signWith(SignatureAlgorithm.HS512, secret)
        .compact();
    
    }
    
    public String verify(String authorization) throws Exception{
        try{
            Jws<Claims> claims=Jwts.parser().setSigningKey(secret).parseClaimsJws(authorization);  
            return claims.getBody().getIssuer(); 
        }catch(Exception exception){
            //tokenRepository.deleteToken(authorization);
            throw new TimeoutException("Your Time is Over Please Login again");
        }
    }
}
