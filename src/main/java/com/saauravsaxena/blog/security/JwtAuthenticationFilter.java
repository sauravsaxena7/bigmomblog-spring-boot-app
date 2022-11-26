package com.saauravsaxena.blog.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@Component
public class JwtAuthenticationFilter  extends OncePerRequestFilter{
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		//getToken from Bearer 235behwih
		String requestToken = request.getHeader("Authorization");
		
		String username=null;
		String token=null;
		
		//System.out.println("pola  "+requestToken);
		
		if(requestToken!=null && requestToken.startsWith("Bearer")) {
			
			token=requestToken.substring(7);//token without bearer
			//System.out.println("tokennn   "+token);
			try {
				username = this.jwtTokenHelper.getUsernameFromToken(token);
				
			}catch(IllegalArgumentException e) {
				System.out.println("unnable to get jwt token");
			}catch(ExpiredJwtException e) {
				System.out.println("jwt token expired "+e);
			}catch(MalformedJwtException e) {
				System.out.println("invalid jwt token");
			}
			
			
		}else {
			System.out.println("Token not staart with Bearer or token is not found");
		}
		
		//once we get the token , now validate
		System.out.println("username  "+username +" context "+ SecurityContextHolder.getContext().getAuthentication());
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			if(this.jwtTokenHelper.validteToken(token,userDetails)) {
				//authentication krna hain
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
						new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				
				usernamePasswordAuthenticationToken
				.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				
				
			}else {
				System.out.println("token is not validate");
			}
		}else {
			System.out.println("user name is null and context is not null");
		}
		
		System.out.println("tola "+SecurityContextHolder.getContext().getAuthentication());
		System.out.println("username "+ username);
		filterChain.doFilter(request, response);
		
		
	}

}
