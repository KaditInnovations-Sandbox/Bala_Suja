package com.travelease.travelease.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.travelease.travelease.model.routemodel.route;
import com.travelease.travelease.service.RouteService;
import org.springframework.web.bind.annotation.GetMapping;


@CrossOrigin(origins = "${crossorigin}")
@RestController
@RequestMapping("/travelease/")
public class RouteController {

    
    @Value("${crossorigin}")
	private String crossorigin;

    @Autowired
    private RouteService routeService;

    //create Route
    @PostMapping("{companyname}/Route")
    public ResponseEntity<String> createVehicle(@RequestHeader String companyname,@RequestBody Map<String,Object> CompanyRoute)throws Exception{
        return ResponseEntity.status(HttpStatus.OK).body(routeService.createRoute(companyname,CompanyRoute));
    }

    //Get Route
    @GetMapping("/Route")
    public List<Map<String, Object>> getRoute() {
        return routeService.getRoute();
    }
    

    //get all active Route details
    @GetMapping("/ActiveRoute")
    public List<Map<route, List<String>>> getAllActiveRoute(){
        return routeService.getActiveRoute();
    }

    //get all inactive Route details 
    @GetMapping("/InactiveRoute")
    public List<Map<route, List<String>>> getAllInactiveRoute(){
        return routeService.getInactiveRoute();
    }

    //get all Route details based on company
    @GetMapping("/CompanyBasedRoute")
    public List<Map<String, Object>> getRouteBasedCompany(@RequestParam String companyname){
        return routeService.getRouteBasedCompany(companyname);
    }

    //get all Route details based on company
    @GetMapping("/CompanyBasedActiveRoute")
    public List<Map<String, Object>> getActiveRouteBasedCompany(@RequestParam String companyname){
        return routeService.getActiveRouteBasedCompany(companyname);
    }

    //get all Route details based on company
    @GetMapping("/CompanyBasedInactiveRoute")
    public List<Map<String, Object>> getInactiveRouteBasedCompany(@RequestParam String companyname){
        return routeService.getInactiveRouteBasedCompany(companyname);
    }
    

     //bind Route
    @PutMapping("/BindRoute")
	public ResponseEntity<String> BindRoute(@RequestBody route Route) throws Exception{
		return ResponseEntity.status(HttpStatus.OK).body(routeService.BindRoute(Route));
	}

     //delete Route
     @DeleteMapping("/Route")
     public ResponseEntity<String> DeleteRoute(@RequestBody route Route) throws Exception{
         return ResponseEntity.status(HttpStatus.OK).body(routeService.DeleteRoute(Route));
     }    

     //get all Route Id
    @GetMapping("/AllRouteId")
    public List<String> getAllRouteId(){
        return routeService.getAllRouteId();
    }

    @PostMapping("/CheckRouteId")
    public ResponseEntity<Boolean> CheckRouteId(@RequestParam("RouteId")String RouteId)throws Exception{
        return ResponseEntity.status(HttpStatus.OK).body(routeService.CheckRouteId(RouteId));
    }


}
