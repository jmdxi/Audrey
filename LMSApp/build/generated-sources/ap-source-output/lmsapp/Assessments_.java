package lmsapp;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import lmsapp.AssessmentsPK;
import lmsapp.Modules;
import lmsapp.Students;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-06-01T04:55:23")
@StaticMetamodel(Assessments.class)
public class Assessments_ { 

    public static volatile SingularAttribute<Assessments, Integer> totalmarks;
    public static volatile SingularAttribute<Assessments, Students> students;
    public static volatile SingularAttribute<Assessments, Integer> marks;
    public static volatile SingularAttribute<Assessments, Integer> weighting;
    public static volatile SingularAttribute<Assessments, AssessmentsPK> assessmentsPK;
    public static volatile SingularAttribute<Assessments, Modules> modules;

}