package lmsapp;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import lmsapp.Assessments;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-06-01T04:55:23")
@StaticMetamodel(Modules.class)
public class Modules_ { 

    public static volatile SingularAttribute<Modules, String> code;
    public static volatile SingularAttribute<Modules, String> name;
    public static volatile CollectionAttribute<Modules, Assessments> assessmentsCollection;

}