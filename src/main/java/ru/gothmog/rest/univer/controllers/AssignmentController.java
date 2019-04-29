package ru.gothmog.rest.univer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.gothmog.rest.univer.exceptions.ResourceNotFoundException;
import ru.gothmog.rest.univer.model.Assignment;
import ru.gothmog.rest.univer.repositories.AssignmentRepository;
import ru.gothmog.rest.univer.repositories.StudentRepository;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AssignmentController {
    @Autowired
    private AssignmentRepository assignmentRepository;
    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/students/{studentId}/assignments")
    public List<Assignment> getContactByStudentId(@PathVariable Long studentId) {

        if(!studentRepository.existsById(studentId)) {
            throw new ResourceNotFoundException("Student not found!",studentId);
        }

        return assignmentRepository.findByStudentId(studentId);
    }

    @PostMapping("/students/{studentId}/assignments")
    public Assignment addAssignment(@PathVariable Long studentId,
                                    @Valid @RequestBody Assignment assignment) {
        return studentRepository.findById(studentId)
                .map(student -> {
                    assignment.setStudent(student);
                    return assignmentRepository.save(assignment);
                }).orElseThrow(() -> new ResourceNotFoundException("Student not found!",studentId));
    }

    @PutMapping("/students/{studentId}/assignments/{assignmentId}")
    public Assignment updateAssignment(@PathVariable Long studentId,
                                       @PathVariable Long assignmentId,
                                       @Valid @RequestBody Assignment assignmentUpdated) {

        if(!studentRepository.existsById(studentId)) {
            throw new ResourceNotFoundException("Student not found!",studentId);
        }

        return assignmentRepository.findById(assignmentId)
                .map(assignment -> {
                    assignment.setName(assignmentUpdated.getName());
                    assignment.setGrade(assignmentUpdated.getGrade());
                    return assignmentRepository.save(assignment);
                }).orElseThrow(() -> new ResourceNotFoundException("Assignment not found!",assignmentId));
    }

    @DeleteMapping("/students/{studentId}/assignments/{assignmentId}")
    public String deleteAssignment(@PathVariable Long studentId,
                                   @PathVariable Long assignmentId) {

        if(!studentRepository.existsById(studentId)) {
            throw new ResourceNotFoundException("Student not found!",studentId);
        }

        return assignmentRepository.findById(assignmentId)
                .map(assignment -> {
                    assignmentRepository.delete(assignment);
                    return "Deleted Successfully!";
                }).orElseThrow(() -> new ResourceNotFoundException("Contact not found!",assignmentId));
    }
}
