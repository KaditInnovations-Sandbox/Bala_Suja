package com.travelease.travelease.model.tokenmodel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sessiontoken")
public class Token {
    
    @Id
    @Column(name = "email_id")
    private String emailId;

    @Column(name= "Token_id")
    @NotNull
    private String Token_id;
}
