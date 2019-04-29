package ru.gothmog.rest.univer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "lecturer", schema = "dbo")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Lecturer extends Person{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(nullable = false)
    private BigDecimal salary;

    @ManyToMany( fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable( name = "lecturers_students", schema = "dbo",
            joinColumns = {@JoinColumn(name = "lecturer_id", referencedColumnName = "id",foreignKey = @ForeignKey(name = "fk_lecturers_students_lecturer_id"))},
            inverseJoinColumns = {@JoinColumn(name = "student_id", referencedColumnName = "id",foreignKey = @ForeignKey(name = "fk_lecturers_students_student_id"))}
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Set<Student> students = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lecturer lecturer = (Lecturer) o;
        return Objects.equals(id, lecturer.id) &&
                Objects.equals(salary, lecturer.salary) &&
                Objects.equals(students, lecturer.students);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, salary, students);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append(id)
                .append(salary)
                .append(students)
                .toString();
    }

//     CONSTRUCTORS

    public Lecturer() { }

    public Lecturer(String firstName, String lastName, BigDecimal salary) {
        super(firstName, lastName);
        this.salary = salary;
    }

    // GETTERS AND SETTERS

//    public Long getId() {return id;}
//    public void setId(Long id) {this.id = id;}
//
//    public BigInteger getSalary() {return salary;}
//    public void setSalary(BigInteger salary) {this.salary = salary;}

//    public Set<Student> getStudents() {return students;}
//    public void setStudents(Set<Student> students) {this.students = students;}
}
