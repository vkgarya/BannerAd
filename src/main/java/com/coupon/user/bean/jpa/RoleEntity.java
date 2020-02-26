// This Bean has a basic Primary Key (not composite)

package com.coupon.user.bean.jpa;

import com.coupon.user.bean.Role;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Persistent class for entity stored in table "role"
 *
 * @author MyDeziner.com
 */

@Entity
@Table(name = "role", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
public class RoleEntity implements Serializable {

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
    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @Column(name = "display_name", nullable = false, length = 45)
    private String displayName;

    // ----------------------------------------------------------------------
    // ENTITY LINKS (RELATIONSHIP)
    // ----------------------------------------------------------------------
    @OneToMany(mappedBy = "role", targetEntity = UserRoleEntity.class)
    private List<UserRoleEntity> listOfUserRole;

    // ----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    // ----------------------------------------------------------------------
    public RoleEntity() {
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

    public String getName() {
        return this.name;
    }

    // ----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    // ----------------------------------------------------------------------
    // --- DATABASE MAPPING : name (VARCHAR)
    public void setName(final String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(final String displayName) {
        this.displayName = displayName;
    }

    public List<UserRoleEntity> getListOfUserRole() {
        return this.listOfUserRole;
    }

    // ----------------------------------------------------------------------
    // GETTERS & SETTERS FOR LINKS
    // ----------------------------------------------------------------------
    public void setListOfUserRole(final List<UserRoleEntity> listOfUserRole) {
        this.listOfUserRole = listOfUserRole;
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
        sb.append(name);
        return sb.toString();
    }

    public Role getRole() {
        Role role = new Role();
        role.setId(this.getId());
        role.setDisplayName(this.getDisplayName());
        role.setName(this.getName());
        return role;
    }
}
