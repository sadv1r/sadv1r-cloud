package ru.sadv1r.cloud.linkshortener.service;

import com.google.common.hash.Hashing;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import ru.sadv1r.cloud.http.context.HttpRequestAccessor;
import ru.sadv1r.cloud.linkshortener.configuration.LinksShortenerConfigurationProperties;

import java.nio.charset.Charset;

@Service
@RequiredArgsConstructor
public class UrlService {
    private final LinksShortenerConfigurationProperties configurationProperties;
    private final HttpRequestAccessor requestAccessor;

    static final ThreadLocal<ServerWebExchange> CURRENT_EXCHANGE_HOLDER = new InheritableThreadLocal<>();

    public String getBaseUrl() {
        final String baseUrl = configurationProperties.getBaseUrl();
        if (StringUtils.hasText(baseUrl)) {
            return baseUrl;
        }

        final HttpRequest httpRequest = requestAccessor.currentRequest();
       /* final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        final String scheme = request.getScheme();
        final String host = request.getHeader("Host");
        return scheme + "://" + host;*/

        return "";
    }

    public String getId(final String longUrl) {
//        return RandomStringUtils.randomAlphanumeric(5);
        return Hashing.murmur3_32_fixed().hashString(longUrl, Charset.defaultCharset()).toString();
    }

    public String getShortUrl(final String id) {
        return getBaseUrl() + "/" + id;
    }

}
