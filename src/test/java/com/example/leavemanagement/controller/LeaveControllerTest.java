package com.example.leavemanagement.controller;
import com.example.leavemanagement.dto.*;
import com.example.leavemanagement.model.*;
import com.example.leavemanagement.service.LeaveService;
import com.example.leavemanagement.exception.GlobalExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@WebMvcTest(LeaveController.class)
@Import(GlobalExceptionHandler.class)
class LeaveControllerTest {
    @Autowired MockMvc mvc;
    @Autowired ObjectMapper mapper;
    @MockBean LeaveService service;
    @Test void validCreateReturns201() throws Exception {
        CreateLeaveRequest req=new CreateLeaveRequest();
        req.setEmployeeId("EMP-1"); req.setLeaveType(LeaveType.ANNUAL);
        req.setStartDate(LocalDate.parse("2026-12-01")); req.setEndDate(LocalDate.parse("2026-12-03")); req.setReason("Vacation");
        when(service.createLeave(any())).thenReturn(new LeaveResponse(1L,"EMP-1",LeaveType.ANNUAL,
            req.getStartDate(),req.getEndDate(),"Vacation",LeaveStatus.PENDING));
        mvc.perform(post("/leaves").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(req)))
            .andExpect(status().isCreated()).andExpect(header().string("Location","/leaves/1"))
            .andExpect(jsonPath("$.status").value("PENDING"));
    }
    @Test void invalidCreateReturns400() throws Exception {
        String body="{\"employeeId\":\"\",\"leaveType\":\"ANNUAL\",\"startDate\":\"2026-12-01\",\"endDate\":\"2026-12-03\",\"reason\":\"Vacation\"}";
        mvc.perform(post("/leaves").contentType(MediaType.APPLICATION_JSON).content(body))
            .andExpect(status().isBadRequest()).andExpect(jsonPath("$.validationErrors.employeeId").exists());
    }
}
