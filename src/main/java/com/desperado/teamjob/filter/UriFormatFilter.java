package com.desperado.teamjob.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("uriFormatFilter")
public class UriFormatFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String uri = httpServletRequest.getRequestURI();
        String newUri = uri.replace("//","/");
        httpServletRequest = new HttpServletRequestWrapper(httpServletRequest){
            @Override
            public String getRequestURI() {
                return newUri;
            }
        };
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
