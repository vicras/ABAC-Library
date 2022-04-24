package com.vicras.blog.model;

import static java.time.LocalDateTime.now;
import static javax.persistence.GenerationType.SEQUENCE;
import static lombok.AccessLevel.PUBLIC;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;

@Getter(PUBLIC)
@Setter(PUBLIC)
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    public static final String SEQUENCE_GENERATOR = "SEQ_GEN";
    public static final int ALLOCATION_SIZE = 1;

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = SEQUENCE_GENERATOR)
    private Long id;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @Version
    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    @PrePersist
    private void setCreationDate() {
        this.createdAt = now();
    }

    @PreUpdate
    private void setChangeDate() {
        this.updatedAt = now();
    }
}