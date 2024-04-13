package com.travelease.travelease.repository;

import org.socialsignin.spring.data.dynamodb.repository.DynamoDBCrudRepository;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;

import com.travelease.travelease.model.dynamodb.PrimaryKey;
import com.travelease.travelease.model.dynamodb.RouteBasedStop;

import java.util.List;


@EnableScan
@EnableDynamoDBRepositories
public interface RouteStopRepository extends DynamoDBCrudRepository<RouteBasedStop, PrimaryKey>{

    // List<RouteBasedStop> findByRouteId(String routeId);
}
