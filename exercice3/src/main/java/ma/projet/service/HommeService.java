package ma.projet.service;

import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Date;
import java.util.List;

public class HommeService implements IDao<Homme> {
    @Override
    public boolean create(Homme o) {
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
    public boolean update(Homme o) {
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
    public boolean delete(Homme o) {
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
    public Homme findById(Long id) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.get(Homme.class, id);
        }
    }

    @Override
    public List<Homme> findAll() {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery("from Homme", Homme.class).list();
        }
    }

    public List<Femme> epousesEntre(Long hommeId, Date d1, Date d2) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery(
                    "select distinct m.femme from Mariage m where m.homme.id = :hid and m.dateDebut <= :d2 and (m.dateFin is null or m.dateFin >= :d1)",
                    Femme.class)
                    .setParameter("hid", hommeId)
                    .setParameter("d1", d1)
                    .setParameter("d2", d2)
                    .list();
        }
    }

    public List<Mariage> mariagesDe(Long hommeId) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery("from Mariage m where m.homme.id = :hid order by m.dateDebut", Mariage.class)
                    .setParameter("hid", hommeId)
                    .list();
        }
    }
}
