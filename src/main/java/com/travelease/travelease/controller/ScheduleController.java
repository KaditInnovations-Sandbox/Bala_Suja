package com.travelease.travelease.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.travelease.travelease.service.ScheduleService;

@CrossOrigin(origins = "${crossorigin}")
@RestController
@RequestMapping("/travelease/")
public class ScheduleController {

    @Value("${crossorigin}")
	private String crossorigin;

    @Autowired 
    private ScheduleService scheduleService;

    //create Schedule
    @PostMapping("{companyname}/Schedule")
    public ResponseEntity<String> createSchedule(@RequestHeader String companyname,@RequestBody Map<String,Object> CompanySchedule)throws Exception{
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.createSchedule(companyname,CompanySchedule));
    }

    //get schedule based
    @GetMapping("/Schdedule")
    public List<Map<String, Object>> getSchedule(@RequestHeader String companyname) {
        return scheduleService.getSchedule(companyname);
    }


    //bind Route
    @PutMapping("/Schdedule")
    public ResponseEntity<String> BindRoute(@RequestBody Map<String,Object> CompanySchedule) throws Exception{
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.updateSchedule(CompanySchedule));
    }


    //delete Route
    @DeleteMapping("/Schdedule")
    public ResponseEntity<String> DeleteRoute(@RequestBody Map<String,Object> CompanySchedule) throws Exception{
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.DeleteSchedule(CompanySchedule));
    } 

    
}
