package com.example.repository;

import com.example.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByUsername(String username);

    Optional<Users> findByEmail(String email);

    List<Users> findByPhone(String phone);

    @Query("SELECT u FROM Users u WHERE u.username LIKE %:keyword% OR u.email LIKE %:keyword%")
    List<Users> searchByUsernameOrEmail(@Param("keyword") String keyword);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    @Query("SELECT u FROM Users u WHERE u.createdAt >= CURRENT_DATE")
    List<Users> findTodayRegisteredUsers();

    long countByPhoneIsNotNull();
}