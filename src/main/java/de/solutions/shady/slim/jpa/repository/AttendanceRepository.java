package de.solutions.shady.slim.jpa.repository;

import de.solutions.shady.slim.jpa.domain.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    
}
