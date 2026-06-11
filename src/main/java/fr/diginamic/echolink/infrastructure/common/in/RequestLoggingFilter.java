package fr.diginamic.echolink.infrastructure.common.in;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * A Spring filter executed once per request that logs incoming HTTP requests
 * along with their processing time.
 *
 * <p>This filter is configured with the highest precedence to ensure it captures
 * as much information as possible before and after the request is processed by the
 * filter chain.</p>
 *
 * <p>It logs the following details:</p>
 * <ul>
 *     <li>HTTP method (GET, POST, etc.)</li>
 *     <li>Request URI</li>
 *     <li>HTTP response status code</li>
 *     <li>Total processing time in milliseconds</li>
 * </ul>
 *
 * <p>Logs are enhanced with ANSI color codes to improve readability in the console:</p>
 * <ul>
 *     <li>Green: 2xx responses (success)</li>
 *     <li>Yellow: 4xx responses (client errors)</li>
 *     <li>Red: 5xx responses (server errors)</li>
 * </ul>
 *
 * <p>Example log output:</p>
 * <pre>
 * [200] - GET /api/users - 15ms
 * </pre>
 *
 * <p>This filter is registered as a Spring component and is automatically applied
 * to all HTTP requests handled by the application.</p>
 *
 * @see org.springframework.web.filter.OncePerRequestFilter
 * @see jakarta.servlet.Filter
 */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestLoggingFilter extends OncePerRequestFilter {

    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RED = "\u001B[31m";

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        long start = System.currentTimeMillis();

        try {

            filterChain.doFilter(request, response);

        } finally {

            long duration = System.currentTimeMillis() - start;

            int status = response.getStatus();
            String color = getColor(status);

            log.info(
                    "{}[{}]{} - {} {} - {}ms",
                    color,
                    status,
                    RESET,
                    request.getMethod(),
                    request.getRequestURI(),
                    duration
            );
        }
    }

    private String getColor(int status) {
        if (status >= 500) return RED;
        if (status >= 400) return YELLOW;
        if (status >= 200) return GREEN;
        return RED;
    }
}
