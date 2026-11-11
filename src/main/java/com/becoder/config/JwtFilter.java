package com.becoder.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.becoder.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter{

	@Autowired
	private JwtService jwtSer;
	
	@Autowired
	private UserDetailsService userDetailsSer;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		try
		{
		String auth=request.getHeader("Authorization");
		
		String token=null;
		String username=null;
		if(auth!=null && auth.startsWith("Bearer"))
		{
			token=auth.substring(7);
			username=jwtSer.extractUsername(token);
		}
		
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
		{
			UserDetails userDetails=userDetailsSer.loadUserByUsername(username);
			Boolean validateToken=jwtSer.validateToken(token,userDetails);
			if(validateToken)
			{
				UsernamePasswordAuthenticationToken aut=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				aut.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(aut);
			}
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			response.setContentType("application/json");
			response.getWriter().write(e.getMessage());
			return;
			//this try and catch block is used for handle exception that occr after token expiration 
			//we used try and catch bcz thsi filter realated exception is not handle by globalexceptionhandler class
		}
		filterChain.doFilter(request, response);
	}

}
