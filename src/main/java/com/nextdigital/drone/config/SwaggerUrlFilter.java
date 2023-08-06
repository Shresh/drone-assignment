package com.nextdigital.drone.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class SwaggerUrlFilter extends OncePerRequestFilter {
    private String servletPath;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        servletPath = request.getServletPath();
        if (servletPath == null) {
            servletPath = "";
        }
        if (request.getRequestURI().equals(servletPath + "/swagger-ui.html")) {
            response.sendRedirect(servletPath + "/error");
        }
        filterChain.doFilter(request, response);
    }
}
