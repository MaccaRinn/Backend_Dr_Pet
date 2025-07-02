package com.example.dr_pet.repo;

import com.example.dr_pet.model.Account;
import com.example.dr_pet.model.Pet;
import com.example.dr_pet.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ScheduleRepo extends JpaRepository <Schedule, Long>{
    Optional<Schedule> findByScheduleID(Long scheduleID);
    Optional<Schedule> findByScheduleIDAndIsActiveTrue(Long scheduleID);
    // Tìm tất cả schedule của một Pet mà isActive = true

    List<Schedule> findByAccountAndIsActiveTrue(Account accountID);


    List<Schedule> findByPetAndIsActiveTrue(Pet pet);

}
