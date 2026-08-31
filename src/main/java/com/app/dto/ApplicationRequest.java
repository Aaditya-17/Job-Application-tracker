package com.app.dto;

import com.app.entity.ApplicationStatus;

import java.time.LocalDate;

public record ApplicationRequest(String company,
                                 String role,
                                 String jobLink,
                                 ApplicationStatus status,
                                 LocalDate appliedDate,
                                 String notes) {
}
