package com.example.dr_pet.DTO.Request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleRequest {

    @NotNull(message = "Date time must not be empty")
    private LocalDateTime DateTime;
    @NotBlank(message = "Description must not be empty")
    private String description;
    @NotBlank(message = "Title must not be empty")
    private String title;
    @NotBlank(message = "Status must not be empty")
    private String status;



}
