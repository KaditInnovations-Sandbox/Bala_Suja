package com.travelease.travelease.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.travelease.travelease.model.schedulemodel.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long>{
    
    @Query(nativeQuery = true, value ="SELECT * from Schedule e WHERE e.schedule_id=:id")
    Schedule checkById(@Param("id")Long id);
}
