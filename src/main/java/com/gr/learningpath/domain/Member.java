package com.gr.learningpath.domain;

import com.gr.learningpath.domain.group.Group;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@SuperBuilder(toBuilder = true)
@Table(name = "member")
public class Member extends BaseEntity implements Versionable {

    private static final long serialVersionUID = -6921585378263031256L;

    @Column(nullable = false)
    private MemberStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", foreignKey = @ForeignKey(name = "fk_member_student_id"))
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_group_id", nullable = false, foreignKey = @ForeignKey(name = "fk_member_group_id"))
    private Group group;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", foreignKey = @ForeignKey(name = "fk_member_session_id"))
    private Session session;


    private Boolean present;

    @Column(name = "evaluation_value")
    private Integer evaluationValue;

    @Version
    @Column(name = "version")
    private Long version;
}
