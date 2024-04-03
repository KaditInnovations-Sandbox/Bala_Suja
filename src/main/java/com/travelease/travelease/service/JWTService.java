package com.travelease.travelease.service;

import java.util.Date;
import java.security.Key;
import java.util.Base64;

// import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Service
public class JWTService {

    
    // private String generateToken(UserDetails userDetails){
    //     return Jwts.builder().setSubject(userDetails.getUsername())
    //            .setIssuedAt(new Date(System.currentTimeMillis()))
    //            .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24)))

    //            .signWith(SignatureAlgorithm.HS256, "a221jk" )
    //            .compact();
    // }
    
    // private Key getSignKey() {
    //     byte[] keyBytes = Base64.getDecoder().decode("4132FA");
    //     return Keys.hmacShaKeyFor(keyBytes);
    // }
    
}
