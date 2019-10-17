package com.classrooms.configuration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {

		
//		      response.addHeader("Access-Control-Allow-Origin", "*");
//		      response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
//		      response.addHeader("Access-Control-Max-Age", "3600");
//		      response.addHeader("Access-Control-Allow-Headers","Origin ,Accept,X-requested-with,Content-Type" +"Access-Control-Request-Method,"
//		      +"Access-Control-Request-Headers,"+"Authorization" );  
//		      response.addHeader("Access-control-expose-headers","Access-Control-Allow-Origin,"+"Access-Control-Credentials,Authorization");
		
		   if (request.getMethod().equals("OPTIONS")) {
		          response.setStatus(HttpServletResponse.SC_OK);
		      } else {
		  		
		  		System.out.println("****JwtAuthorizationFilter doFilterInternal******");
		  		String jwt = request.getHeader(JwtProperties.HEADER_STRING);
		  		System.out.println("doFilterInternal jwt : "+jwt);
		  		if (jwt == null) {
		  			filterChain.doFilter(request, response);
		  			System.out.println("jwt is null : ");
		  			return;
		  		}
		  		Claims claims = Jwts.parser().setSigningKey(JwtProperties.SECRET)
		  				.parseClaimsJws(jwt.replace(JwtProperties.TOKEN_PREFIX, ""))

		  				.getBody();
		  		
		  		System.out.println("doFilterInternal claims : "+claims);
		  		System.out.println("doFilterInternal claims roles : "+claims.get("roles"));
		  		String username = claims.getSubject();
		  		ArrayList<Map<String, String>> roles = (ArrayList<Map<String, String>>) claims.get("roles");
		  		Collection<GrantedAuthority> autorities = new ArrayList<>();

		  		roles.forEach(r -> {
		  			autorities.add(new SimpleGrantedAuthority(r.get("authority")));
		  		});

		  		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
		  				null, autorities);
		  		SecurityContextHolder.getContext().setAuthentication(authenticationToken);

		  		filterChain.doFilter(request, response);
		       
		      }
		
		
		
		


	}

}
