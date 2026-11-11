package com.becoder.service.impl;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import java.util.Base64;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.becoder.entity.User;
import com.becoder.service.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServiceImpl implements JwtService{

	public String secretKey="";
	
	public JwtServiceImpl() {
		try {
			KeyGenerator genk=KeyGenerator.getInstance("HmacSHA256");
			SecretKey sk=genk.generateKey();
			secretKey=Base64.getEncoder().encodeToString(sk.getEncoded());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String generateToken(User user) {

		HashMap<String,Object> claims=new HashMap();
		claims.put("id", user.getId());
		claims.put("role", user.getRole());
		claims.put("status", user.getAccountStatus().getIsActive());
		
        String token=Jwts.builder()
        		         .claims().add(claims)
        		         .subject(user.getEmail())
        		         .issuedAt(new Date(System.currentTimeMillis()))
        		         .expiration(new Date(System.currentTimeMillis()+60*60*60*10))
        		         .and()
        		         .signWith(getKey())
        		         .compact();
		return token;
	}

	private Key getKey() {
		byte[] getB=Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(getB);
	}

	@Override
	public String extractUsername(String token) {
		Claims claims=extractAllClaims(token);
		return claims.getSubject();
	}

	private Claims extractAllClaims(String token) {
		try {
		Claims claims=Jwts.parser().verifyWith(dcryptKey(secretKey)).build().parseSignedClaims(token).getPayload();
		     return claims;
		}
		catch(Exception e)
		{
			throw e;
		}
		//this try and catch block is used for handle exception that occr after token expiration 
		//we used try and catch bcz thsi filter realated exception is not handle by globalexceptionhandler class
	}

	private SecretKey dcryptKey(String secretKey2) {
		byte[] keyByte=Decoders.BASE64.decode(secretKey2);
		return Keys.hmacShaKeyFor(keyByte);
	}

	@Override
	public Boolean validateToken(String token, UserDetails userDetails) {
		
		if(userDetails.getUsername().equals(extractUsername(token)) && !isExpired(token))
		{
			return true;
		}
		return false;
	}

	private boolean isExpired(String token) {
		Claims claims=extractAllClaims(token);
		Date isExpire=claims.getExpiration();
		return isExpire.before(new Date()); 
	}

	
}
