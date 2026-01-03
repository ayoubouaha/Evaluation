package ma.projet.service;

import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import ma.projet.classes.LigneCommandeProduit;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class LigneCommandeProduitService implements IDao<LigneCommandeProduit> {
    @Override
    public boolean create(LigneCommandeProduit o) {
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
    public boolean update(LigneCommandeProduit o) {
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
    public boolean delete(LigneCommandeProduit o) {
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
    public LigneCommandeProduit findById(Long id) {
        // Not applicable for composite key
        return null;
    }

    @Override
    public List<LigneCommandeProduit> findAll() {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery("from LigneCommandeProduit", LigneCommandeProduit.class).list();
        }
    }
}


