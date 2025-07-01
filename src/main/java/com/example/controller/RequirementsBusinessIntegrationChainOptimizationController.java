package com.example.controller;

import com.example.service.RequirementsBusinessIntegrationChainOptimizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/requirements_business_integration_chain_optimization")
public class RequirementsBusinessIntegrationChainOptimizationController {

    private static final Logger logger = LoggerFactory.getLogger(RequirementsBusinessIntegrationChainOptimizationController.class);

    private final RequirementsBusinessIntegrationChainOptimizationService service;

    @Autowired
    public RequirementsBusinessIntegrationChainOptimizationController(RequirementsBusinessIntegrationChainOptimizationService service) {
        this.service = service;
    }

    @GetMapping("/list")
    public ResponseEntity<List<?>> listRequirementsBusinessIntegrationChainOptimizations() {
        logger.info("Fetching all requirements_business_integration_chain_optimizations");
        List<?> optimizations = service.getAllOptimizations();
        return ResponseEntity.ok(optimizations);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createRequirementsBusinessIntegrationChainOptimization(
            @Valid @RequestBody Object optimizationRequest) {
        logger.info("Creating new requirements_business_integration_chain_optimization");
        Object createdOptimization = service.createOptimization(optimizationRequest);
        return ResponseEntity.ok(createdOptimization);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateRequirementsBusinessIntegrationChainOptimization(
            @Valid @RequestBody Object optimizationRequest) {
        logger.info("Updating requirements_business_integration_chain_optimization");
        Object updatedOptimization = service.updateOptimization(optimizationRequest);
        return ResponseEntity.ok(updatedOptimization);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteRequirementsBusinessIntegrationChainOptimization(
            @RequestParam String id) {
        logger.info("Deleting requirements_business_integration_chain_optimization with id: {}", id);
        service.deleteOptimization(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        logger.error("Error occurred in RequirementsBusinessIntegrationChainOptimizationController: ", e);
        return ResponseEntity.internalServerError().body("An error occurred: " + e.getMessage());
    }
}