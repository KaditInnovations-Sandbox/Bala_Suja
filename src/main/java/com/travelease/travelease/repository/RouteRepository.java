package com.travelease.travelease.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.travelease.travelease.model.routemodel.route;

public interface RouteRepository extends JpaRepository<route,Long>{

    @Query(nativeQuery = true, value ="SELECT * from route e WHERE e.id=:id")
    route checkById(@Param("id")Long id);

    
    @Query(nativeQuery = true, value = "SELECT * FROM route e WHERE e.route_is_active = true")
    List<route> findByAccessTrue();

    @Query(nativeQuery = true, value = "SELECT * FROM route e WHERE e.route_is_active = false")
    List<route> findByAccessFalse();

    @Query(nativeQuery = true, value ="SELECT * from route e WHERE e.route_id=:RouteId")
    route findByRouteId(@Param("RouteId")String RouteId);

} 
   
