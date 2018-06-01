/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lmsapp;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import lmsapp.exceptions.NonexistentEntityException;
import lmsapp.exceptions.PreexistingEntityException;

/**
 *
 * @author JMD
 */
public class AssessmentsJpaController implements Serializable {

    public AssessmentsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Assessments assessments) throws PreexistingEntityException, Exception {
        if (assessments.getAssessmentsPK() == null) {
            assessments.setAssessmentsPK(new AssessmentsPK());
        }
        assessments.getAssessmentsPK().setModulecode(assessments.getModules().getCode());
        assessments.getAssessmentsPK().setStudentno(assessments.getStudents().getStudentno());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Modules modules = assessments.getModules();
            if (modules != null) {
                modules = em.getReference(modules.getClass(), modules.getCode());
                assessments.setModules(modules);
            }
            em.persist(assessments);
            if (modules != null) {
                modules.getAssessmentsCollection().add(assessments);
                modules = em.merge(modules);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAssessments(assessments.getAssessmentsPK()) != null) {
                throw new PreexistingEntityException("Assessments " + assessments + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Assessments assessments) throws NonexistentEntityException, Exception {
        assessments.getAssessmentsPK().setModulecode(assessments.getModules().getCode());
        assessments.getAssessmentsPK().setStudentno(assessments.getStudents().getStudentno());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Assessments persistentAssessments = em.find(Assessments.class, assessments.getAssessmentsPK());
            Modules modulesOld = persistentAssessments.getModules();
            Modules modulesNew = assessments.getModules();
            if (modulesNew != null) {
                modulesNew = em.getReference(modulesNew.getClass(), modulesNew.getCode());
                assessments.setModules(modulesNew);
            }
            assessments = em.merge(assessments);
            if (modulesOld != null && !modulesOld.equals(modulesNew)) {
                modulesOld.getAssessmentsCollection().remove(assessments);
                modulesOld = em.merge(modulesOld);
            }
            if (modulesNew != null && !modulesNew.equals(modulesOld)) {
                modulesNew.getAssessmentsCollection().add(assessments);
                modulesNew = em.merge(modulesNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                AssessmentsPK id = assessments.getAssessmentsPK();
                if (findAssessments(id) == null) {
                    throw new NonexistentEntityException("The assessments with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(AssessmentsPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Assessments assessments;
            try {
                assessments = em.getReference(Assessments.class, id);
                assessments.getAssessmentsPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The assessments with id " + id + " no longer exists.", enfe);
            }
            Modules modules = assessments.getModules();
            if (modules != null) {
                modules.getAssessmentsCollection().remove(assessments);
                modules = em.merge(modules);
            }
            em.remove(assessments);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Assessments> findAssessmentsEntities() {
        return findAssessmentsEntities(true, -1, -1);
    }

    public List<Assessments> findAssessmentsEntities(int maxResults, int firstResult) {
        return findAssessmentsEntities(false, maxResults, firstResult);
    }

    private List<Assessments> findAssessmentsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Assessments.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Assessments findAssessments(AssessmentsPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Assessments.class, id);
        } finally {
            em.close();
        }
    }

    public int getAssessmentsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Assessments> rt = cq.from(Assessments.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
