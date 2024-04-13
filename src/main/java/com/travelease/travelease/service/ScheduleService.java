package com.travelease.travelease.service;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
            Schedule schedule = new Schedule();
            schedule.setRouteId(routeRepository.findByRouteId((String)CompanySchedule.get("route_id")));
            schedule.setScheduleStartDate(LocalDate.parse((String)CompanySchedule.get("start_date")));
            schedule.setScheduleEndDate(LocalDate.parse((String)CompanySchedule.get("end_date")));
            schedule.setDriverId(driverVehicleAssociationRepository.findDriverVehicleByVehicleId(vehicleRepository.checkByVehicleNumber((String)CompanySchedule.get("vehicle_number")).getVehicleId()).getDriverId());   
            schedule.setScheduleDays((List<String>)CompanySchedule.get("days"));     
            scheduleRepository.save(schedule);
            return "Schedule Created Successfully";
        }else{
            throw new ResourceNotFoundException("Company Not Found");
        }
    }

    public boolean showschedule(LocalDate startDate, LocalDate endDate, List<String> days) {
        LocalDate date = startDate;
        // Generate list of dates between start and end date for the specified days
        while (!date.isAfter(endDate)) {
            DayOfWeek day=date.getDayOfWeek();
            if(days.contains(day.toString().toLowerCase())){
                
            }          
            date = date.plusDays(1);
        }

        return true;
    }
}
