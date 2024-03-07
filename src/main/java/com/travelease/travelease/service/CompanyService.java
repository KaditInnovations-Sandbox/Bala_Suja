package com.travelease.travelease.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    //get all company
    public List<company> getAllCompanyDetails(){
        return companyRepository.findAll();
    }

    //create company
    public String createCompany(company company) throws Exception{
        company iscompany=companyRepository.findByComapnyName(company.getCompanyName());
        if(iscompany==null){
            companyRepository.save(company);
            return "created";
        }else{
            throw new Exception();
        }
    }

    //get all active vehicle
    public List<company> getAllActiveCompany(){
        return companyRepository.findByAccessTrue();
    }


    //get all inactive vehicle
    public List<company> getAllInactiveCompany(){
        return companyRepository.findByAccessFalse();
    }

    //update company
    public String updateCompany(company companyDetails)throws Exception{
        company company=companyRepository.checkById(companyDetails.getCompanyId());
        if(companyRepository.findById(companyDetails.getCompanyId()).isPresent()){
            company.setCompanyName(companyDetails.getCompanyName());;
            company.setCompanyEmail(companyDetails.getCompanyEmail());
            company.setCompanyPhone(companyDetails.getCompanyPhone());
            company.setCompanyStartDate(companyDetails.getCompanyStartDate());
            company.setCompanyEndDate(companyDetails.getCompanyEndDate());
            company.setCompanyPoc(companyDetails.getCompanyPoc());
            company.setCompanyLastUpdatedTime(LocalDateTime.now());
            companyRepository.save(company);
            return "updated";
        }else{
            throw new Exception();
        }
    }

    //Company Remove access
    public String DeleteCompany(String companyName) throws Exception{
        company company=companyRepository.findByComapnyName(companyName);
        if(company==null){
            throw new ResourceNotFoundException("Company Not found");
        }else{
            company.setCompanyIsActive(false);
            companyRepository.save(company);
            return "Deleted";
        }       
        
    }

    //Bind Company
    public String BindCompany(String companyName) throws Exception{
        company company=companyRepository.findByComapnyName(companyName);
        if(company==null){
            throw new ResourceNotFoundException("Company Not found");
        }else{
            company.setCompanyIsActive(true);
            companyRepository.save(company);
            return "Added";
        }       
    }

    //data read from csv
    public void saveCompanyFromCsv(MultipartFile file) throws IOException {
        List<company> companys = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                company company = new company();
                company.setCompanyName(data[0]);
                company.setCompanyEmail(data[1]);
                company.setCompanyPhone(new BigInteger(data[2]));
                company.setCompanyPoc(data[3]);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                company.setCompanyStartDate(LocalDate.parse(data[4],formatter));
                company.setCompanyEndDate(LocalDate.parse(data[5],formatter));
                companys.add(company);
            }
        }
        companyRepository.saveAll(companys);
    }
    
}
