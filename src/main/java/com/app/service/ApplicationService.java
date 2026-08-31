package com.app.service;

import com.app.dto.ApplicationRequest;
import com.app.dto.ApplicationResponse;
import com.app.entity.Application;
import com.app.entity.ApplicationStatus;
import com.app.entity.User;
import com.app.exception.ResourceNotFoundException;
import com.app.repository.ApplicationRepository;
import com.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;

    public ApplicationResponse create(String userEmail, ApplicationRequest request){
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(()->new ResourceNotFoundException("User Not Found"));

        Application application = Application.builder()
                .company(request.company())
                .role(request.role())
                .jobLink(request.jobLink())
                .status(request.status() !=null?request.status() : ApplicationStatus.APPLIED)
                .appliedDate(request.appliedDate())
                .notes(request.notes())
                .user(user)
                .build();

            applicationRepository.save(application);
            return toResponse(application);
    }

    public List<ApplicationResponse> getAll(String userEmail, ApplicationStatus status, String company) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<Application> apps;
        if (status != null) {
            apps = applicationRepository.findByUserIdAndStatus(user.getId(), status);
        } else if (company != null && !company.isBlank()) {
            apps = applicationRepository.findByUserIdAndCompanyContainingIgnoreCase(user.getId(), company);
        } else {
            apps = applicationRepository.findByUserId(user.getId());
        }

        return apps.stream().map(this::toResponse).toList();
    }

    public ApplicationResponse update(String userEmail, Integer id, ApplicationRequest request) {
        Application app = getOwnedApplication(userEmail, id);

        app.setCompany(request.company());
        app.setRole(request.role());
        app.setJobLink(request.jobLink());
        app.setStatus(request.status());
        app.setAppliedDate(request.appliedDate());
        app.setNotes(request.notes());

        applicationRepository.save(app);
        return toResponse(app);
    }

    private Application getOwnedApplication(String userEmail, Integer id) {
        Application app = applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found"));

        if (!app.getUser().getEmail().equals(userEmail)) {
            throw new ResourceNotFoundException("Application not found"); // deliberately same message
        }

        return app;
    }

    public void delete(String userEmail, Integer id) {
        Application app = getOwnedApplication(userEmail, id);
        applicationRepository.delete(app);
    }

    private ApplicationResponse toResponse(Application app) {
        return new ApplicationResponse(
                app.getId(), app.getCompany(), app.getRole(), app.getJobLink(),
                app.getStatus(), app.getAppliedDate(), app.getNotes()
        );
    }
}

