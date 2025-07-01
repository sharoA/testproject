package com.example.service;

import com.example.repository.UserRepository;
import com.example.repository.ChainOptimizationRepository;
import com.example.repository.OptimizationRecordRepository;
import com.example.exception.BusinessException;
import com.example.exception.NotFoundException;
import com.example.model.User;
import com.example.model.ChainOptimization;
import com.example.model.OptimizationRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserManagementService {

    private final UserRepository userRepository;
    private final ChainOptimizationRepository chainOptimizationRepository;
    private final OptimizationRecordRepository optimizationRecordRepository;

    @Autowired
    public UserManagementService(UserRepository userRepository,
                                ChainOptimizationRepository chainOptimizationRepository,
                                OptimizationRecordRepository optimizationRecordRepository) {
        this.userRepository = userRepository;
        this.chainOptimizationRepository = chainOptimizationRepository;
        this.optimizationRecordRepository = optimizationRecordRepository;
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));
    }

    @Transactional
    public User createUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new BusinessException("Username already exists");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new BusinessException("Email already exists");
        }
        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(Long id, User userDetails) {
        User user = getUserById(id);
        user.setUsername(userDetails.getUsername());
        user.setEmail(userDetails.getEmail());
        user.setPhone(userDetails.getPhone());
        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }

    @Transactional(readOnly = true)
    public List<ChainOptimization> getChainOptimizations() {
        return chainOptimizationRepository.findAll();
    }

    @Transactional(readOnly = true)
    public ChainOptimization getChainOptimizationById(Long id) {
        return chainOptimizationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Chain optimization not found with id: " + id));
    }

    @Transactional
    public ChainOptimization resubmitChainOptimization(Long id, ChainOptimization updatedOptimization) {
        ChainOptimization existing = getChainOptimizationById(id);
        existing.setOptimizationType(updatedOptimization.getOptimizationType());
        existing.setBeforeValue(updatedOptimization.getBeforeValue());
        existing.setAfterValue(updatedOptimization.getAfterValue());
        existing.setImprovementRate(updatedOptimization.getImprovementRate());
        existing.setStatus(updatedOptimization.getStatus());
        return chainOptimizationRepository.save(existing);
    }

    @Transactional(readOnly = true)
    public List<OptimizationRecord> getOptimizationRecords(Long optimizationId) {
        if (!chainOptimizationRepository.existsById(optimizationId)) {
            throw new NotFoundException("Chain optimization not found with id: " + optimizationId);
        }
        return optimizationRecordRepository.findByOptimizationId(optimizationId);
    }

    @Transactional(readOnly = true)
    public List<ChainOptimization> getOrganizationUnitQuotas() {
        return chainOptimizationRepository.findAll();
    }

    @Transactional(readOnly = true)
    public ChainOptimization getOrganizationUnitQuotaDetails(Long unitId) {
        return chainOptimizationRepository.findById(unitId)
                .orElseThrow(() -> new NotFoundException("Organization unit not found with id: " + unitId));
    }
}