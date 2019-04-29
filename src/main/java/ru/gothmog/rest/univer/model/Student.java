package ru.gothmog.rest.univer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "student",schema = "dbo")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Student extends Person{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column
    private Float gpa;

    @ManyToMany( fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE, CascadeType.PERSIST},
            mappedBy = "students")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Set<Lecturer> lecturers = new HashSet<>();

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Assignment> assignments;

    @OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL,
            mappedBy = "student")
    @JsonIgnore
    private Contact contact;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(id, student.id) &&
                Objects.equals(gpa, student.gpa) &&
                Objects.equals(lecturers, student.lecturers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, gpa, lecturers);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append(id)
                .append(gpa)
                .append(lecturers)
                .toString();
    }

//     CONSTRUCTORS

    public Student() { }

    public Student(String firstName, String lastName, Float gpa) {
        super(firstName, lastName);
        this.gpa = gpa;
    }

    // GETTERS AND SETTERS

//    public Long getId() {return id;}
//    public void setId(Long id) {this.id = id;}
//
//    public Float getGpa() {return gpa;}
//    public void setGpa(Float gpa) {this.gpa = gpa;}

//    public Set<Lecturer> getLecturers() {return lecturers;}
//    public void setLecturers(Set<Lecturer> lecturers) {this.lecturers = lecturers;}
}
