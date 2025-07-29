package com.mayank.jwtAuthentication.config;

import com.mayank.jwtAuthentication.service.ErrorResponseService;
import com.mayank.jwtAuthentication.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final ErrorResponseService errorResponseService;

    private static final List<String> EXCLUDED_PATHS = List.of(
            "/v1/auth/login", "/v1/auth/register"  // add paths you want to exclude
    );

    public JwtFilter(JwtService jwtService, UserDetailsService userDetailsService, ErrorResponseService errorResponseService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.errorResponseService = errorResponseService;
    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        return EXCLUDED_PATHS.contains(path); // skip filter for these paths
    }
    @Override
    protected void doFilterInternal( HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader=request.getHeader("Authorization");

        if(authHeader==null || !authHeader.startsWith("Bearer ") ){
            errorResponseService.setUnauthorizedResponse(response,"Token not present");
            return;
        }

        try{
             String token=authHeader.substring(7);
             String email=jwtService.extractEmail(token);

            System.out.println(email);
            Authentication authentication= SecurityContextHolder.getContext().getAuthentication();

            if(email!=null && authentication==null){
                UserDetails userDetails=userDetailsService.loadUserByUsername(email);
                if(jwtService.isTokenValid(token,userDetails)){
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource()
                            .buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }

            }
            filterChain.doFilter(request,response);
        } catch (Exception e) {
           errorResponseService.setUnauthorizedResponse(response,"Token is invalid");

        }

    }
}
