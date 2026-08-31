package com.app.dto;

import com.app.entity.ApplicationStatus;

import java.time.LocalDate;

public record ApplicationResponse (Integer id,
                                   String company,
                                   String role,
                                   String jobLink,
                                   ApplicationStatus status,
                                   LocalDate appliedDate,
                                   String notes){
}
