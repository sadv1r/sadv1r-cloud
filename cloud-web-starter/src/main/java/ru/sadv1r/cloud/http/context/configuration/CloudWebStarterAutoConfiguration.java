package ru.sadv1r.cloud.http.context.configuration;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import ru.sadv1r.cloud.http.context.HttpRequestAccessor;
import ru.sadv1r.cloud.http.context.ReactorExchangeFilter;

import static org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type.REACTIVE;

@AutoConfiguration
public class CloudWebStarterAutoConfiguration {

    @Bean
    public HttpRequestAccessor httpRequestAccessor() {
        return new HttpRequestAccessor();
    }

    @Bean
    @ConditionalOnWebApplication(type = REACTIVE)
    public ReactorExchangeFilter reactorExchangeFilter() {
        return new ReactorExchangeFilter();
    }

}
