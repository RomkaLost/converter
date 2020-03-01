package ru.lost.converter.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import ru.lost.converter.domain.User;

public interface UserRepo extends JpaRepository<User, Long > {
    User findByUsername(String username);
}
