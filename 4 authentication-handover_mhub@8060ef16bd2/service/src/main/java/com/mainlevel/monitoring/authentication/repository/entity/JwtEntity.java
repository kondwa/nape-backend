package com.mainlevel.monitoring.authentication.repository.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * JPA entity that represents a JWT for a logged in user.
 */
@Entity
@Table(name = "a_jwt")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = false)
@EqualsAndHashCode(callSuper = false)
public class JwtEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String SEQUENCE = "a_jwt_id_seq";

    @Id
    @GeneratedValue(generator = SEQUENCE, strategy = GenerationType.AUTO)
    @SequenceGenerator(name = SEQUENCE, sequenceName = SEQUENCE, allocationSize = 1)
    private Long id;

    @Column(length = 1000)
    private String jwt;

    @OneToOne
    @JoinColumn(name = "userEntity_id")
    private UserEntity userEntity;

}
