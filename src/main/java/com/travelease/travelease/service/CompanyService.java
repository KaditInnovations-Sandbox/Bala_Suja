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
            throw new ResourceNotFoundException("Company Name already exist");
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
    public String DeleteCompany(company companys) throws Exception{
        company company=companyRepository.findByComapnyName(companys.getCompanyName());
        if(company==null){
            throw new ResourceNotFoundException("Company Not found");
        }else{
            company.setCompanyIsActive(false);
            company.setCompanyDeletedTime(LocalDateTime.now());
            company.setCompanyLastUpdatedTime(LocalDateTime.now());
            company.setRemarks(companys.getRemarks());
            companyRepository.save(company);
            return "Deleted";
        }       
        
    }

    //Bind Company
    public String BindCompany(company companys) throws Exception{
        company company=companyRepository.findByComapnyName(companys.getCompanyName());
        if(company==null){
            throw new ResourceNotFoundException("Company Not found");
        }else{
            company.setCompanyIsActive(true);
            company.setCompanyLastUpdatedTime(LocalDateTime.now());
            company.setRemarks(null);
            company.setCompanyDeletedTime(null);
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

    //check companyname
    public boolean CheckCompanyByName(String companyname){
        company company = companyRepository.findByComapnyName(companyname);
        if(company == null){
            return true; // If company name is not present then return true
        }else{
            throw new ResourceNotFoundException("company name already exist");
        }  
    }

    //check company by phone number
    public boolean CheckCompanyByPhone(BigInteger phone){
        company company = companyRepository.findByComapnyPhone(phone);
        if(company == null){
            return true; // If company phone is not present then return true
        }else{
            throw new ResourceNotFoundException("company Phone already exist");
        }  
    }
    
    //check company by email id
    public boolean CheckCompanyByEmail(String email){
        company company = companyRepository.findByComapnyEmail(email);
        if(company == null){
            return true; // If company Email is not present then return true
        }else{
            throw new ResourceNotFoundException("company Email already exist");
        }  
    }
}
