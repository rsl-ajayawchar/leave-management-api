package com.example.leavemanagement.repository;
import com.example.leavemanagement.model.LeaveRequest;
import java.util.*;
public interface LeaveRepository {
    List<LeaveRequest> findAll();
    Optional<LeaveRequest> findById(Long id);
    LeaveRequest save(LeaveRequest leave);
    void deleteById(Long id);
}
