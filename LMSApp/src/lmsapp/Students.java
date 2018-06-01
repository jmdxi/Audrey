/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lmsapp;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author JMD
 */
@Entity
@Table(name = "STUDENTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Students.findAll", query = "SELECT s FROM Students s")
    , @NamedQuery(name = "Students.findByStudentno", query = "SELECT s FROM Students s WHERE s.studentno = :studentno")
    , @NamedQuery(name = "Students.findBySurname", query = "SELECT s FROM Students s WHERE s.surname = :surname")
    , @NamedQuery(name = "Students.findByFirstname", query = "SELECT s FROM Students s WHERE s.firstname = :firstname")
    , @NamedQuery(name = "Students.findByIdnumber", query = "SELECT s FROM Students s WHERE s.idnumber = :idnumber")})
public class Students implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "STUDENTNO")
    private Integer studentno;
    @Basic(optional = false)
    @Column(name = "SURNAME")
    private String surname;
    @Basic(optional = false)
    @Column(name = "FIRSTNAME")
    private String firstname;
    @Column(name = "IDNUMBER")
    private String idnumber;

    public Students() {
    }

    public Students(Integer studentno) {
        this.studentno = studentno;
    }

    public Students(Integer studentno, String surname, String firstname) {
        this.studentno = studentno;
        this.surname = surname;
        this.firstname = firstname;
    }

    public Integer getStudentno() {
        return studentno;
    }

    public void setStudentno(Integer studentno) {
        this.studentno = studentno;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (studentno != null ? studentno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Students)) {
            return false;
        }
        Students other = (Students) object;
        if ((this.studentno == null && other.studentno != null) || (this.studentno != null && !this.studentno.equals(other.studentno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lmsapp.Students[ studentno=" + studentno + " ]";
    }
    
}
