package com.vicras.abaclib.use.model;

import static com.vicras.abaclib.use.model.BaseEntity.ALLOCATION_SIZE;
import static com.vicras.abaclib.use.model.BaseEntity.SEQUENCE_GENERATOR;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@ToString
@Table(name = "document")
@SequenceGenerator(
        name = SEQUENCE_GENERATOR, sequenceName = "SEQ_DOCUMENT", allocationSize = ALLOCATION_SIZE
)
public class Document extends BaseEntity {

    private String title;
    private String text;

    @ManyToOne
    private CommonUser creator;
}
