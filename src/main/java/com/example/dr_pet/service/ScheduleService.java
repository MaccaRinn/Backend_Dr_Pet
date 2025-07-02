package com.example.dr_pet.service;


import com.example.dr_pet.DTO.Request.ScheduleRequest;
import com.example.dr_pet.DTO.Response.ScheduleResponse;
import com.example.dr_pet.model.Schedule;
import com.example.dr_pet.repo.AccountRepo;
import com.example.dr_pet.repo.PetRepo;
import com.example.dr_pet.repo.ScheduleRepo;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.hibernate.validator.internal.IgnoreForbiddenApisErrors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ScheduleService {

    @Autowired
    private ScheduleRepo scheduleRepo;

    @Autowired
    private PetRepo petRepo;

    @Autowired
    private AccountRepo accountRepo;

    private ScheduleResponse mapToScheduleResponse(Schedule schedule){
        ScheduleResponse dto = new ScheduleResponse();
        dto.setScheduleID(schedule.getScheduleID());
        dto.setDateTime(schedule.getDateTime());
        dto.setDescription(schedule.getDescription());
        dto.setPet(schedule.getPet());
        dto.setAccount(schedule.getAccount());
        return dto;
    }


    public ScheduleResponse addSchedule(ScheduleRequest request, String username){
        Schedule schedule = new Schedule();
        schedule.setDateTime(request.getDateTime());
        schedule.setDescription(request.getDescription());
        schedule.setPet(petRepo.findByPetIDAndIsActiveTrue(request.getPetId()).orElseThrow(()->new RuntimeException("Pet not found")));
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
        schedule.setDateTime(request.getDateTime());
        schedule.setDescription(request.getDescription());
        scheduleRepo.save(schedule);
    }

    public ScheduleResponse getScheduleDetail(Long scheduleId) {
        Schedule schedule = scheduleRepo.findByScheduleIDAndIsActiveTrue(scheduleId).orElseThrow(()->new RuntimeException("Schedule not found"));
        return mapToScheduleResponse(schedule);
    }

    public List<ScheduleResponse> getAllScheduleOfPet(Long petId) {
        List<Schedule> schedules = scheduleRepo.findByPetAndIsActiveTrue(petRepo.findById(petId).orElseThrow(()->new RuntimeException("Pet not found")));
        return schedules.stream().map(this::mapToScheduleResponse).toList();
    }

}
