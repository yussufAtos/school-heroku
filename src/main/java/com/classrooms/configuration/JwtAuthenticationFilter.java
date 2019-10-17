package com.classrooms.configuration;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.classrooms.model.UserApp;
import com.fasterxml.jackson.databind.ObjectMapper;
//import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
//import com.auth0.jwt.JWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private AuthenticationManager authenticationManager;

	public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		System.out.println("****attemptAuthentication****");
		UserApp user = null;
		try {
			user = new ObjectMapper().readValue(request.getInputStream(), UserApp.class);
			
			System.out.println("attemptAuthentication getInputStream() : " + request.getInputStream());

		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("user : " + user.getUsername() + " password : " + user.getPassword() + " roles : " + user.getRoles());

		Authentication auth = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), new ArrayList<>()));
		
		System.out.println(" attemptAuthentication auth : "+auth);

		return auth;

	}

	@Override 
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		User principal = (User) authResult.getPrincipal();
        System.out.println(" ***successfulAuthentication*** " );

		System.out.println("UserPrincipal : " + principal);
		System.out.println("UserPrincipal : " + principal.getUsername());
		System.out.println("UserPrincipal : " + principal.getAuthorities());

		// 1

		/*
		 * String token = JWT.create() .withSubject(principal.getUsername())
		 * .withExpiresAt(new Date(System.currentTimeMillis() +
		 * JwtProperties.EXPIRATION_TIME))
		 * .sign(HMAC512(JwtProperties.SECRET.getBytes()));
		 */

		/*
		 * String token = Jwts.builder() .setSubject(principal.getUsername())
		 * .setExpiration(new Date(System.currentTimeMillis() +
		 * JwtProperties.EXPIRATION_TIME)
		 * .sigWith(SignatueAlgorithm.HS512,JwtProperties.SECRET)
		 * 
		 * .claim("roles",principal.getAuthorities()) .compacte();
		 */

		// 2

		/*
		 * Claims claims = Jwts.claims().setSubject(principal.getUsername());
		 * claims.put("role", principal.getAuthorities());
		 * 
		 * String token =
		 * Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512,
		 * JwtProperties.SECRET) .compact();
		 */

		// 3
		Long now = System.currentTimeMillis();
		String token = Jwts.builder().setSubject(principal.getUsername()).claim("roles", principal.getAuthorities())
				.setIssuedAt(new Date(now)).setExpiration(new Date(now + JwtProperties.EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, JwtProperties.SECRET).compact();
		
		System.out.println("successfulAuthentication token : "+token);
		response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + token);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter()
				.write("{\"" + JwtProperties.HEADER_STRING + "\":\"" + JwtProperties.TOKEN_PREFIX + token + "\"}");

	}

}

