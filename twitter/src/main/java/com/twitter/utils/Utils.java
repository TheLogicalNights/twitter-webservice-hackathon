package com.twitter.utils;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class Utils {
	@Autowired
	private Environment env;

	public String getJWTUtil() {

		String jwt = Jwts.builder().setSubject("123")
				.setExpiration(new Date(
						System.currentTimeMillis() + Long.parseLong(env.getProperty("token.expiration_time").trim())))
				.signWith(SignatureAlgorithm.HS512, env.getProperty("token.secret")).compact();
		return jwt;
	}

	public Boolean validateRequest(String jwt) {
		boolean returnValue = false;
		jwt = jwt.split(" ")[1];
		String subject = Jwts.parser().setSigningKey(env.getProperty("token.secret")).parseClaimsJws(jwt).getBody().getSubject();
		if(subject.equals("register") || subject.equals("login") || subject.equals("user")) {
			returnValue = true;
		}
		return returnValue;
	}
}
