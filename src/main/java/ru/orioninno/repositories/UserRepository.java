package ru.orioninno.repositories;

import ru.orioninno.entities.Role;
import ru.orioninno.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findAll();
    List<User> findAllByRoles(Role role);
    Optional<User> findByPhone(String phone);
}
