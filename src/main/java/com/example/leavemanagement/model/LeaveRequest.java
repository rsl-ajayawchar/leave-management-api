package com.example.leavemanagement.model;
import java.time.LocalDate;
public class LeaveRequest {
    private Long id;
    private String employeeId;
    private LeaveType leaveType;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
    private LeaveStatus status;
    public LeaveRequest() {}
    public LeaveRequest(Long id, String employeeId, LeaveType leaveType, LocalDate startDate,
                        LocalDate endDate, String reason, LeaveStatus status) {
        this.id=id; this.employeeId=employeeId; this.leaveType=leaveType; this.startDate=startDate;
        this.endDate=endDate; this.reason=reason; this.status=status;
    }
    public Long getId(){return id;} public void setId(Long v){id=v;}
    public String getEmployeeId(){return employeeId;} public void setEmployeeId(String v){employeeId=v;}
    public LeaveType getLeaveType(){return leaveType;} public void setLeaveType(LeaveType v){leaveType=v;}
    public LocalDate getStartDate(){return startDate;} public void setStartDate(LocalDate v){startDate=v;}
    public LocalDate getEndDate(){return endDate;} public void setEndDate(LocalDate v){endDate=v;}
    public String getReason(){return reason;} public void setReason(String v){reason=v;}
    public LeaveStatus getStatus(){return status;} public void setStatus(LeaveStatus v){status=v;}
}
