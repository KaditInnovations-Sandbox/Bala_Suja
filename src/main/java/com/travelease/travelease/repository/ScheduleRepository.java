package com.travelease.travelease.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travelease.travelease.model.schedulemodel.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long>{
    
}
