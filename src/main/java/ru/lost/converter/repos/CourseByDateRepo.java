package ru.lost.converter.repos;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.lost.converter.domain.CourseByDate;

import java.time.LocalDate;

public interface CourseByDateRepo extends CrudRepository<CourseByDate, Long> {
    CourseByDate findByIdAndDate(String id, LocalDate date);
    @Query(value = "SELECT date FROM CourseByDate where date=(SELECT max(date) FROM CourseByDate) and id=:valuteId")
    LocalDate getMaxDate(String valuteId);

}
