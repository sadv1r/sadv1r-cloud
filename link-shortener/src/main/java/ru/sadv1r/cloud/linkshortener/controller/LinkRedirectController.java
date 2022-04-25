package ru.sadv1r.cloud.linkshortener.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import ru.sadv1r.cloud.linkshortener.service.LinkService;
import ru.sadv1r.cloud.linkshortener.service.UrlService;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LinkRedirectController {

    private final LinkService linkService;
    private final UrlService urlService;

    @GetMapping("{id}")
    public RedirectView openUrlWithId(@PathVariable String id) {
        linkService.hit(id);
        final Optional<String> optionalUrl = linkService.findLongUrlById(id);

        final String url = optionalUrl.orElse(urlService.getBaseUrl());
        log.debug("Redirecting to: {}", url);

        return new RedirectView(url);
    }

}
