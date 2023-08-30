package com.example.quizapp.repository;

import com.example.quizapp.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query(value = "SELECT u from User u WHERE u.role = 'USER'")
    List<User> findAll(Sort sort);

    @Query(value = "SELECT u from User u WHERE u.role = 'ADMIN'")
    User findAllAdmins();

    @Query(value = "SELECT u from User u WHERE u.id=:id")
    User findUserById(@Param("id") Long id);

    @Query(value = "SELECT * FROM _user u",
            countQuery = "SELECT count(*) FROM _user",
            nativeQuery = true)
    Page<User> findAllUsersWithPagination(Pageable pageable);

    @Query(value = "SELECT * from _user u ORDER BY u.first_name", nativeQuery = true)
    List<User> getSortedUsersByName();

    @Query("SELECT u FROM User u JOIN FETCH u.avatar")
    List<User> findAllUsersWithAvatar();
}
