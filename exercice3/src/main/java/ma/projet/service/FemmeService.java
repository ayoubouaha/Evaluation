package ma.projet.service;

import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.Date;
import java.util.List;

public class FemmeService implements IDao<Femme> {
    @Override
    public boolean create(Femme o) {
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
    public boolean update(Femme o) {
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
    public boolean delete(Femme o) {
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
    public Femme findById(Long id) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.get(Femme.class, id);
        }
    }

    @Override
    public List<Femme> findAll() {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery("from Femme", Femme.class).list();
        }
    }

    public long countEnfantsBetweenNative(Long femmeId, Date d1, Date d2) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            Number n = (Number) s.createNamedNativeQuery("Femme.countEnfantsBetween")
                    .setParameter("fid", femmeId)
                    .setParameter("d1", new java.sql.Date(d1.getTime()))
                    .setParameter("d2", new java.sql.Date(d2.getTime()))
                    .getSingleResult();
            return n == null ? 0 : n.longValue();
        }
    }

    public List<Femme> femmesMarieesAuMoinsDeuxFois() {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createNamedQuery("Femme.marieeAuMoinsDeuxFois", Femme.class).list();
        }
    }

    public long countHommesMariesAQuatreFemmesEntre(Date d1, Date d2) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = s.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);

            Root<Homme> hommeRoot = cq.from(Homme.class);

            Subquery<Long> sub = cq.subquery(Long.class);
            Root<ma.projet.beans.Mariage> m = sub.from(ma.projet.beans.Mariage.class);
            Predicate range = cb.and(
                    cb.lessThanOrEqualTo(m.get("dateDebut"), d2),
                    cb.or(cb.isNull(m.get("dateFin")), cb.greaterThanOrEqualTo(m.get("dateFin"), d1))
            );
            sub.select(m.get("homme").get("id")).where(cb.and(cb.equal(m.get("homme").get("id"), hommeRoot.get("id")), range));

            cq.select(cb.countDistinct(hommeRoot))
              .where(cb.greaterThanOrEqualTo(cb.count(sub), 4L));

            // Fallback using HQL if criteria with count(sub) is not supported by provider
            List<Long> ids = s.createQuery(
                    "select m.homme.id from Mariage m where m.dateDebut <= :d2 and (m.dateFin is null or m.dateFin >= :d1) group by m.homme.id having count(distinct m.femme.id) >= 4",
                    Long.class)
                    .setParameter("d1", d1)
                    .setParameter("d2", d2)
                    .list();
            return ids.size();
        }
    }
}
