package com.travelease.travelease.service;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.checkerframework.checker.units.qual.s;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travelease.travelease.exception.ResourceNotFoundException;
import com.travelease.travelease.model.companymodel.company;
import com.travelease.travelease.model.schedulemodel.Schedule;
import com.travelease.travelease.repository.CompanyRepository;
import com.travelease.travelease.repository.DriverVehicleAssociationRepository;
import com.travelease.travelease.repository.RouteRepository;
import com.travelease.travelease.repository.ScheduleRepository;
import com.travelease.travelease.repository.VehicleRepository;


@Service
public class ScheduleService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private VehicleRepository vehicleRepository;
    
    @Autowired
    private DriverVehicleAssociationRepository driverVehicleAssociationRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;


    //create schedule
    @SuppressWarnings("unchecked")
    public String createSchedule(String companyname,Map<String, Object> CompanySchedule) throws Exception{
        company company = companyRepository.findByComapnyName(companyname);
        if(company!=null && company.getCompanyIsActive()){
           
            LocalDate date = LocalDate.parse((String)CompanySchedule.get("start_date"));
            int count=0;
            while (!date.isAfter(LocalDate.parse((String)CompanySchedule.get("end_date")))) {
                Schedule schedule = new Schedule();
                DayOfWeek day=date.getDayOfWeek();
                if(((List<String>)CompanySchedule.get("days")).contains(day.toString().toLowerCase())){
                    schedule.setRouteId(routeRepository.findByRouteId((String)CompanySchedule.get("route_id")));
                    schedule.setDriverId(driverVehicleAssociationRepository.findDriverVehicleByVehicleId(vehicleRepository.checkByVehicleNumber((String)CompanySchedule.get("vehicle_number")).getVehicleId()).getDriverId()); 
                    schedule.setScheduleDate(date);
                    scheduleRepository.save(schedule);
                    count++;
                }          
                date = date.plusDays(1);
            }
            return count+" Schedules Created Successfully";
        }else{
            throw new ResourceNotFoundException("Company Not Found");
        }
    }

    //get schedule
    public List<Map<String, Object>> getSchedule(){
        List<Map<String, Object>> showSchedulesList = new ArrayList<>();
        List<Schedule> schedules=scheduleRepository.findAll();
        for(Schedule schedule : schedules){
            Map<String, Object> showSchedulesMap = new HashMap<>();
            showSchedulesMap.put("schedule_id", schedule.getScheduleId());
            showSchedulesMap.put("schedule_date", schedule.getScheduleDate());
            showSchedulesMap.put("last_updated_time", schedule.getLastUpdatedTime());
            showSchedulesMap.put("route_id", routeRepository.checkById(schedule.getRouteId().getId()).getRouteId());
            showSchedulesMap.put("vehicle_capacity",driverVehicleAssociationRepository.findDriverVehicleByDriverId(schedule.getDriverId().getDriverId()).getVehicleId().getVehicleCapacity());
            showSchedulesMap.put("vehicle_number",driverVehicleAssociationRepository.findDriverVehicleByDriverId(schedule.getDriverId().getDriverId()).getVehicleId().getVehicleNumber());
            showSchedulesMap.put("driver_name", schedule.getDriverId().getDriverName());
            showSchedulesMap.put("passenger_count",routeRepository.findByRouteIdCount(schedule.getRouteId().getRouteId()));
            showSchedulesList.add(showSchedulesMap);
        }

        return showSchedulesList;
    }
    
    public String updateSchedule(Map<String,Object> companyDetails){

        Schedule schedule = scheduleRepository.checkById((Long)companyDetails.get("schedule_id"));
        if(schedule != null){
            schedule.setDriverId(driverVehicleAssociationRepository.findDriverVehicleByVehicleId(vehicleRepository.checkByVehicleNumber((String)companyDetails.get("vehicle_number")).getVehicleId()).getDriverId());
            scheduleRepository.save(schedule);
            return "Updated successfully";
        }else{
            throw new ResourceNotFoundException("schdele Id incorrect");
        }

        
    }

    public String DeleteSchedule(Map<String,Object> companyDetails){
        Schedule schedule = scheduleRepository.checkById((Long)companyDetails.get("schedule_id"));
        if(schedule != null){
            schedule.setScheduleDeletedTime(LocalDateTime.now());
            schedule.setRemarks((String)companyDetails.get("remarks"));
            schedule.setScheduleIsActive(false);
            scheduleRepository.save(schedule);
            return "Deleted successfully";
        }else{
            throw new ResourceNotFoundException("Schdele Id incorrect");
        }
    }
}
