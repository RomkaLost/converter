package ru.lost.converter.repos;

import org.springframework.data.repository.CrudRepository;
import ru.lost.converter.domain.Valute;

public interface ValuteRepo extends CrudRepository<Valute, Long> {//FIXME
    Valute findById(String id);
}
