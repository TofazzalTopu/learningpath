package com.gr.learningpath.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(toBuilder = true)
@Setter
@Getter
@Entity
@Table(
        name = "role",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "code", name = "uk_roles_code")
        }
)
public class Role extends BaseEntity implements Versionable {

    private static final long serialVersionUID = -5460961826917174713L;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "description")
    private String description;

    @ElementCollection(targetClass = Permission.class, fetch = FetchType.EAGER)
    @CollectionTable(
            name = "user_role_permission",
            joinColumns = {@JoinColumn(name = "user_role_id", foreignKey = @ForeignKey(name = "fk_user_role_permission_role_id"))},
            uniqueConstraints = {@UniqueConstraint(columnNames = {"user_role_id", "permission"}, name = "uc_user_role_permission_role_id_permission")}
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "permission")
    private Set<Permission> permissions;

    @Version
    @Column(name = "version")
    private Long version;
}
