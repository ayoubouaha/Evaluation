package ma.projet.beans;

import javax.persistence.Entity;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name = "Femme.marieeAuMoinsDeuxFois",
        query = "select f from Femme f where (select count(m) from Mariage m where m.femme = f) >= 2")
@NamedNativeQuery(name = "Femme.countEnfantsBetween",
        query = "SELECT COALESCE(SUM(m.nbrEnfant),0) FROM Mariage m WHERE m.femme_id = :fid AND m.dateDebut <= :d2 AND (m.dateFin IS NULL OR m.dateFin >= :d1)")
public class Femme extends Personne {
}
