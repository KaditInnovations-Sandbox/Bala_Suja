package com.travelease.travelease.model.adminmodel;

import java.math.BigInteger;
import java.time.LocalDateTime;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Admin")
public class Admin {
    
    public interface PublicView {}
    public interface PrivateView extends PublicView {}    
        
    @Id
    @JsonView(PublicView.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "adminIdGenerator")
    @GenericGenerator(
            name = "adminIdGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "admin_sequence"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    @Column(name = "AdminId")
    private Long AdminId;
    @JsonView(PublicView.class)
    @Column(name = "AdminName")
    private String AdminName;
    @JsonView(PublicView.class)
    @Column(name = "AdminEmail", unique = true, nullable = false)
    private String AdminEmail;
    @JsonView(PublicView.class)
    @Column(name = "AdminPhone", unique = true, nullable = false) 
    private BigInteger AdminPhone;
    @JsonView(PrivateView.class)
    @Column(name = "AdminPassword")
    private String AdminPassword;
    @JsonView(PublicView.class)
    @Column(name = "AdminCreatedAt")
    private LocalDateTime AdminCreatedAt = LocalDateTime.now();
    @JsonView(PublicView.class)
    @Column(name = "AdminLastLogin")
    private LocalDateTime AdminLastLogin;
    @JsonView(PublicView.class)
    @Column(name = "AdminIsActive")
    private Boolean AdminIsActive = true;
    @JsonView(PublicView.class)
    @Column(name = "AdminLastUpdatedTime")
    private LocalDateTime AdminLastUpdatedTime;
    @JsonView(PublicView.class)
    @Column(name = "AdminDeletedTime")
    private LocalDateTime AdminDeletedTime;
    @JsonView(PublicView.class)
    @Column(name = "AdminRoleType", nullable = false)
    private String AdminRoleType;
    @JsonView(PublicView.class)
    @Column(name = "Remarks")
    private String Remarks;
    

}
