package com.example.demo.service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.exception.DuplicateFlagException;
import com.example.demo.exception.FlagNotFoundException;
import com.example.demo.model.FeatureFlag;
import com.example.demo.repository.FeatureFlagRepository;

@Service
public class FeatureFlagService {
    private final FeatureFlagRepository repository;

    public FeatureFlagService(FeatureFlagRepository repository){
        this.repository = repository;
    }
    
    public List<FeatureFlag> getAllFlags(){
        return repository.findAll();
    }
    
    public FeatureFlag getFlagById(Long id){
        return repository.findById(id)
            .orElseThrow(() -> new FlagNotFoundException("Flag not found"));
    }

    public void deleteFlag(Long id){
        FeatureFlag flag = repository.findById(id)
            .orElseThrow(() -> new FlagNotFoundException("Flag not found"));
        
        repository.delete(flag);
    }

    public FeatureFlag createFlag(FeatureFlag flag){
        repository.findByName(flag.getName())
        .ifPresent(f -> {
            throw new DuplicateFlagException("Flag with name '" + flag.getName() + "' already exists");
        });

        flag.setCreatedAt(LocalDateTime.now());
        flag.setUpdatedAt(LocalDateTime.now());
        return repository.save(flag);
    }

    
    public FeatureFlag updateFlag(Long id, FeatureFlag updated) {
    FeatureFlag flag = repository.findById(id)
        .orElseThrow(() -> new FlagNotFoundException("Flag not found"));
        
        

        if (updated.getName() != null) flag.setName(updated.getName());
        if (updated.getDescription() != null) flag.setDescription(updated.getDescription());
        if (updated.getEnabled() != null) {
            flag.setEnabled(updated.getEnabled());
        }
        flag.setUpdatedAt(LocalDateTime.now());
        return repository.save(flag);
    }

    public Optional<FeatureFlag> findByName(String name){
        return repository.findByName(name);
    }
}
