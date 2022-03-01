package com.example.demo.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.filter.OrderedCharacterEncodingFilter;

public class EncordingFilter extends OrderedCharacterEncodingFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        super.setOrder(HIGHEST_PRECEDENCE);
        super.setEncoding("utf-8");
        super.setForceEncoding(true);
        super.doFilterInternal(request, response, filterChain);
    }
}
