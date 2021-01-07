package com.mainlevel.monitoring.authentication.repository.entity;

import static javax.persistence.GenerationType.AUTO;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * JPA entity that represents a user.
 */
@Entity
@Table(name = "a_user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = false)
@EqualsAndHashCode(callSuper = false)
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String SEQUENCE = "a_user_id_seq";

    @Id
    @GeneratedValue(generator = SEQUENCE, strategy = AUTO)
    @SequenceGenerator(name = SEQUENCE, sequenceName = SEQUENCE, allocationSize = 1, initialValue = 51)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    private String password;

    private String salt;

    private String firstname;

    private String lastname;

    private String userType;

    private String authentication;

    private Timestamp expiryDate;

    private String theme;

    private Boolean active;

    @OrderBy("name")
    @ManyToMany //@formatter:off
	@JoinTable(
		name = "a_user_role",
		joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
		inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
	) //@formatter:on
    private List<RoleEntity> roles;
}
