/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcInterviewform.TCInterviewForm.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author SIF
 */
@Entity
@Table(name = "city")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "City.findAll", query = "SELECT c FROM City c")
    , @NamedQuery(name = "City.findById", query = "SELECT c FROM City c WHERE c.id = :id")
    , @NamedQuery(name = "City.findByCreatedAt", query = "SELECT c FROM City c WHERE c.createdAt = :createdAt")
    , @NamedQuery(name = "City.findByDescripton", query = "SELECT c FROM City c WHERE c.descripton = :descripton")
    , @NamedQuery(name = "City.findByEnabled", query = "SELECT c FROM City c WHERE c.enabled = :enabled")
    , @NamedQuery(name = "City.findByName", query = "SELECT c FROM City c WHERE c.name = :name")
    , @NamedQuery(name = "City.findByStateId", query = "SELECT c FROM City c WHERE c.stateId = :stateId")
    , @NamedQuery(name = "City.findByUpdatedAt", query = "SELECT c FROM City c WHERE c.updatedAt = :updatedAt")
    , @NamedQuery(name = "City.findByZone", query = "SELECT c FROM City c WHERE c.zone = :zone")})
public class City implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "descripton")
    private String descripton;
    @Column(name = "enabled")
    private Integer enabled;
    @Column(name = "name")
    private String name;
    @Column(name = "state_id")
    private Integer stateId;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @Column(name = "zone")
    private String zone;

    public City() {
    }

    public City(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getDescripton() {
        return descripton;
    }

    public void setDescripton(String descripton) {
        this.descripton = descripton;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof City)) {
            return false;
        }
        City other = (City) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tcInterviewform.TCInterviewForm.model.City[ id=" + id + " ]";
    }
    
}
