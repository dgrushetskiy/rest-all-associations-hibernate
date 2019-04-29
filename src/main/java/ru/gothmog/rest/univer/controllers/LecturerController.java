package ru.gothmog.rest.univer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gothmog.rest.univer.exceptions.ResourceNotFoundException;
import ru.gothmog.rest.univer.model.Lecturer;
import ru.gothmog.rest.univer.model.Student;
import ru.gothmog.rest.univer.repositories.LecturerRepository;
import ru.gothmog.rest.univer.repositories.StudentRepository;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/api/lecturers")
public class LecturerController {

    @Autowired
    private LecturerRepository lecturers;

    @Autowired
    private StudentRepository students;

    @PostMapping() // Validates the request body as a Lecturer type
    public Lecturer createLecturer(@Valid @RequestBody Lecturer lecturer){
        // Saves and return the new lecturer
        return this.lecturers.save(lecturer);
    }

    @GetMapping() // Finds all stored lecturers in a pageable format
    public Page<Lecturer> getLecturers(Pageable pageable){
        return this.lecturers.findAll(pageable);
    }

    @GetMapping("/{id}") // Finds a lecturer by id (the variable must be wrapped by "{}" and match the @PathVariable name
    public Lecturer getLecturer(@PathVariable Long id){
        // If the record exists by id return it, otherwise throw an exception
        return this.lecturers.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Lecturer", id)
        );
    }

    @PutMapping() // Validates the request body as a Lecturer type
    public Lecturer updateLecturer(@Valid @RequestBody Lecturer lecturer){
        // Finds lecturer by id, maps it's content, updates new values and save. Throws an exception if not found.
        return this.lecturers.findById(lecturer.getId()).map((toUpdate) -> {
            toUpdate.setFirstName(lecturer.getFirstName());
            toUpdate.setLastName(lecturer.getLastName());
            toUpdate.setSalary(lecturer.getSalary());
            return this.lecturers.save(toUpdate);
        }).orElseThrow(() -> new ResourceNotFoundException("Lecturer", lecturer.getId()));
    }

    @DeleteMapping("/{id}") // Find lecturer by id
    public ResponseEntity deleteLecturer(@PathVariable Long id){
        // If id exists, delete the record and return a response message, otherwise throws exception
        return this.lecturers.findById(id).map((toDelete) -> {
            this.lecturers.delete(toDelete);
            return ResponseEntity.ok("Lecturer id " + id + " deleted");
        }).orElseThrow(() -> new ResourceNotFoundException("Lecturer", id));
    }

    @GetMapping("/{lecturerId}/students")
    public Set<Student> getStudents(@PathVariable Long lecturerId){
        // Finds lecturer by id and returns it's recorded students, otherwise throws exception
        return this.lecturers.findById(lecturerId).map((lecturer) -> {
            return lecturer.getStudents();
        }).orElseThrow(() -> new ResourceNotFoundException("Lecturer", lecturerId));
    }

    @PostMapping("/{id}/students/{studentId}") // Path variable names must match with method's signature variables.
    public Set<Student> addStudent(@PathVariable Long id, @PathVariable Long studentId){
        // Finds a persisted student
        Student student = this.students.findById(studentId).orElseThrow(
                () -> new ResourceNotFoundException("Student", studentId)
        );

        // Finds a lecturer and adds the given student to the lecturer's set.
        return this.lecturers.findById(id).map((lecturer) -> {
            lecturer.getStudents().add(student);
            return this.lecturers.save(lecturer).getStudents();
        }).orElseThrow(() -> new ResourceNotFoundException("Lecturer", id));
    }
}
