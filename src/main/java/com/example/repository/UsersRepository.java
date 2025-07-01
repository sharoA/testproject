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

    List<Users> findByUsernameContainingIgnoreCase(String username);

    List<Users> findByEmailContainingIgnoreCase(String email);

    @Query("SELECT u FROM Users u WHERE u.phone = :phone")
    Optional<Users> findByPhone(@Param("phone") String phone);

    @Query("SELECT u FROM Users u WHERE u.createdAt >= :startDate AND u.createdAt <= :endDate")
    List<Users> findUsersCreatedBetween(@Param("startDate") java.time.LocalDateTime startDate,
                                      @Param("endDate") java.time.LocalDateTime endDate);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    @Query("SELECT COUNT(u) FROM Users u WHERE u.username LIKE %:keyword% OR u.email LIKE %:keyword%")
    long countByUsernameOrEmailContaining(@Param("keyword") String keyword);
}