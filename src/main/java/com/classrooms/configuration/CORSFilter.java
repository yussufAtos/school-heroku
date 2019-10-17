package com.classrooms.configuration;



import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CORSFilter implements Filter {

  public CORSFilter() {
  }

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
      HttpServletResponse response = (HttpServletResponse) res;
      HttpServletRequest request = (HttpServletRequest) req;
      response.setHeader("Access-Control-Allow-Origin", "*");
      response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
      response.setHeader("Access-Control-Max-Age", "3600");
//      response.setHeader("Access-Control-Allow-Headers","Origin ,Accept,X-requested-with,Content-Type" +"Access-Control-Request-Method,"
//      +"Access-Control-Request-Headers,"+"Authorization" ); 
      response.setHeader("Access-Control-Allow-Headers", "x-requested-with, Authorization, x-auth-token, origin, content-type, accept");

      response.setHeader("Access-control-expose-headers","Access-Control-Allow-Origin,"+"Access-Control-Credentials,Authorization");
     
     
     if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
          response.setStatus(HttpServletResponse.SC_OK);
          System.out.println("CORSFilter request.getMethod() : "+request.getMethod() +" : "+"OPTIONS".equalsIgnoreCase(request.getMethod()));
      } else {
    	  System.out.println("CORSFilter else");
          chain.doFilter(req, res);
      }
  }

  @Override
  public void init(FilterConfig filterConfig) {
  }

  @Override
  public void destroy() {
  }
}