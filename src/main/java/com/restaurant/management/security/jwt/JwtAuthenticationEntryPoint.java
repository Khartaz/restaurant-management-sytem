package com.restaurant.management.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant.management.web.response.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

//    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException, ServletException {
        ApiResponse response = new ApiResponse(401, "Unauthorised");

        OutputStream out = httpServletResponse.getOutputStream();

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(out, response);

        out.flush();
//        logger.error("Responding with unauthorized error. Message - {}", e.getMessage());
//        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
    }
}