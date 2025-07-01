package com.example.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "optimization_record", schema = "public")
public class OptimizationRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "optimization_id", nullable = false)
    private Long optimizationId;

    @Column(name = "step_name", nullable = false, length = 100)
    private String stepName;

    @Column(name = "step_result", columnDefinition = "TEXT")
    private String stepResult;

    @Column(name = "execution_time", nullable = false)
    private Integer executionTime;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    public OptimizationRecord() {
    }

    public OptimizationRecord(Long id, Long optimizationId, String stepName, String stepResult, Integer executionTime, Timestamp createdAt) {
        this.id = id;
        this.optimizationId = optimizationId;
        this.stepName = stepName;
        this.stepResult = stepResult;
        this.executionTime = executionTime;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOptimizationId() {
        return optimizationId;
    }

    public void setOptimizationId(Long optimizationId) {
        this.optimizationId = optimizationId;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public String getStepResult() {
        return stepResult;
    }

    public void setStepResult(String stepResult) {
        this.stepResult = stepResult;
    }

    public Integer getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(Integer executionTime) {
        this.executionTime = executionTime;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OptimizationRecord that = (OptimizationRecord) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(optimizationId, that.optimizationId) &&
                Objects.equals(stepName, that.stepName) &&
                Objects.equals(stepResult, that.stepResult) &&
                Objects.equals(executionTime, that.executionTime) &&
                Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, optimizationId, stepName, stepResult, executionTime, createdAt);
    }

    @Override
    public String toString() {
        return "OptimizationRecord{" +
                "id=" + id +
                ", optimizationId=" + optimizationId +
                ", stepName='" + stepName + '\'' +
                ", stepResult='" + stepResult + '\'' +
                ", executionTime=" + executionTime +
                ", createdAt=" + createdAt +
                '}';
    }
}