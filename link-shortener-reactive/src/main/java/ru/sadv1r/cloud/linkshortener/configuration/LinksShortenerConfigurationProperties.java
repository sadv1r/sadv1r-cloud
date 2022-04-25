package ru.sadv1r.cloud.linkshortener.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * URL Shortener properties
 */
@Data
@ConfigurationProperties(prefix = "ru.sadv1r.cloud.link-shortener")
public class LinksShortenerConfigurationProperties {

    /**
     * Base URL for shortened links
     */
    private String baseUrl;

}
