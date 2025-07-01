package com.example.repository;

import com.example.entity.OptimizationRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OptimizationRecordRepository extends JpaRepository<OptimizationRecord, Long> {

    List<OptimizationRecord> findByOptimizationId(Long optimizationId);

    List<OptimizationRecord> findByStepName(String stepName);

    List<OptimizationRecord> findByStepNameContainingIgnoreCase(String stepName);

    @Query("SELECT o FROM OptimizationRecord o WHERE o.executionTime > :minExecutionTime")
    List<OptimizationRecord> findByExecutionTimeGreaterThan(@Param("minExecutionTime") Integer minExecutionTime);

    @Query("SELECT o FROM OptimizationRecord o WHERE o.executionTime < :maxExecutionTime")
    List<OptimizationRecord> findByExecutionTimeLessThan(@Param("maxExecutionTime") Integer maxExecutionTime);

    @Query("SELECT o FROM OptimizationRecord o WHERE o.createdAt BETWEEN :startDate AND :endDate")
    List<OptimizationRecord> findByCreatedAtBetween(@Param("startDate") java.time.LocalDateTime startDate,
                                                  @Param("endDate") java.time.LocalDateTime endDate);

    @Query("SELECT o FROM OptimizationRecord o WHERE o.optimizationId = :optimizationId AND o.stepName = :stepName")
    List<OptimizationRecord> findByOptimizationIdAndStepName(@Param("optimizationId") Long optimizationId,
                                                           @Param("stepName") String stepName);

    @Query("SELECT o FROM OptimizationRecord o WHERE o.stepResult IS NOT NULL")
    List<OptimizationRecord> findByStepResultIsNotNull();

    @Query("SELECT o FROM OptimizationRecord o WHERE o.stepResult LIKE %:keyword%")
    List<OptimizationRecord> findByStepResultContaining(@Param("keyword") String keyword);
}