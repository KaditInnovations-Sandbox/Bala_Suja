package com.travelease.travelease.model.dynamodb;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.travelease.travelease.model.routemodel.route;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBTable(tableName = "RouteBasedStop")
public class RouteBasedStop {

    @EmbeddedId
    private PrimaryKey key;

    @DynamoDBAttribute(attributeName = "stopname")
    private String stopname;

    @DynamoDBAttribute(attributeName = "stopisactive")
    private boolean stopisactive;

    @DynamoDBHashKey(attributeName = "routeid")
    public String getRouteId(){
        return key.getRouteid();
    }

    @DynamoDBRangeKey(attributeName = "stopid")
    @DynamoDBAutoGeneratedKey
    public long getStopId(){
        return key.getStopid();
    }

    public void setRouteId(String route){
        if(key==null){
            key = new PrimaryKey();
        }
        key.setRouteid(route);
    }

    public void setStopId(Long stopId){
        if(key==null){
            key=new PrimaryKey();
        }
        key.setStopid(stopId);
    }

}
