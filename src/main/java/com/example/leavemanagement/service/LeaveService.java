package com.example.leavemanagement.service;
import com.example.leavemanagement.dto.*;
import com.example.leavemanagement.exception.LeaveNotFoundException;
import com.example.leavemanagement.model.*;
import com.example.leavemanagement.repository.LeaveRepository;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class LeaveService {
    private final LeaveRepository repository;
    public LeaveService(LeaveRepository repository){this.repository=repository;}
    public List<LeaveResponse> getAllLeaves(){return repository.findAll().stream().map(this::response).toList();}
    public LeaveResponse getLeaveById(Long id){return response(find(id));}
    public LeaveResponse createLeave(CreateLeaveRequest request){
        LeaveRequest leave=new LeaveRequest(null,request.getEmployeeId().trim(),request.getLeaveType(),
            request.getStartDate(),request.getEndDate(),request.getReason().trim(),LeaveStatus.PENDING);
        return response(repository.save(leave));
    }
    public LeaveResponse updateLeave(Long id,UpdateLeaveRequest request){
        LeaveRequest leave=find(id);
        leave.setEmployeeId(request.getEmployeeId().trim()); leave.setLeaveType(request.getLeaveType());
        leave.setStartDate(request.getStartDate()); leave.setEndDate(request.getEndDate());
        leave.setReason(request.getReason().trim());
        if(request.getStatus()!=null) leave.setStatus(request.getStatus());
        return response(repository.save(leave));
    }
    public void deleteLeave(Long id){find(id); repository.deleteById(id);}
    private LeaveRequest find(Long id){return repository.findById(id).orElseThrow(()->new LeaveNotFoundException(id));}
    private LeaveResponse response(LeaveRequest l){
        return new LeaveResponse(l.getId(),l.getEmployeeId(),l.getLeaveType(),l.getStartDate(),l.getEndDate(),l.getReason(),l.getStatus());
    }
}
