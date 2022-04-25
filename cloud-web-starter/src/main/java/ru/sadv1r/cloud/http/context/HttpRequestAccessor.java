package ru.sadv1r.cloud.http.context;

import org.springframework.http.HttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ServerWebExchange;

import javax.servlet.http.HttpServletRequest;

public class HttpRequestAccessor {

    static final ThreadLocal<ServerWebExchange> CURRENT_EXCHANGE_HOLDER =
            new InheritableThreadLocal<>();

    public HttpRequest currentRequest() {
        final ServerWebExchange webExchange = CURRENT_EXCHANGE_HOLDER.get();
        if (webExchange != null) {
            return webExchange.getRequest();
        }

        var servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

        HttpServletRequest servletRequest = servletRequestAttributes.getRequest();
        return new ServletServerHttpRequest(servletRequest);
    }

}
