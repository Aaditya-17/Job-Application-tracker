package com.app.controller;

import com.app.dto.ApplicationRequest;
import com.app.dto.ApplicationResponse;
import com.app.entity.ApplicationStatus;
import com.app.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/applications")
public class ApplicationController {
    private final ApplicationService applicationService;

    @PostMapping
    public ResponseEntity<ApplicationResponse> create(
            Authentication auth, @RequestBody ApplicationRequest request) {
        return ResponseEntity.ok(applicationService.create(auth.getName(), request));
    }

    @GetMapping
    public ResponseEntity<List<ApplicationResponse>> getAll(
            Authentication auth,
            @RequestParam(required = false) ApplicationStatus status,
            @RequestParam(required = false) String company) {
        return ResponseEntity.ok(applicationService.getAll(auth.getName(), status, company));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApplicationResponse> update(
            Authentication auth, @PathVariable Integer id, @RequestBody ApplicationRequest request) {
        return ResponseEntity.ok(applicationService.update(auth.getName(), id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(Authentication auth, @PathVariable Integer id) {
        applicationService.delete(auth.getName(), id);
        return ResponseEntity.noContent().build();
    }
}
