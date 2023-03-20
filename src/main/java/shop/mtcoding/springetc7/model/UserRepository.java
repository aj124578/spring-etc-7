package shop.mtcoding.springetc7.model;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer>{
 
    @Query("select u from User u where u.username = :username and u.password = :password") // 객체 지향 쿼리
    Optional<User> findByUsernameAndPassword(
        @Param("username") String username,
        @Param("password") String password
    );
}
