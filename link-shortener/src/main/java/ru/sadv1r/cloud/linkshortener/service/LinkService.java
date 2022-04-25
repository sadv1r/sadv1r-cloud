package ru.sadv1r.cloud.linkshortener.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sadv1r.cloud.linkshortener.entity.LinkEntity;
import ru.sadv1r.cloud.linkshortener.entity.LinkOnlyWithUrl;
import ru.sadv1r.cloud.linkshortener.model.Link;
import ru.sadv1r.cloud.linkshortener.repository.LinkRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class LinkService {

    private final LinkRepository linkRepository;
    private final UrlService urlService;

    public Link shorten(String longUrl) {
        final String id = urlService.getId(longUrl);

        final Optional<Link> link = findById(id);
        if (link.isPresent()) {
            log.debug("Short URL with ID {} already exists", id);
            return link.get();
        }

        final LinkEntity linkEntity = new LinkEntity();
        linkEntity.setId(id);
        linkEntity.setUrl(longUrl);

        final LinkEntity savedLinkEntity = linkRepository.save(linkEntity);
        log.debug("Created Short URL for ID: {} for Long url: {}", id, longUrl);

        final Link response = new Link();
        response.setShortUrl(urlService.getShortUrl(savedLinkEntity.getId()));
        response.setLongUrl(savedLinkEntity.getUrl());
        response.setHits(savedLinkEntity.getHits());

        return response;
    }

    public Optional<Link> findById(String id) {
        return linkRepository.findById(id)
                .map(e -> {
                    final Link response = new Link();
                    response.setShortUrl(urlService.getShortUrl(e.getId()));
                    response.setLongUrl(e.getUrl());
                    response.setHits(e.getHits());
                    return response;
                });
    }

    public Optional<String> findLongUrlById(String id) {
        return linkRepository.findUrlById(id).map(LinkOnlyWithUrl::getUrl);
    }

    public void hit(String id) {
        linkRepository.hit(id);
    }

}
