// This Bean has a basic Primary Key (not composite)

package com.coupon.user.bean.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Persistent class for entity stored in table "user_role"
 *
 * @author MyDeziner.com
 */

@Entity
@Table(name = "user_role", uniqueConstraints = @UniqueConstraint(columnNames = { "role_id", "user_id" }))
public class UserRoleEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    // ----------------------------------------------------------------------
    // ENTITY PRIMARY KEY (BASED ON A SINGLE FIELD)
    // ----------------------------------------------------------------------
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    // ----------------------------------------------------------------------
    // ENTITY DATA FIELDS
    // ----------------------------------------------------------------------
    // "userId" (column "user_id") is not defined by itself because used as FK
    // in a link
    // "firmId" (column "firm_id") is not defined by itself because used as FK
    // in a link
    // "roleId" (column "role_id") is not defined by itself because used as FK
    // in a link

    // ----------------------------------------------------------------------
    // ENTITY LINKS (RELATIONSHIP)
    // ----------------------------------------------------------------------
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private RoleEntity role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    // ----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    // ----------------------------------------------------------------------
    public UserRoleEntity() {
        super();
    }

    public Integer getId() {
        return this.id;
    }

    // ----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    // ----------------------------------------------------------------------
    public void setId(final Integer id) {
        this.id = id;
    }

    // ----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    // ----------------------------------------------------------------------

    public RoleEntity getRole() {
        return this.role;
    }

    // ----------------------------------------------------------------------
    // GETTERS & SETTERS FOR LINKS
    // ----------------------------------------------------------------------
    public void setRole(final RoleEntity role) {
        this.role = role;
    }

    public UserEntity getUser() {
        return this.user;
    }

    public void setUser(final UserEntity user) {
        this.user = user;
    }

    // ----------------------------------------------------------------------
    // toString METHOD
    // ----------------------------------------------------------------------
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append(id);
        sb.append("]:");
        return sb.toString();
    }

}
