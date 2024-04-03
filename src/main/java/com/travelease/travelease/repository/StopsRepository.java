package com.travelease.travelease.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.travelease.travelease.model.routemodel.stops;

public interface StopsRepository extends JpaRepository<stops,Long>{
    
    @Query(nativeQuery = true, value ="SELECT stop_name from stops e WHERE e.route_id=:RouteId")
    List<String> findStopByRouteId(@Param("RouteId")Long RouteId);

    @Query(nativeQuery = true, value ="SELECT * from stops e WHERE e.route_id=:id")
    List<stops> checkByRouteId(@Param("id")Long id);

    @Query(nativeQuery = true, value = "SELECT * from stops e WHERE e.stop_name =:stopname")
    stops findByStopname(@Param("stopname")String stopname);
    
}
