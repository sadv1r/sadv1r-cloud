package ru.sadv1r.cloud.linkshortener.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.sadv1r.cloud.linkshortener.model.Link;
import ru.sadv1r.cloud.linkshortener.model.LinkShortenRequest;
import ru.sadv1r.cloud.linkshortener.service.LinkService;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/links")
@RequiredArgsConstructor
public class LinkController {

    private final LinkService linkService;

    @PostMapping("shorten")
    public Mono<Link> shorten(@RequestBody @Valid LinkShortenRequest request) {
        return linkService.shorten(request.getLongUrl());
    }

    @GetMapping("{id}")
    public Mono<Link> getLink(@PathVariable String id) {
        return linkService.findById(id);
    }

}
