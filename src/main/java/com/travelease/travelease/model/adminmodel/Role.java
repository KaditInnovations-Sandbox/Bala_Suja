package com.travelease.travelease.model.adminmodel;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "role")
public class Role {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "RoleIdGenerator")
    @GenericGenerator(
            name = "RoleIdGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "role_sequence"),
                    @Parameter(name = "initial_value", value = "123400"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    @Column(name = "AdminRoleId")
    private Long AdminRoleId;

    @Column(name = "AdminRoleName")
    private String AdminRoleName;

    @Column(name = "AdminRoleDescription")
    private String AdminRoleDescription; 

}
