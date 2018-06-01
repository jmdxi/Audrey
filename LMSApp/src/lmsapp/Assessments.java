/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lmsapp;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author JMD
 */
@Entity
@Table(name = "ASSESSMENTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Assessments.findAll", query = "SELECT a FROM Assessments a")
    , @NamedQuery(name = "Assessments.findByStudentno", query = "SELECT a FROM Assessments a WHERE a.assessmentsPK.studentno = :studentno")
    , @NamedQuery(name = "Assessments.findByModulecode", query = "SELECT a FROM Assessments a WHERE a.assessmentsPK.modulecode = :modulecode")
    , @NamedQuery(name = "Assessments.findByAssessmentno", query = "SELECT a FROM Assessments a WHERE a.assessmentsPK.assessmentno = :assessmentno")
    , @NamedQuery(name = "Assessments.findByWeighting", query = "SELECT a FROM Assessments a WHERE a.weighting = :weighting")
    , @NamedQuery(name = "Assessments.findByTotalmarks", query = "SELECT a FROM Assessments a WHERE a.totalmarks = :totalmarks")
    , @NamedQuery(name = "Assessments.findByMarks", query = "SELECT a FROM Assessments a WHERE a.marks = :marks")})
public class Assessments implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AssessmentsPK assessmentsPK;
    @Basic(optional = false)
    @Column(name = "WEIGHTING")
    private int weighting;
    @Basic(optional = false)
    @Column(name = "TOTALMARKS")
    private int totalmarks;
    @Basic(optional = false)
    @Column(name = "MARKS")
    private int marks;
    @JoinColumn(name = "MODULECODE", referencedColumnName = "CODE", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Modules modules;
    @JoinColumn(name = "STUDENTNO", referencedColumnName = "STUDENTNO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Students students;

    public Assessments() {
    }

    public Assessments(AssessmentsPK assessmentsPK) {
        this.assessmentsPK = assessmentsPK;
    }

    public Assessments(AssessmentsPK assessmentsPK, int weighting, int totalmarks, int marks) {
        this.assessmentsPK = assessmentsPK;
        this.weighting = weighting;
        this.totalmarks = totalmarks;
        this.marks = marks;
    }

    public Assessments(int studentno, String modulecode, String assessmentno) {
        this.assessmentsPK = new AssessmentsPK(studentno, modulecode, assessmentno);
    }

    public AssessmentsPK getAssessmentsPK() {
        return assessmentsPK;
    }

    public void setAssessmentsPK(AssessmentsPK assessmentsPK) {
        this.assessmentsPK = assessmentsPK;
    }

    public int getWeighting() {
        return weighting;
    }

    public void setWeighting(int weighting) {
        this.weighting = weighting;
    }

    public int getTotalmarks() {
        return totalmarks;
    }

    public void setTotalmarks(int totalmarks) {
        this.totalmarks = totalmarks;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    public Modules getModules() {
        return modules;
    }

    public void setModules(Modules modules) {
        this.modules = modules;
    }

    public Students getStudents() {
        return students;
    }

    public void setStudents(Students students) {
        this.students = students;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (assessmentsPK != null ? assessmentsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Assessments)) {
            return false;
        }
        Assessments other = (Assessments) object;
        if ((this.assessmentsPK == null && other.assessmentsPK != null) || (this.assessmentsPK != null && !this.assessmentsPK.equals(other.assessmentsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lmsapp.Assessments[ assessmentsPK=" + assessmentsPK + " ]";
    }
    
}
