package com.travelease.travelease.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.travelease.travelease.model.passengermodel.passenger;
import com.travelease.travelease.repository.CompanyPassengerRepository;
import com.travelease.travelease.repository.PassengerRepository;

@Service
public class PassengerService {
    
    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private CompanyPassengerRepository companyPassengerRepository;

    public List<passenger> getAllPassengerDetails(Long CompanyId){
        // companyPassengerRepository.findByCompanyId(CompanyId);
        return null;
    }
}
