package ru.lost.converter.repos;

import org.springframework.data.repository.CrudRepository;
import ru.lost.converter.domain.Convertation;
import ru.lost.converter.domain.User;

import java.util.List;

public interface ConvertationRepo extends CrudRepository<Convertation, Long> {
    List<Convertation> findByAuthor(User user);
}
