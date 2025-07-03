package com.example.dr_pet.service;


import com.example.dr_pet.DTO.Request.ScheduleRequest;
import com.example.dr_pet.DTO.Response.ScheduleResponse;
import com.example.dr_pet.model.Schedule;
import com.example.dr_pet.repo.AccountRepo;
import com.example.dr_pet.repo.ScheduleRepo;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@Transactional
public class ScheduleService {

    @Autowired
    private ScheduleRepo scheduleRepo;

    @Autowired
    private AccountRepo accountRepo;

    private ScheduleResponse mapToScheduleResponse(Schedule schedule){
        ScheduleResponse dto = new ScheduleResponse();
        dto.setScheduleID(schedule.getScheduleID());
        dto.setStart(schedule.getStart());
        dto.setEnd(schedule.getEnd());
        dto.setDescription(schedule.getDescription());
        dto.setTitle(schedule.getTitle());
        dto.setStatus(schedule.getStatus());
        return dto;
    }

    public ScheduleResponse addSchedule(ScheduleRequest request, String username){
        Schedule schedule = new Schedule();
        schedule.setStart(request.getStart());
        schedule.setEnd(request.getEnd());
        schedule.setDescription(request.getDescription());
        schedule.setTitle(request.getTitle());
        schedule.setStatus(request.getStatus());
        schedule.setActive(true);
        schedule.setAccount(accountRepo.findByUsernameAndIsActiveTrue(username).orElseThrow(()->new RuntimeException("Account not found")));
        scheduleRepo.save(schedule);
        return mapToScheduleResponse(schedule);
    }

    public List<ScheduleResponse> getAllSchedule(String username) {
        List<Schedule> schedules = scheduleRepo.findByAccountAndIsActiveTrue(accountRepo.findByUsernameAndIsActiveTrue(username).orElseThrow(()->new RuntimeException("Account not found")));
        return schedules.stream().map(this::mapToScheduleResponse).toList();
    }

    public void deleteSchedule(Long scheduleId){
        Schedule schedule = scheduleRepo.findByScheduleIDAndIsActiveTrue(scheduleId).orElseThrow(()->new RuntimeException("Schedule not found"));
        schedule.setActive(false);
        scheduleRepo.save(schedule);
    }

    public void updateSchedule(Long scheduleId, @Valid ScheduleRequest request) {
        Schedule schedule = scheduleRepo.findByScheduleIDAndIsActiveTrue(scheduleId).orElseThrow(()->new RuntimeException("Schedule not found"));
        schedule.setStart(request.getStart());
        schedule.setEnd(request.getEnd());
        schedule.setDescription(request.getDescription());
        schedule.setTitle(request.getTitle());
        schedule.setStatus(request.getStatus());
        scheduleRepo.save(schedule);
    }

    public ScheduleResponse getScheduleDetail(Long scheduleId) {
        Schedule schedule = scheduleRepo.findByScheduleIDAndIsActiveTrue(scheduleId).orElseThrow(()->new RuntimeException("Schedule not found"));
        return mapToScheduleResponse(schedule);
    }


}
