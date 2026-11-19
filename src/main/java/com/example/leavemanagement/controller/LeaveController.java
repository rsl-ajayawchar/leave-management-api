package com.example.leavemanagement.controller;
import com.example.leavemanagement.dto.*;
import com.example.leavemanagement.service.LeaveService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;
@RestController
@RequestMapping("/leaves")
public class LeaveController {
    private final LeaveService service;
    public LeaveController(LeaveService service){this.service=service;}
    @GetMapping public ResponseEntity<List<LeaveResponse>> getAll(){return ResponseEntity.ok(service.getAllLeaves());}
    @GetMapping("/{id}") public ResponseEntity<LeaveResponse> getById(@PathVariable Long id){return ResponseEntity.ok(service.getLeaveById(id));}
    @PostMapping public ResponseEntity<LeaveResponse> create(@Valid @RequestBody CreateLeaveRequest request){
        LeaveResponse created=service.createLeave(request);
        return ResponseEntity.created(URI.create("/leaves/"+created.id())).body(created);
    }
    @PutMapping("/{id}") public ResponseEntity<LeaveResponse> update(@PathVariable Long id,@Valid @RequestBody UpdateLeaveRequest request){
        return ResponseEntity.ok(service.updateLeave(id,request));
    }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id){service.deleteLeave(id);}
}
