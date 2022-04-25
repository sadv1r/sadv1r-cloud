package ru.sadv1r.cloud.linkshortener.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table("links")
public class LinkEntity implements LinkOnlyWithUrl {

    @Id
    private String id;
    private String url;
    private Integer hits = 0;
    //    @CreatedDate
//    private OffsetDateTime createdAt;
//    @LastModifiedDate
//    private OffsetDateTime updatedAt;

}
