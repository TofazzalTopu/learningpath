package com.gr.learningpath.domain;

import com.gr.learningpath.domain.course.Course;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@SuperBuilder(toBuilder = true)
@Table(name = "user", uniqueConstraints = {@UniqueConstraint(columnNames = "username", name = "uk_users_username")})
public class User extends BaseEntity implements Versionable {
    private static final long serialVersionUID = 9079462241491508709L;
    @Column(name = "is_student")
    public Boolean isStudent;
    @Column(name = "is_mentor")
    public Boolean isMentor;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "display_name")
    private String displayName;
    @Column(name = "active")
    @Builder.Default
    private Boolean active = false;
    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_user_role_user_id")), inverseJoinColumns = @JoinColumn(name = "role_id", nullable = false, foreignKey = @ForeignKey(name = "fk_users_roles_role_id"))

    )
    @Singular
    private Set<Role> roles;
    @Version
    @Column(name = "version")
    private Long version;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    private Student student;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "userId", cascade = CascadeType.ALL)
    private Mentor mentor;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "lives_in")
    private String livesIn;
    @Column(name = "registration_date", nullable = false)
    private LocalDate registrationDate;
    @Column(name = "contact_number")
    private String contactNumber;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "facebook_url")
    private String facebookUrl;
    @Column(name = "skype_id")
    private String skypeId;
    @Type(type = "text")
    @Column(name = "about_me")
    private String aboutMe;
    @Column(name = "courses_of_interest")
    private String coursesOfInterest;

//    @OneToOne(mappedBy="toUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private Notification notification;

    @OneToMany(targetEntity = Course.class,
            cascade = CascadeType.ALL,
            mappedBy = "user",
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<Course> courses;

    public Set<Permission> getPermissions() {
        return roles == null ? Collections.emptySet()
                : roles.stream().flatMap(userRole -> userRole.getPermissions().stream())
                .collect(Collectors.toSet());
    }

    public boolean hasPermission(Permission requiredPermission) {
        return getPermissions().stream().anyMatch(permission -> permission.equals(requiredPermission));
    }


}
