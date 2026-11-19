package com.example.leavemanagement.repository;
import com.example.leavemanagement.model.LeaveRequest;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
@Repository
public class InMemoryLeaveRepository implements LeaveRepository {
    private final ConcurrentMap<Long,LeaveRequest> data=new ConcurrentHashMap<>();
    private final AtomicLong sequence=new AtomicLong();
    public List<LeaveRequest> findAll(){return data.values().stream().sorted(Comparator.comparing(LeaveRequest::getId)).toList();}
    public Optional<LeaveRequest> findById(Long id){return Optional.ofNullable(data.get(id));}
    public LeaveRequest save(LeaveRequest leave){
        if(leave.getId()==null) leave.setId(sequence.incrementAndGet());
        data.put(leave.getId(),leave); return leave;
    }
    public void deleteById(Long id){data.remove(id);}
}
