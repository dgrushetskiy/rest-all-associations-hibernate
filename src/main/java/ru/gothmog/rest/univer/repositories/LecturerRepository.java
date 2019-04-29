package ru.gothmog.rest.univer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gothmog.rest.univer.model.Lecturer;

@Repository
public interface LecturerRepository extends JpaRepository<Lecturer,Long> {
}
