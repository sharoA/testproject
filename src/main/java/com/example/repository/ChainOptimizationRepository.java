package com.example.repository;

import com.example.entity.ChainOptimization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChainOptimizationRepository extends JpaRepository<ChainOptimization, Long> {

    List<ChainOptimization> findByChainId(String chainId);

    List<ChainOptimization> findByOptimizationType(String optimizationType);

    List<ChainOptimization> findByStatus(String status);

    List<ChainOptimization> findByImprovementRateGreaterThan(Double rate);

    List<ChainOptimization> findByImprovementRateBetween(Double minRate, Double maxRate);

    @Query("SELECT co FROM ChainOptimization co WHERE co.chainId = :chainId AND co.optimizationType = :optimizationType")
    List<ChainOptimization> findByChainIdAndOptimizationType(String chainId, String optimizationType);

    @Query("SELECT co FROM ChainOptimization co WHERE co.createdAt >= CURRENT_DATE")
    List<ChainOptimization> findTodayRecords();

    @Query("SELECT AVG(co.improvementRate) FROM ChainOptimization co WHERE co.chainId = :chainId")
    Double calculateAverageImprovementRateByChainId(String chainId);

    @Query("SELECT co FROM ChainOptimization co ORDER BY co.improvementRate DESC LIMIT 10")
    List<ChainOptimization> findTop10ByImprovementRate();

    long countByStatus(String status);
}