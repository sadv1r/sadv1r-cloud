package ru.sadv1r.cloud.linkshortener.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(LinksShortenerConfigurationProperties.class)
public class LinksShortenerConfiguration {

    /*@Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> builder.serializationInclusion(JsonInclude.Include.NON_NULL)
                .modulesToInstall(JavaTimeModule.class);
    }*/

}
