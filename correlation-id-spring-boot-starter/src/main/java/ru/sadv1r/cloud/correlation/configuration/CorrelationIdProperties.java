package ru.sadv1r.cloud.correlation.configuration;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.Ordered;

/**
 * Correlation ID properties
 */
@Value
@ConfigurationProperties("ru.sadv1r.cloud.correlation")
public class CorrelationIdProperties {

    /**
     * Use received HTTP header {@link #httpHeaderName} if exists
     */
    boolean acceptRequestHeader = true;

    /**
     * Add {@link #httpHeaderName} to response HTTP headers
     */
    boolean addToResponse = true;

    /**
     * HTTP correlation ID header name
     */
    String httpHeaderName = "X-Request-Id";

    /**
     * MDC context key name
     */
    String mdcKeyName = "requestId";

    /**
     * Priority order of the filter
     */
    int filterOrder = Ordered.HIGHEST_PRECEDENCE;

}
