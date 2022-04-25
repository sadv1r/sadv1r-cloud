package ru.sadv1r.cloud.linkshortener.service;

import com.google.common.hash.Hashing;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ru.sadv1r.cloud.linkshortener.configuration.LinksShortenerConfigurationProperties;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;

@Service
@RequiredArgsConstructor
public class UrlService {

    private final LinksShortenerConfigurationProperties configurationProperties;

    public String getBaseUrl() {
        final String baseUrl = configurationProperties.getBaseUrl();
        if (StringUtils.hasText(baseUrl)) {
            return baseUrl;
        }

        final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        final String scheme = request.getScheme();
        final String host = request.getHeader("Host");
        return scheme + "://" + host;
    }

    public String getId(final String longUrl) {
//        return RandomStringUtils.randomAlphanumeric(5);
        return Hashing.murmur3_32_fixed().hashString(longUrl, Charset.defaultCharset()).toString();
    }

    public String getShortUrl(final String id) {
        return getBaseUrl() + "/" + id;
    }

}
