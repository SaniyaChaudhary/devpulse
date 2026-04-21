package com.yourname.devpulse.repository;

import com.project.devpulse.model.WeeklyReport;
import com.project.devpulse.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface WeeklyReportRepository
        extends JpaRepository<WeeklyReport, Long> {

    List<WeeklyReport> findByUser(User user);

    List<WeeklyReport> findByUserOrderByGeneratedAtDesc(User user);

    Optional<WeeklyReport> findByUserAndWeekStartDate(
            User user,
            LocalDateTime weekStartDate
    );
}