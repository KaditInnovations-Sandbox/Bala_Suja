package com.travelease.travelease.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.travelease.travelease.model.schedulemodel.Schedule;
import com.travelease.travelease.repository.ScheduleRepository;
import com.travelease.travelease.service.ScheduleService;

@CrossOrigin(origins = "${crossorigin}")
@RestController
@RequestMapping("/travelease/")
public class ScheduleController {

    @Autowired 
    private ScheduleService scheduleService;

    @Autowired
    private ScheduleRepository scheduleRepository;

    //create Schedule
    @PostMapping("{companyname}/Schedule")
    public ResponseEntity<String> createSchedule(@RequestHeader String companyname,@RequestBody Map<String,Object> CompanySchedule)throws Exception{
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.createSchedule(companyname,CompanySchedule));
    }

    
    //Get Schedule by date 
    @GetMapping("/ShowSchdeduleDate")
    public boolean getshowschedule(@RequestHeader("start") LocalDate startdate, @RequestHeader("end") LocalDate enddate,@RequestHeader("days") List<String> days) {
        return scheduleService.showschedule(startdate,enddate,days);
    }

    @GetMapping("/ShowSchdedule")
    public List<Schedule> getSchedule() {
        return scheduleRepository.findAll();
    }
    

    
}
