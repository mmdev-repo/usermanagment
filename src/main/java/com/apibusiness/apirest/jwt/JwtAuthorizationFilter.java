package com.apibusiness.apirest.jwt;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
	
	public static final String AUTHORIZATION = "Authorization";

    @Autowired
    private JwtService jwtService;

    public JwtAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        String authHeader = request.getHeader(AUTHORIZATION);
        if (jwtService.isBearer(authHeader)) {
           // LogManager.getLogger(this.getClass().getName()).debug(">>> FILTER JWT...");
            List<GrantedAuthority> authorities = null;
			try {
				authorities = jwtService.roles(authHeader).stream()
				        .map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            UsernamePasswordAuthenticationToken authentication = null;
			try {
				authentication = new UsernamePasswordAuthenticationToken(jwtService.user(authHeader), null, authorities);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

}
