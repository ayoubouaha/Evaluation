package ma.projet.service;

import ma.projet.classes.Employe;
import ma.projet.classes.EmployeTache;
import ma.projet.classes.Projet;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class EmployeService implements IDao<Employe> {
    @Override
    public boolean create(Employe o) {
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
    public boolean update(Employe o) {
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
    public boolean delete(Employe o) {
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
    public Employe findById(Long id) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.get(Employe.class, id);
        }
    }

    @Override
    public List<Employe> findAll() {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery("from Employe", Employe.class).list();
        }
    }

    public List<EmployeTache> tachesRealiseesParEmploye(Long employeId) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery("from EmployeTache et where et.employe.id = :eid", EmployeTache.class)
                    .setParameter("eid", employeId)
                    .list();
        }
    }

    public List<Projet> projetsGeresParEmploye(Long employeId) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery("from Projet p where p.chefProjet.id = :eid", Projet.class)
                    .setParameter("eid", employeId)
                    .list();
        }
    }
}
