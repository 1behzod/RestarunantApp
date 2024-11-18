package uz.behzod.RestarunantApp.repository.impl;


import uz.behzod.RestarunantApp.domain.auth.User;
import uz.behzod.RestarunantApp.repository.BaseRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends BaseRepository<User, Long> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    Optional<User> findById(Long id);

    List<User> findAll();

    void deleteById(Long id);

}

