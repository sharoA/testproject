package com.example.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "chain_optimization", description = "链数优化记录表")
public class ChainOptimization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT")
    private Long id;

    @Column(name = "chain_id", nullable = false, length = 100)
    private String chainId;

    @Column(name = "optimization_type", nullable = false, length = 50)
    private String optimizationType;

    @Column(name = "before_value", nullable = false, precision = 10, scale = 2)
    private BigDecimal beforeValue;

    @Column(name = "after_value", nullable = false, precision = 10, scale = 2)
    private BigDecimal afterValue;

    @Column(name = "improvement_rate", nullable = false, precision = 5, scale = 2)
    private BigDecimal improvementRate;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public ChainOptimization() {
    }

    public ChainOptimization(Long id, String chainId, String optimizationType, BigDecimal beforeValue, 
                           BigDecimal afterValue, BigDecimal improvementRate, String status, 
                           LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.chainId = chainId;
        this.optimizationType = optimizationType;
        this.beforeValue = beforeValue;
        this.afterValue = afterValue;
        this.improvementRate = improvementRate;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChainId() {
        return chainId;
    }

    public void setChainId(String chainId) {
        this.chainId = chainId;
    }

    public String getOptimizationType() {
        return optimizationType;
    }

    public void setOptimizationType(String optimizationType) {
        this.optimizationType = optimizationType;
    }

    public BigDecimal getBeforeValue() {
        return beforeValue;
    }

    public void setBeforeValue(BigDecimal beforeValue) {
        this.beforeValue = beforeValue;
    }

    public BigDecimal getAfterValue() {
        return afterValue;
    }

    public void setAfterValue(BigDecimal afterValue) {
        this.afterValue = afterValue;
    }

    public BigDecimal getImprovementRate() {
        return improvementRate;
    }

    public void setImprovementRate(BigDecimal improvementRate) {
        this.improvementRate = improvementRate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "ChainOptimization{" +
                "id=" + id +
                ", chainId='" + chainId + '\'' +
                ", optimizationType='" + optimizationType + '\'' +
                ", beforeValue=" + beforeValue +
                ", afterValue=" + afterValue +
                ", improvementRate=" + improvementRate +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}