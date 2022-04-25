package ru.sadv1r.cloud.linkshortener.model;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class Link {

    private String longUrl;
    private String shortUrl;
    private Integer hits;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

}
