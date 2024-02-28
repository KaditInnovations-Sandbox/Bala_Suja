package com.travelease.travelease.model.adminmodel;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Roleassociation")
public class AdminRoleAssociation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "userRoleAssociationIdGenerator")
    @GenericGenerator(
            name = "userRoleAssociationIdGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "user_role_association_sequence"),
                    @Parameter(name = "initial_value", value = "234500"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    @Column(name = "userroleid")
    private Long userRoleId;

    @ManyToOne
    @JoinColumn(name = "userid", nullable = false)
    private Admin user;

    @ManyToOne
    @JoinColumn(name = "roleid", nullable = false)
    private Role role;
    
}
