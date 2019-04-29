package ru.gothmog.rest.univer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gothmog.rest.univer.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
}
