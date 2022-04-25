package ru.sadv1r.cloud.correlation.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.sadv1r.cloud.correlation.configuration.CorrelationIdProperties;
import ru.sadv1r.cloud.correlation.generator.RequestIdGenerator;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class ServletCorrelationIdFilter extends OncePerRequestFilter {

    private final CorrelationIdProperties properties;
    private final RequestIdGenerator idGenerator;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String requestIdHeaderValue = request.getHeader(properties.getHttpHeaderName());

        final String requestId;
        if (properties.isAcceptRequestHeader() && requestIdHeaderValue != null) {
            requestId = request.getHeader(properties.getHttpHeaderName());
        } else {
            requestId = idGenerator.generateCorrelationId();
        }

        MDC.put(properties.getMdcKeyName(), requestId);
        log.trace("MDC context value set {}={}", properties.getMdcKeyName(), requestId);

        if (properties.isAddToResponse()) {
            response.addHeader(properties.getHttpHeaderName(), requestId);
            log.trace("HTTP response header value set {}={}", properties.getHttpHeaderName(), requestId);
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            log.trace("MDC context value removing {}={}", properties.getMdcKeyName(), requestId);
            MDC.remove(properties.getMdcKeyName());
            log.trace("MDC context value removed {}={}", properties.getMdcKeyName(), requestId);
        }
    }

}
