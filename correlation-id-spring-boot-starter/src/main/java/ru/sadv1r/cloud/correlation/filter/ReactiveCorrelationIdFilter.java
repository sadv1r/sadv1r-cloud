package ru.sadv1r.cloud.correlation.filter;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.lang.NonNull;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import ru.sadv1r.cloud.correlation.configuration.CorrelationIdProperties;
import ru.sadv1r.cloud.correlation.generator.RequestIdGenerator;

@Slf4j
@RequiredArgsConstructor
public class ReactiveCorrelationIdFilter implements WebFilter, Ordered {

    private final CorrelationIdProperties properties;
    private final RequestIdGenerator idGenerator;

    @NonNull
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        final String requestIdHeaderValue = exchange.getRequest().getHeaders().getFirst(properties.getHttpHeaderName());

        final String requestId;
        if (properties.isAcceptRequestHeader() && requestIdHeaderValue != null) {
            requestId = exchange.getRequest().getHeaders().getFirst(properties.getHttpHeaderName());
        } else {
            requestId = idGenerator.generateCorrelationId();
        }
        return chain.filter(exchange)
                .doOnSubscribe(subscription -> {
                    MDC.put(properties.getMdcKeyName(), requestId);
                    log.trace("MDC context value set {}={}", properties.getMdcKeyName(), requestId);

                    if (properties.isAddToResponse()) {
                        exchange.getResponse().getHeaders().set(properties.getHttpHeaderName(), requestId);
                    }
                })
                .doFinally(whatever -> {
                    log.trace("MDC context value removing {}={}", properties.getMdcKeyName(), requestId);
                    MDC.remove(properties.getMdcKeyName());
                    log.trace("MDC context value removed {}={}", properties.getMdcKeyName(), requestId);
                });
    }

    @PostConstruct
    void setupThreadsDecorator() {
        Schedulers.onScheduleHook("mdc", runnable -> {
            final String requestId = MDC.get(properties.getMdcKeyName());
            return () -> {
                MDC.put(properties.getMdcKeyName(), requestId);
                log.trace("MDC context value '{}' set in thread", properties.getMdcKeyName());
                try {
                    runnable.run();
                } finally {
                    log.trace("MDC context value '{}' removing from thread", properties.getMdcKeyName());
                    MDC.remove(properties.getMdcKeyName());
                    log.trace("MDC context value '{}' removed from thread", properties.getMdcKeyName());
                }
            };
        });
    }

    @PreDestroy
    void shutdownThreadsDecorator() {
        Schedulers.resetOnScheduleHook("mdc");
    }

    @Override
    public int getOrder() {
        return properties.getFilterOrder();
    }

}
