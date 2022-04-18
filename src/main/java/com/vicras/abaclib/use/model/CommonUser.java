package com.vicras.abaclib.use.model;

import static com.vicras.abaclib.use.model.BaseEntity.ALLOCATION_SIZE;
import static com.vicras.abaclib.use.model.BaseEntity.SEQUENCE_GENERATOR;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.STRING;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@ToString
@Table(name = "common_user")
@SequenceGenerator(
        name = SEQUENCE_GENERATOR,
        sequenceName = "SEQ_COMMON_USER",
        allocationSize = ALLOCATION_SIZE
)
public class CommonUser extends BaseEntity {

    private String login;
    private String password;

    @Enumerated(STRING)
    private Position position;

    @ToString.Exclude
    @OneToMany(cascade = ALL, mappedBy = "creator")
    private Collection<Document> documents;
}
