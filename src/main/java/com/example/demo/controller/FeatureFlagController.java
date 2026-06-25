package com.example.demo.controller;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.FeatureFlag;
import com.example.demo.service.FeatureFlagService;

@RestController
@RequestMapping("/flags")
public class FeatureFlagController {
    private final FeatureFlagService service;

    public FeatureFlagController(FeatureFlagService service){
        this.service = service;
    }

    @GetMapping
    public List<FeatureFlag> getAllFlags(){
        return service.getAllFlags();
    }
    @GetMapping("/{id}")
    public ResponseEntity<FeatureFlag> getFlagById(@PathVariable Long id) {
        FeatureFlag flag = service.getFlagById(id);
        return ResponseEntity.ok(flag);
    }

    @PostMapping
    public ResponseEntity<FeatureFlag> createFlag(@RequestBody FeatureFlag flag){
        FeatureFlag created = service.createFlag(flag);
        return ResponseEntity.status(201).body(created);
        
    }
    @PatchMapping("/{id}")
    public ResponseEntity<FeatureFlag> updateFlag(@PathVariable Long id, @RequestBody FeatureFlag flag){
        FeatureFlag updated = service.updateFlag(id, flag);
        return ResponseEntity.ok(updated);
        
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlag(@PathVariable Long id){
        service.deleteFlag(id);
        return ResponseEntity.noContent().build();
    }
    
    

    


    @GetMapping("/{name}/evaluate")
    public ResponseEntity<?> evaluateFlag(@PathVariable String name){
        return service.findByName(name)
            .map(flag -> ResponseEntity.ok(
                Map.of("name", flag.getName(), "enabled", flag.getEnabled())
            ))
            .orElse(ResponseEntity.notFound().build());
    }
}
