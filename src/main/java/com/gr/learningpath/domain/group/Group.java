package com.gr.learningpath.domain.group;

import com.gr.learningpath.domain.BaseEntity;
import com.gr.learningpath.domain.Department;
import com.gr.learningpath.domain.Member;
import com.gr.learningpath.domain.Versionable;
import com.gr.learningpath.domain.course.Course;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@SuperBuilder(toBuilder = true)
@Table(name = "student_group")
public class Group extends BaseEntity implements Versionable {

    private static final long serialVersionUID = 6755993828137695756L;

    @Column(name = "image_name")
    private String imageName;
    @Column(name = "image_type")
    private String imageType;

    @Column(name = "image_binary")
    @Lob
    private byte[] imageBinary;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", foreignKey = @ForeignKey(name = "fk_group_department_id"))
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", foreignKey = @ForeignKey(name = "fk_group_course_id"))
    private Course course;

    @OneToMany(mappedBy = "group", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @Builder.Default
    private List<Member> members = new ArrayList<>();

    public void addMember(Member member) {
        members.add(member);
        member.setGroup(this);
    }

    public void removeMember(Member member) {
        members.remove(member);
        member.setGroup(null);
    }

    @Version
    @Column(name = "version")
    private Long version;

}
