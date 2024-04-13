package com.travelease.travelease.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.travelease.travelease.model.dynamodb.RouteBasedStop;
import com.travelease.travelease.repository.RouteStopRepository;

@CrossOrigin(origins = "${crossorigin}")
@RestController
@RequestMapping("/travelease/")
public class DynamoDBController {
    

    @Autowired
    private RouteStopRepository routeStopRepository;

    @PostMapping("/RouteBasedStop")
	public ResponseEntity<RouteBasedStop> addStops(@RequestBody RouteBasedStop routeBasedStop) throws Exception {
        System.out.println(routeBasedStop);
		return ResponseEntity.status(HttpStatus.OK).body(routeStopRepository.save(routeBasedStop));
	}


    @GetMapping("/RouteBasedStop")
	public ResponseEntity<Object> getAdminById() {
		return ResponseEntity.status(HttpStatus.OK).body(routeStopRepository.findAll());
	}

    // @GetMapping("/person/{id}")
    // public ResponseEntity getPerson(@PathVariable("id") String id,
    //                                 @RequestParam(value = "creationDate", required = false)
    //                                 @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate creationDate) {

    //     if (creationDate == null) {
    //         List<Person> people = personRepository.findById(id);
    //         return ResponseEntity.ok(people);
    //     }

    //     return getByPrimaryKey(id, creationDate);
    // }
}
