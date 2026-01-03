package ma.projet.service;

import ma.projet.classes.EmployeTache;
import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ProjetService implements IDao<Projet> {
    @Override
    public boolean create(Projet o) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            tx = s.beginTransaction();
            s.save(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            return false;
        }
    }

    @Override
    public boolean update(Projet o) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            tx = s.beginTransaction();
            s.update(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            return false;
        }
    }

    @Override
    public boolean delete(Projet o) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            tx = s.beginTransaction();
            s.delete(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            return false;
        }
    }

    @Override
    public Projet findById(Long id) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.get(Projet.class, id);
        }
    }

    @Override
    public List<Projet> findAll() {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery("from Projet", Projet.class).list();
        }
    }

    public List<Tache> tachesPlanifiees(Long projetId) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery("from Tache t where t.projet.id = :pid", Tache.class)
                    .setParameter("pid", projetId)
                    .list();
        }
    }

    public List<EmployeTache> tachesRealiseesAvecDates(Long projetId) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery("from EmployeTache et where et.tache.projet.id = :pid", EmployeTache.class)
                    .setParameter("pid", projetId)
                    .list();
        }
    }
}
