package ru.sadv1r.cloud.http.context;

import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class ReactorExchangeFilter implements WebFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return chain.filter(exchange)
                .doOnSubscribe(sub -> {
                    HttpRequestAccessor.CURRENT_EXCHANGE_HOLDER.set(exchange);
                })
                .doFinally(whatever -> {
                    HttpRequestAccessor.CURRENT_EXCHANGE_HOLDER.remove();
                });
    }

    @PostConstruct
    void setupReactorThreadsDecorator() {
        Schedulers.onScheduleHook("REQUEST_HOLDER", runnable -> {
            ServerWebExchange currentExchange = HttpRequestAccessor.CURRENT_EXCHANGE_HOLDER.get();
            return () -> {
                if (currentExchange != null) {
                    HttpRequestAccessor.CURRENT_EXCHANGE_HOLDER.set(currentExchange);
                }
                try {
                    runnable.run();
                } finally {
                    HttpRequestAccessor.CURRENT_EXCHANGE_HOLDER.remove();
                }
            };
        });
    }

    @PreDestroy
    void shutdownReactorThreadsDecorator() {
        Schedulers.resetOnScheduleHook("REQUEST_HOLDER");
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

}
