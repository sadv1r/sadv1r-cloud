package ru.sadv1r.cloud.correlation.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.servlet.ConditionalOnMissingFilterBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.sadv1r.cloud.correlation.filter.ReactiveCorrelationIdFilter;
import ru.sadv1r.cloud.correlation.filter.ServletCorrelationIdFilter;
import ru.sadv1r.cloud.correlation.generator.DefaultRequestIdGenerator;
import ru.sadv1r.cloud.correlation.generator.RequestIdGenerator;

import static org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type.REACTIVE;
import static org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type.SERVLET;

@Configuration
@EnableConfigurationProperties(CorrelationIdProperties.class)
public class CorrelationIdAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public RequestIdGenerator requestIdGenerator() {
        return new DefaultRequestIdGenerator();
    }

    @Configuration
    @ConditionalOnWebApplication(type = SERVLET)
    public static class ServletConfiguration {
        @Bean
        @ConditionalOnMissingFilterBean
        public FilterRegistrationBean<ServletCorrelationIdFilter> servletCorrelationIdFilter(CorrelationIdProperties properties,
                                                                                             RequestIdGenerator requestIdGenerator) {
            final ServletCorrelationIdFilter filter = new ServletCorrelationIdFilter(properties, requestIdGenerator);

            final FilterRegistrationBean<ServletCorrelationIdFilter> filterRegistration = new FilterRegistrationBean<>();
            filterRegistration.setFilter(filter);
            filterRegistration.setOrder(properties.getFilterOrder());

            return filterRegistration;
        }
    }

    @Configuration
    @ConditionalOnWebApplication(type = REACTIVE)
    public static class ReactiveConfiguration {
        @Bean
        @ConditionalOnMissingBean
        public ReactiveCorrelationIdFilter reactiveCorrelationIdFilter(CorrelationIdProperties correlationIdProperties,
                                                                       RequestIdGenerator requestIdGenerator) {
            return new ReactiveCorrelationIdFilter(correlationIdProperties, requestIdGenerator);
        }
    }

}
