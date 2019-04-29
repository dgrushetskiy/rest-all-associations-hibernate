package ru.gothmog.rest.univer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gothmog.rest.univer.model.Assignment;

import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment,Long> {

    List<Assignment> findByStudentId(Long studentId);
}
