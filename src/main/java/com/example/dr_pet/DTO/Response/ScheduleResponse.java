package com.example.dr_pet.DTO.Response;


import com.example.dr_pet.model.Account;
import com.example.dr_pet.model.Pet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleResponse {

    private Long scheduleID;
    private String title;
    private LocalDateTime start;
    private LocalDateTime end;
    private String description;
    private String Status;

}
