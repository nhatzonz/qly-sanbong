package com.example.userservice.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class LoggingFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        ContentCachingRequestWrapper req = new ContentCachingRequestWrapper((HttpServletRequest) request);
        ContentCachingResponseWrapper res = new ContentCachingResponseWrapper((HttpServletResponse) response);

        chain.doFilter(req, res);

        if (req.getRequestURI().startsWith("/api/")) {
            String reqBody = new String(req.getContentAsByteArray(), StandardCharsets.UTF_8);
            String resBody = new String(res.getContentAsByteArray(), StandardCharsets.UTF_8);

            log.info("\n>>> REQUEST  {} {}\n    Body: {}\n<<< RESPONSE {}\n    Body: {}",
                    req.getMethod(),
                    req.getRequestURI(),
                    reqBody.isBlank() ? "(empty)" : reqBody,
                    res.getStatus(),
                    resBody.isBlank() ? "(empty)" : resBody);
        }

        res.copyBodyToResponse();
    }
}
