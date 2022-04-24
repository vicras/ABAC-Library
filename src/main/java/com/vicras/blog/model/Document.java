package com.vicras.blog.model;

import static com.vicras.blog.model.BaseEntity.ALLOCATION_SIZE;
import static com.vicras.blog.model.BaseEntity.SEQUENCE_GENERATOR;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.Hibernate;

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
    private boolean approved = false;

    @ManyToOne
    private CommonUser creator;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null
                || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Document document = (Document) o;
        return getId() != null && Objects.equals(getId(), document.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
