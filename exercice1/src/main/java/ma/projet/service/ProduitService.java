package ma.projet.service;

import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import ma.projet.classes.LigneCommandeProduit;
import ma.projet.classes.Produit;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Date;
import java.util.List;

public class ProduitService implements IDao<Produit> {
    @Override
    public boolean create(Produit o) {
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
    public boolean update(Produit o) {
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
    public boolean delete(Produit o) {
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
    public Produit findById(Long id) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.get(Produit.class, id);
        }
    }

    @Override
    public List<Produit> findAll() {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery("select distinct p from Produit p left join fetch p.categorie", Produit.class).list();
        }
    }

    public List<Produit> findByCategorie(Long categorieId) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery("from Produit p where p.categorie.id = :cid", Produit.class)
                    .setParameter("cid", categorieId)
                    .list();
        }
    }

    public List<Produit> findCommandesBetween(Date d1, Date d2) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery("select distinct l.produit from LigneCommandeProduit l where l.commande.date between :d1 and :d2", Produit.class)
                    .setParameter("d1", d1)
                    .setParameter("d2", d2)
                    .list();
        }
    }

    public List<LigneCommandeProduit> findByCommande(Long commandeId) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery("from LigneCommandeProduit l where l.commande.id = :cid", LigneCommandeProduit.class)
                    .setParameter("cid", commandeId)
                    .list();
        }
    }

    public List<Produit> findByPrixSuperieur(double minPrix) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createNamedQuery("Produit.findByPrixSup", Produit.class)
                    .setParameter("minPrix", minPrix)
                    .list();
        }
    }
}
