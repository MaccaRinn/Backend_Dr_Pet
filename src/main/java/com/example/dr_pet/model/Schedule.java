package com.example.dr_pet.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleID;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String title;
    private String description;
    private boolean isActive;
    private String Status;

//    @ManyToOne
//    @JoinColumn(name = "petID")
//    private Pet pet;

    @ManyToOne
    @JoinColumn(name = "accountID")
    private Account account;
}
