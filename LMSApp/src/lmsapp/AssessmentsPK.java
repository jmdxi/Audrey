/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lmsapp;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author JMD
 */
@Embeddable
public class AssessmentsPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "STUDENTNO")
    private int studentno;
    @Basic(optional = false)
    @Column(name = "MODULECODE")
    private String modulecode;
    @Basic(optional = false)
    @Column(name = "ASSESSMENTNO")
    private String assessmentno;

    public AssessmentsPK() {
    }

    public AssessmentsPK(int studentno, String modulecode, String assessmentno) {
        this.studentno = studentno;
        this.modulecode = modulecode;
        this.assessmentno = assessmentno;
    }

    public int getStudentno() {
        return studentno;
    }

    public void setStudentno(int studentno) {
        this.studentno = studentno;
    }

    public String getModulecode() {
        return modulecode;
    }

    public void setModulecode(String modulecode) {
        this.modulecode = modulecode;
    }

    public String getAssessmentno() {
        return assessmentno;
    }

    public void setAssessmentno(String assessmentno) {
        this.assessmentno = assessmentno;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) studentno;
        hash += (modulecode != null ? modulecode.hashCode() : 0);
        hash += (assessmentno != null ? assessmentno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AssessmentsPK)) {
            return false;
        }
        AssessmentsPK other = (AssessmentsPK) object;
        if (this.studentno != other.studentno) {
            return false;
        }
        if ((this.modulecode == null && other.modulecode != null) || (this.modulecode != null && !this.modulecode.equals(other.modulecode))) {
            return false;
        }
        if ((this.assessmentno == null && other.assessmentno != null) || (this.assessmentno != null && !this.assessmentno.equals(other.assessmentno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lmsapp.AssessmentsPK[ studentno=" + studentno + ", modulecode=" + modulecode + ", assessmentno=" + assessmentno + " ]";
    }
    
}
