package com.mayank.jwtAuthentication.service.impl;

import com.mayank.jwtAuthentication.service.ErrorResponseService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ErrorResponseServiceImpl implements ErrorResponseService {
    @Override
    public void setUnauthorizedResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"" + message + "\"}");
    }
}
