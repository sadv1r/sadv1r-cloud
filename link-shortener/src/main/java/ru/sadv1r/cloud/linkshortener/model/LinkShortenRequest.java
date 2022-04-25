package ru.sadv1r.cloud.linkshortener.model;

import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotEmpty;

@Data
public class LinkShortenRequest {

    @URL
    @NotEmpty
    String longUrl;

}
