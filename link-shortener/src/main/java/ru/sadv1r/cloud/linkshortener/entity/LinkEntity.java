package ru.sadv1r.cloud.linkshortener.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.OffsetDateTime;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "links")
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
