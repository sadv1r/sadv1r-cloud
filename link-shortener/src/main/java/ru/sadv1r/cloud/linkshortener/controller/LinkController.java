package ru.sadv1r.cloud.linkshortener.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.sadv1r.cloud.linkshortener.model.Link;
import ru.sadv1r.cloud.linkshortener.model.LinkShortenRequest;
import ru.sadv1r.cloud.linkshortener.service.LinkService;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/links")
@RequiredArgsConstructor
public class LinkController {

    private final LinkService linkService;

    @PostMapping("shorten")
    public Link shorten(@RequestBody @Valid LinkShortenRequest body) {
        return linkService.shorten(body.getLongUrl());
    }

    @GetMapping("{id}")
    public Link getLink(@PathVariable String id) {
        return linkService.findById(id).orElseThrow(EntityNotFoundException::new);
    }


}
