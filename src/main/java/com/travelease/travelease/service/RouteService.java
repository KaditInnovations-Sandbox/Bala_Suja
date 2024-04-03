package com.travelease.travelease.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.text.Style;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travelease.travelease.exception.ResourceNotFoundException;
import com.travelease.travelease.model.companymodel.company;
import com.travelease.travelease.model.routemodel.route;
import com.travelease.travelease.model.routemodel.stops;
import com.travelease.travelease.repository.CompanyRepository;
import com.travelease.travelease.repository.RouteRepository;
import com.travelease.travelease.repository.StopsRepository;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Service
public class RouteService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private StopsRepository stopsRepository;

    //Create Route
    public String createRoute(Map<String, Object> companyRoute) throws Exception {
        company company = companyRepository.findByComapnyName((String)companyRoute.get("company_name"));
        if(company.getCompanyIsActive()){
            route route=new route();

            // need to add routeId based on pickukp and drop location 
            if(((String) companyRoute.get("route_pickup")).equals(company.getCompanyName())){
                route.setRouteId(generateRouteIdOut((String)companyRoute.get("route_pickup"),(String)companyRoute.get("route_drop")));
            }else if(((String) companyRoute.get("route_drop")).equals(company.getCompanyName())){
                route.setRouteId(generateRouteIdIn((String)companyRoute.get("route_pickup"),(String)companyRoute.get("route_drop")));
            }else{
                throw new ResourceNotFoundException("You Must have Pickup or Drop point as a company name");
            }        
            route.setCompanyId(company);
            route.setRoutePickup((String)companyRoute.get("route_pickup"));
            route.setRouteDrop((String)companyRoute.get("route_drop"));
            route.setRouteStartTime(LocalTime.parse((CharSequence) companyRoute.get("start_time"))); //Time Format : 15:45:30
            route.setRouteEndTime(LocalTime.parse((CharSequence) companyRoute.get("end_time")));   //Time Format : 15:45:30
            routeRepository.save(route);
            @SuppressWarnings("unchecked")
            List<String> stopsList = (List<String>) companyRoute.get("Stops");
            for(String s: stopsList){
                stops stop=new stops();
                stop.setStopName(s);
                stop.setRouteId(route);
                stopsRepository.save(stop);
            }
            return "Created";

        }else{
            throw new ResourceNotFoundException("Company Not Found");
        }
    }


    //Update Route
    public String UpdateRoute(Map<String, Object> companyRoute) throws Exception{
        company company = companyRepository.findByComapnyName((String)companyRoute.get("companyName"));
        route route = routeRepository.checkById((Long)companyRoute.get("id"));
        if(company.getCompanyIsActive() && route.getRouteIsActive()){
            route.setRoutePickup((String)companyRoute.get("route_pickup"));
            route.setRouteDrop((String)companyRoute.get("route_drop"));
            route.setRouteStartTime(LocalTime.parse((CharSequence) companyRoute.get("start_time"))); //Time Format : 15:45:30
            route.setRouteEndTime(LocalTime.parse((CharSequence) companyRoute.get("end_time")));   //Time Format : 15:45:30
            routeRepository.save(route);
            List<String> stopsList = (List<String>) companyRoute.get("Stops");
            for(String s : stopsList){
                stops stop = stopsRepository.findByStopname(s);
                if(stop.getRouteId() != route){
                    
                }
                 
            }


                  // need to add routeId based on pickukp and drop location          


             
                
                // for(String s: stopsList){
                //     stops stop=new stops();
                //     stop.setStopName(s);
                //     stop.setRouteId(route);
                //     stopsRepository.save(stop);
                // }

            return null;

        }else{
            throw new ResourceNotFoundException("Company Not Found");
        }
    }
 

    //Get Route
    public List<Map<route, List<String>>> getRoute(){

        List<route> routes=routeRepository.findAll();
        List<Map<route, List<String>>> listOfMappedData = new ArrayList<>();
        for(route r:routes){
            Map<route,List<String>> routeWithStop = new HashMap<>();
            List<String> stops=stopsRepository.findStopByRouteId(r.getId());
            routeWithStop.put(r,stops);
            listOfMappedData.add(routeWithStop);
        }
        return listOfMappedData;
    }

     //Get Active Route
     public List<Map<route, List<String>>> getActiveRoute(){

        List<route> routes=routeRepository.findByAccessTrue();
        List<Map<route, List<String>>> listOfMappedData = new ArrayList<>();
        for(route r:routes){
            Map<route,List<String>> routeWithStop = new HashMap<>();
            List<String> stops=stopsRepository.findStopByRouteId(r.getId());
            routeWithStop.put(r,stops);
            listOfMappedData.add(routeWithStop);
        }
        return listOfMappedData;
    }
    

     //Get InActive Route
     public List<Map<route, List<String>>> getInactiveRoute(){

        List<route> routes=routeRepository.findByAccessFalse();
        List<Map<route, List<String>>> listOfMappedData = new ArrayList<>();
        for(route r:routes){
            Map<route,List<String>> routeWithStop = new HashMap<>();
            List<String> stops=stopsRepository.findStopByRouteId(r.getId());
            routeWithStop.put(r,stops);
            listOfMappedData.add(routeWithStop);
        }
        return listOfMappedData;
    }

    //Remove Route
    public String DeleteRoute(route routes){
        route route=routeRepository.checkById(routes.getId());
        if(route.getRouteIsActive()){
            List<stops> stops=stopsRepository.checkByRouteId(route.getId());
            for(stops s: stops){
                s.setStopIsActive(false);
                stopsRepository.save(s);
            }
            route.setRouteIsActive(false);
            route.setRemarks(routes.getRemarks());
            routeRepository.save(route);
            return "Removed";
        }else{
            throw new ResourceNotFoundException("Route was Inactive");
        }
    }


    //Bind Route
    public String BindRoute(route Route){
        route route=routeRepository.checkById(Route.getId());
        if(!route.getRouteIsActive()){
            List<stops> stops=stopsRepository.checkByRouteId(route.getId());
            for(stops s: stops){
                s.setStopIsActive(true);
                stopsRepository.save(s);
            }
            route.setRouteIsActive(true);
            routeRepository.save(route);
            return "Removed";
        }else{
            throw new ResourceNotFoundException("Route was Active");
        }
    }

    // Generate RouteId based on pickup (Route) to drop (Company)
    public String generateRouteIdIn(String RoutePickup, String RouteDrop) {
        String prefix = (RoutePickup.length() >= 2 ? RoutePickup.substring(0, 2) : RoutePickup)
                      + (RouteDrop.length() >= 2 ? RouteDrop.substring(0, 2) : RouteDrop);
        int suffix = 1001;  // starting value
        String routeId = prefix + String.format("%04d", suffix);
        return routeId;
    }

 
    
    // Generate RouteId based on drop (Company) to pickup (Route)
    public String generateRouteIdOut(String RoutePickup, String RouteDrop) {
        String prefix = (RouteDrop.length() >= 2 ? RouteDrop.substring(0, 2) : RouteDrop)
                      + (RoutePickup.length() >= 2 ? RoutePickup.substring(0, 2) : RoutePickup);
        int suffix = 2001;  // starting value
        String routeId = prefix + String.format("%04d", suffix);
        return routeId;
    }





    
}
