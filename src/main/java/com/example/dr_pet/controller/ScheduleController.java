package com.example.dr_pet.controller;


import com.example.dr_pet.DTO.Request.ScheduleRequest;
import com.example.dr_pet.DTO.Response.ProductResponse;
import com.example.dr_pet.DTO.Response.ScheduleResponse;
import com.example.dr_pet.model.UserPrincipal;
import com.example.dr_pet.service.ScheduleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;


    //add
    @PostMapping("add")
    public ResponseEntity<?> addSchedule(@Valid @RequestBody ScheduleRequest request, BindingResult bindingResult, @AuthenticationPrincipal UserPrincipal userPrincipal){
        if (bindingResult.hasErrors()) {
            String errMsg = bindingResult.getFieldErrors().stream()
                    .map(err -> err.getField() + ": " + err.getDefaultMessage())
                    .reduce((a, b) -> a + "; " + b)
                    .orElse("Dữ liệu không hợp lệ");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST).body(errMsg);
        }
        try{
            scheduleService.addSchedule(request,userPrincipal.getUsername());
            return ResponseEntity.ok("Add successfully");
        }catch (RuntimeException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }


    //all by user
    @GetMapping
    public ResponseEntity<?> getAllSchedule(@AuthenticationPrincipal UserPrincipal userPrincipal){
        List<ScheduleResponse> listSchedule = scheduleService.getAllSchedule(userPrincipal.getUsername());
        if (listSchedule.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Empty List");
        }
        return ResponseEntity.ok(listSchedule);
    }

    //detail
    @GetMapping("detail/{scheduleId}")
    public ResponseEntity<?> getScheduleDetail(@PathVariable Long scheduleId){
        ScheduleResponse scheduleResponse = scheduleService.getScheduleDetail(scheduleId);
        if (scheduleResponse == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(scheduleResponse);
    }

//    //all by pet
//    @GetMapping("all/pet/{petId}")
//    public ResponseEntity<?> getAllScheduleOfPet(@PathVariable Long petId){
//        List<ScheduleResponse> listSchedule = scheduleService.getAllScheduleOfPet(petId);
//        if (listSchedule.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Empty List");
//        }
//        return ResponseEntity.ok(listSchedule);
//    }


    //delete
    @PostMapping("delete/{scheduleId}")
    public ResponseEntity<?> deleteSchedule(@PathVariable Long scheduleId){
        scheduleService.deleteSchedule(scheduleId);
        return ResponseEntity.ok("Delete successfully");
    }


    //update
    @PutMapping("update/{scheduleId}")
    public ResponseEntity<?> updateSchedule(@PathVariable Long scheduleId, @Valid @RequestBody ScheduleRequest request, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            String errMsg = bindingResult.getFieldErrors().stream()
                    .map(err -> err.getField() + ": " + err.getDefaultMessage())
                    .reduce((a, b) -> a + "; " + b)
                    .orElse("Dữ liệu không hợp lệ");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST).body(errMsg);
        }
        try{
            scheduleService.updateSchedule(scheduleId,request);
            return ResponseEntity.ok("Update successfully");
        }catch (RuntimeException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }





}
