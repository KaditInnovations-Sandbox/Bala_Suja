package com.travelease.travelease.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travelease.travelease.exception.ResourceNotFoundException;
import com.travelease.travelease.model.companymodel.company;
import com.travelease.travelease.repository.CompanyRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public List<company> getAllCompanyDetails(){
        return companyRepository.findAll();
    }

    @SuppressWarnings("null")
    public String createCompany(company company) throws IOException{
        companyRepository.save(company);
        return "created";
    }

    public String deleteCompany(String email) {
        company company=companyRepository.findByCompanyEmail(email);
        if(company==null){
            throw new ResourceNotFoundException("Company not found");
        }else{
            // company.setAdminIsActive(false);
            companyRepository.save(company);
            return "Deleted";
        }
    }
}
