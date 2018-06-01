/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lmsapp;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import lmsapp.exceptions.IllegalOrphanException;
import lmsapp.exceptions.NonexistentEntityException;
import lmsapp.exceptions.PreexistingEntityException;

/**
 *
 * @author JMD
 */
public class ModulesJpaController implements Serializable {

    public ModulesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Modules modules) throws PreexistingEntityException, Exception {
        if (modules.getAssessmentsCollection() == null) {
            modules.setAssessmentsCollection(new ArrayList<Assessments>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Assessments> attachedAssessmentsCollection = new ArrayList<Assessments>();
            for (Assessments assessmentsCollectionAssessmentsToAttach : modules.getAssessmentsCollection()) {
                assessmentsCollectionAssessmentsToAttach = em.getReference(assessmentsCollectionAssessmentsToAttach.getClass(), assessmentsCollectionAssessmentsToAttach.getAssessmentsPK());
                attachedAssessmentsCollection.add(assessmentsCollectionAssessmentsToAttach);
            }
            modules.setAssessmentsCollection(attachedAssessmentsCollection);
            em.persist(modules);
            for (Assessments assessmentsCollectionAssessments : modules.getAssessmentsCollection()) {
                Modules oldModulesOfAssessmentsCollectionAssessments = assessmentsCollectionAssessments.getModules();
                assessmentsCollectionAssessments.setModules(modules);
                assessmentsCollectionAssessments = em.merge(assessmentsCollectionAssessments);
                if (oldModulesOfAssessmentsCollectionAssessments != null) {
                    oldModulesOfAssessmentsCollectionAssessments.getAssessmentsCollection().remove(assessmentsCollectionAssessments);
                    oldModulesOfAssessmentsCollectionAssessments = em.merge(oldModulesOfAssessmentsCollectionAssessments);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findModules(modules.getCode()) != null) {
                throw new PreexistingEntityException("Modules " + modules + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Modules modules) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Modules persistentModules = em.find(Modules.class, modules.getCode());
            Collection<Assessments> assessmentsCollectionOld = persistentModules.getAssessmentsCollection();
            Collection<Assessments> assessmentsCollectionNew = modules.getAssessmentsCollection();
            List<String> illegalOrphanMessages = null;
            for (Assessments assessmentsCollectionOldAssessments : assessmentsCollectionOld) {
                if (!assessmentsCollectionNew.contains(assessmentsCollectionOldAssessments)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Assessments " + assessmentsCollectionOldAssessments + " since its modules field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Assessments> attachedAssessmentsCollectionNew = new ArrayList<Assessments>();
            for (Assessments assessmentsCollectionNewAssessmentsToAttach : assessmentsCollectionNew) {
                assessmentsCollectionNewAssessmentsToAttach = em.getReference(assessmentsCollectionNewAssessmentsToAttach.getClass(), assessmentsCollectionNewAssessmentsToAttach.getAssessmentsPK());
                attachedAssessmentsCollectionNew.add(assessmentsCollectionNewAssessmentsToAttach);
            }
            assessmentsCollectionNew = attachedAssessmentsCollectionNew;
            modules.setAssessmentsCollection(assessmentsCollectionNew);
            modules = em.merge(modules);
            for (Assessments assessmentsCollectionNewAssessments : assessmentsCollectionNew) {
                if (!assessmentsCollectionOld.contains(assessmentsCollectionNewAssessments)) {
                    Modules oldModulesOfAssessmentsCollectionNewAssessments = assessmentsCollectionNewAssessments.getModules();
                    assessmentsCollectionNewAssessments.setModules(modules);
                    assessmentsCollectionNewAssessments = em.merge(assessmentsCollectionNewAssessments);
                    if (oldModulesOfAssessmentsCollectionNewAssessments != null && !oldModulesOfAssessmentsCollectionNewAssessments.equals(modules)) {
                        oldModulesOfAssessmentsCollectionNewAssessments.getAssessmentsCollection().remove(assessmentsCollectionNewAssessments);
                        oldModulesOfAssessmentsCollectionNewAssessments = em.merge(oldModulesOfAssessmentsCollectionNewAssessments);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = modules.getCode();
                if (findModules(id) == null) {
                    throw new NonexistentEntityException("The modules with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Modules modules;
            try {
                modules = em.getReference(Modules.class, id);
                modules.getCode();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The modules with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Assessments> assessmentsCollectionOrphanCheck = modules.getAssessmentsCollection();
            for (Assessments assessmentsCollectionOrphanCheckAssessments : assessmentsCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Modules (" + modules + ") cannot be destroyed since the Assessments " + assessmentsCollectionOrphanCheckAssessments + " in its assessmentsCollection field has a non-nullable modules field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(modules);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Modules> findModulesEntities() {
        return findModulesEntities(true, -1, -1);
    }

    public List<Modules> findModulesEntities(int maxResults, int firstResult) {
        return findModulesEntities(false, maxResults, firstResult);
    }

    private List<Modules> findModulesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Modules.class));
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

    public Modules findModules(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Modules.class, id);
        } finally {
            em.close();
        }
    }

    public int getModulesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Modules> rt = cq.from(Modules.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
