package com.home.profile.student;

import java.util.Optional;
import java.util.UUID;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Student> findWithLockById(UUID id);
}
