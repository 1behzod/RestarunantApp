package uz.behzod.restaurantApp.repository;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.behzod.restaurantApp.domain.auth.User;
import uz.behzod.restaurantApp.repository.custom.UserRepositoryCustom;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User, Long>, UserRepositoryCustom {


    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.username = :username AND u.deleted = false AND u.id != :id")
    boolean existsByUsernameAndDeletedIsFalseAndIdNot(@Param("id") Long id, @Param("username") String username);


}

