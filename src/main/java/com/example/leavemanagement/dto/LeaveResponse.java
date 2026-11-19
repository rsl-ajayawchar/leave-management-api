package com.example.leavemanagement.dto;
import com.example.leavemanagement.model.*;
import java.time.LocalDate;
public record LeaveResponse(Long id, String employeeId, LeaveType leaveType, LocalDate startDate,
                            LocalDate endDate, String reason, LeaveStatus status) {}
