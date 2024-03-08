package com.example.househubadmin.jwt;

import com.example.househubadmin.exception.BadRequestException;
import com.example.househubadmin.exception.InternalServerError;
import com.example.househubadmin.exception.MethodNotAllowedException;
import com.example.househubadmin.exception.NotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Log4j2
@AllArgsConstructor
public class JwtTokenFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String bearerToken = ((HttpServletRequest) servletRequest).getHeader("Authorization");
        if (bearerToken == null) {
            HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
            int status = httpServletResponse.getStatus();
            if(status == 400){
                throw new BadRequestException("Bad Request");
            }else if(status == 404){
                throw new NotFoundException("The request not found ");
            }else if(status == 405){
                throw new MethodNotAllowedException("Method not allowed");
            } else if (status == 500) {
                throw new InternalServerError("Internal server error");
            }
        }
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            bearerToken = bearerToken.substring(7);
        }
        try {
            if (bearerToken != null && jwtTokenProvider.validateToken(bearerToken)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(bearerToken);
                if (authentication != null) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (Exception e) {
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid token");
            response.getWriter().flush();
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}