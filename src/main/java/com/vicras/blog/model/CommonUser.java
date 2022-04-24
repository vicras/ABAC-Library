package com.vicras.blog.model;

import static com.vicras.blog.model.BaseEntity.ALLOCATION_SIZE;
import static com.vicras.blog.model.BaseEntity.SEQUENCE_GENERATOR;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.STRING;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.Hibernate;

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
    @Enumerated(STRING)
    private Department department;

    @ToString.Exclude
    @OneToMany(cascade = ALL, mappedBy = "creator")
    private Collection<Document> documents;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null
                || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        CommonUser that = (CommonUser) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
