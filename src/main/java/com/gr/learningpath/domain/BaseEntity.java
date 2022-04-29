package com.gr.learningpath.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@MappedSuperclass
@Getter
@SuperBuilder(toBuilder = true)
public class BaseEntity implements Persistable, Identifiable<Long> {

    private static final long serialVersionUID = -3558752020059042774L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private Timestamp updatedAt;

    public BaseEntity(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public boolean isPersisted() {
        return id != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity that = (BaseEntity) o;
        return (id != null) && (id.equals(that.id));
    }

    @Override
    public int hashCode() {
        int hashCode = 13;
        hashCode += null == id ? 0 : id.hashCode() * 19;
        return hashCode;
    }

    @Override
    public String toString() {
        return "Entity " + getClass().getSimpleName() + '[' + "id=" + id + ']';
    }
}

