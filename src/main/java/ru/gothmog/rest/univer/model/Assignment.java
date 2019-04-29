package ru.gothmog.rest.univer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "assignments",schema = "dbo")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Assignment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "grade")
    private Integer grade;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false,foreignKey = @ForeignKey(name = "fk_assignments_student"))
    @JsonIgnore
    private Student student;

    public Assignment() {}

    public Assignment(String name, int grade) {
        this.name = name;
        this.grade = grade;
    }

}
