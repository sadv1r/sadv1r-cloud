package ru.sadv1r.cloud.linkshortener.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.sadv1r.cloud.linkshortener.entity.LinkEntity;
import ru.sadv1r.cloud.linkshortener.entity.LinkOnlyWithUrl;
import ru.sadv1r.cloud.linkshortener.model.Link;
import ru.sadv1r.cloud.linkshortener.repository.LinkRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class LinkService {

    private final LinkRepository linkRepository;
    private final UrlService urlService;

    public Mono<Link> shorten(String longUrl) {
        final String id = urlService.getId(longUrl);

        return findById(id).doOnNext(e -> {
                    log.debug("Short URL with ID {} already exists", id);
                })
                .switchIfEmpty(create(id, longUrl));
    }

    private Mono<Link> create(String id, String longUrl) {
        final LinkEntity linkEntity = new LinkEntity();
        linkEntity.setId(id);
        linkEntity.setUrl(longUrl);

        return linkRepository.save(linkEntity)
                .doOnNext(e -> {
                    log.debug("Created Short URL for ID: {} for Long url: {}", id, longUrl);
                })
                .map(e -> {
                    final Link response = new Link();
                    response.setShortUrl(urlService.getShortUrl(e.getId()));
                    response.setLongUrl(e.getUrl());
                    response.setHits(e.getHits());
                    return response;
                });
    }

    public Mono<Link> findById(String id) {
        return linkRepository.findById(id)
                .map(e -> {
                    final Link response = new Link();
                    response.setShortUrl(urlService.getShortUrl(e.getId()));
                    response.setLongUrl(e.getUrl());
                    response.setHits(e.getHits());
                    return response;
                });
    }

    public Mono<String> findLongUrlById(String id) {
        return linkRepository.findUrlById(id).map(LinkOnlyWithUrl::getUrl);
    }

    public void hit(String id) {
        linkRepository.hit(id);
    }

}
