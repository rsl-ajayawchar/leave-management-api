package com.example.leavemanagement.service;
import com.example.leavemanagement.dto.*;
import com.example.leavemanagement.exception.LeaveNotFoundException;
import com.example.leavemanagement.model.*;
import com.example.leavemanagement.repository.LeaveRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
@ExtendWith(MockitoExtension.class)
class LeaveServiceTest {
    @Mock LeaveRepository repository;
    LeaveService service;
    @BeforeEach void setup(){service=new LeaveService(repository);}
    @Test void createSetsPendingStatus(){
        CreateLeaveRequest request=new CreateLeaveRequest();
        request.setEmployeeId("EMP-1"); request.setLeaveType(LeaveType.ANNUAL);
        request.setStartDate(LocalDate.parse("2026-12-01")); request.setEndDate(LocalDate.parse("2026-12-03"));
        request.setReason("Vacation");
        when(repository.save(any())).thenAnswer(inv->{LeaveRequest l=inv.getArgument(0);l.setId(1L);return l;});
        LeaveResponse result=service.createLeave(request);
        assertEquals(1L,result.id()); assertEquals(LeaveStatus.PENDING,result.status());
    }
    @Test void missingIdThrowsNotFound(){
        when(repository.findById(44L)).thenReturn(Optional.empty());
        assertThrows(LeaveNotFoundException.class,()->service.getLeaveById(44L));
    }
    @Test void deleteRemovesExistingLeave(){
        LeaveRequest leave=new LeaveRequest(2L,"EMP-2",LeaveType.SICK,LocalDate.now(),LocalDate.now(),"Sick",LeaveStatus.PENDING);
        when(repository.findById(2L)).thenReturn(Optional.of(leave));
        service.deleteLeave(2L); verify(repository).deleteById(2L);
    }
}
