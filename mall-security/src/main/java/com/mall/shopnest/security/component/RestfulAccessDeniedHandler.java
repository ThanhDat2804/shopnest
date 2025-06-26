package com.mall.shopnest.security.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mall.shopnest.api.CommonResult;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

public class RestfulAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException e) throws IOException, ServletException {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        String result = objectMapper.writeValueAsString(CommonResult.forbidden(e.getMessage()));
        response.getWriter().println(result);
        response.getWriter().flush();
    }
}
