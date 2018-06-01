/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lmsapp;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author JMD
 */
@Entity
@Table(name = "MODULES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Modules.findAll", query = "SELECT m FROM Modules m")
    , @NamedQuery(name = "Modules.findByCode", query = "SELECT m FROM Modules m WHERE m.code = :code")
    , @NamedQuery(name = "Modules.findByName", query = "SELECT m FROM Modules m WHERE m.name = :name")})
public class Modules implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CODE")
    private String code;
    @Basic(optional = false)
    @Column(name = "NAME")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "modules")
    private Collection<Assessments> assessmentsCollection;

    public Modules() {
    }

    public Modules(String code) {
        this.code = code;
    }

    public Modules(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public Collection<Assessments> getAssessmentsCollection() {
        return assessmentsCollection;
    }

    public void setAssessmentsCollection(Collection<Assessments> assessmentsCollection) {
        this.assessmentsCollection = assessmentsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (code != null ? code.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Modules)) {
            return false;
        }
        Modules other = (Modules) object;
        if ((this.code == null && other.code != null) || (this.code != null && !this.code.equals(other.code))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lmsapp.Modules[ code=" + code + " ]";
    }
    
}
