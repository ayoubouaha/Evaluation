package ma.projet.service;

import ma.projet.classes.EmployeTache;
import ma.projet.classes.Tache;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Date;
import java.util.List;

public class TacheService implements IDao<Tache> {
    @Override
    public boolean create(Tache o) {
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
    public boolean update(Tache o) {
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
    public boolean delete(Tache o) {
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
    public Tache findById(Long id) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.get(Tache.class, id);
        }
    }

    @Override
    public List<Tache> findAll() {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery("from Tache", Tache.class).list();
        }
    }

    public List<Tache> findByPrixSuperieur(double minPrix) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createNamedQuery("Tache.findByPrixSup", Tache.class)
                    .setParameter("minPrix", minPrix)
                    .list();
        }
    }

    public List<EmployeTache> tachesRealiseesEntre(Date d1, Date d2) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery(
                    "from EmployeTache et where et.dateDebutReelle >= :d1 and et.dateFinReelle <= :d2",
                    EmployeTache.class)
                    .setParameter("d1", d1)
                    .setParameter("d2", d2)
                    .list();
        }
    }
}
