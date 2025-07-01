package com.example.service;

import com.example.entity.ChainOptimization;
import com.example.entity.OptimizationRecord;
import com.example.repository.ChainOptimizationRepository;
import com.example.repository.OptimizationRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RequirementsBusinessIntegrationChainOptimizationManagementService {

    private static final Logger logger = LoggerFactory.getLogger(RequirementsBusinessIntegrationChainOptimizationManagementService.class);

    @Autowired
    private ChainOptimizationRepository chainOptimizationRepository;

    @Autowired
    private OptimizationRecordRepository optimizationRecordRepository;

    @Transactional
    public ChainOptimization createChainOptimization(ChainOptimization chainOptimization) {
        try {
            logger.info("Creating new chain optimization: {}", chainOptimization);
            ChainOptimization savedOptimization = chainOptimizationRepository.save(chainOptimization);
            logger.info("Successfully created chain optimization with ID: {}", savedOptimization.getId());
            return savedOptimization;
        } catch (Exception e) {
            logger.error("Error occurred while creating chain optimization", e);
            throw new RuntimeException("Failed to create chain optimization", e);
        }
    }

    @Transactional(readOnly = true)
    public Optional<ChainOptimization> getChainOptimizationById(Long id) {
        try {
            logger.debug("Fetching chain optimization with ID: {}", id);
            return chainOptimizationRepository.findById(id);
        } catch (Exception e) {
            logger.error("Error occurred while fetching chain optimization with ID: {}", id, e);
            throw new RuntimeException("Failed to fetch chain optimization", e);
        }
    }

    @Transactional(readOnly = true)
    public List<ChainOptimization> getAllChainOptimizations() {
        try {
            logger.debug("Fetching all chain optimizations");
            return chainOptimizationRepository.findAll();
        } catch (Exception e) {
            logger.error("Error occurred while fetching all chain optimizations", e);
            throw new RuntimeException("Failed to fetch chain optimizations", e);
        }
    }

    @Transactional
    public ChainOptimization updateChainOptimization(ChainOptimization chainOptimization) {
        try {
            logger.info("Updating chain optimization with ID: {}", chainOptimization.getId());
            if (!chainOptimizationRepository.existsById(chainOptimization.getId())) {
                throw new RuntimeException("Chain optimization not found with ID: " + chainOptimization.getId());
            }
            ChainOptimization updatedOptimization = chainOptimizationRepository.save(chainOptimization);
            logger.info("Successfully updated chain optimization with ID: {}", updatedOptimization.getId());
            return updatedOptimization;
        } catch (Exception e) {
            logger.error("Error occurred while updating chain optimization with ID: {}", chainOptimization.getId(), e);
            throw new RuntimeException("Failed to update chain optimization", e);
        }
    }

    @Transactional
    public void deleteChainOptimization(Long id) {
        try {
            logger.info("Deleting chain optimization with ID: {}", id);
            if (!chainOptimizationRepository.existsById(id)) {
                throw new RuntimeException("Chain optimization not found with ID: " + id);
            }
            chainOptimizationRepository.deleteById(id);
            logger.info("Successfully deleted chain optimization with ID: {}", id);
        } catch (Exception e) {
            logger.error("Error occurred while deleting chain optimization with ID: {}", id, e);
            throw new RuntimeException("Failed to delete chain optimization", e);
        }
    }

    @Transactional
    public OptimizationRecord createOptimizationRecord(OptimizationRecord optimizationRecord) {
        try {
            logger.info("Creating new optimization record for optimization ID: {}", optimizationRecord.getOptimizationId());
            OptimizationRecord savedRecord = optimizationRecordRepository.save(optimizationRecord);
            logger.info("Successfully created optimization record with ID: {}", savedRecord.getId());
            return savedRecord;
        } catch (Exception e) {
            logger.error("Error occurred while creating optimization record", e);
            throw new RuntimeException("Failed to create optimization record", e);
        }
    }

    @Transactional(readOnly = true)
    public List<OptimizationRecord> getOptimizationRecordsByOptimizationId(Long optimizationId) {
        try {
            logger.debug("Fetching optimization records for optimization ID: {}", optimizationId);
            return optimizationRecordRepository.findByOptimizationId(optimizationId);
        } catch (Exception e) {
            logger.error("Error occurred while fetching optimization records for optimization ID: {}", optimizationId, e);
            throw new RuntimeException("Failed to fetch optimization records", e);
        }
    }
}