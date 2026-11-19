package com.example.leavemanagement.dto;
import com.example.leavemanagement.model.LeaveType;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
public class CreateLeaveRequest {
    @NotBlank(message="employeeId is required") private String employeeId;
    @NotNull(message="leaveType is required") private LeaveType leaveType;
    @NotNull(message="startDate is required") private LocalDate startDate;
    @NotNull(message="endDate is required") private LocalDate endDate;
    @NotBlank(message="reason is required") @Size(max=500,message="reason must not exceed 500 characters") private String reason;
    @AssertTrue(message="endDate must be on or after startDate")
    public boolean isDateRangeValid(){return startDate==null || endDate==null || !endDate.isBefore(startDate);}
    public String getEmployeeId(){return employeeId;} public void setEmployeeId(String v){employeeId=v;}
    public LeaveType getLeaveType(){return leaveType;} public void setLeaveType(LeaveType v){leaveType=v;}
    public LocalDate getStartDate(){return startDate;} public void setStartDate(LocalDate v){startDate=v;}
    public LocalDate getEndDate(){return endDate;} public void setEndDate(LocalDate v){endDate=v;}
    public String getReason(){return reason;} public void setReason(String v){reason=v;}
}
