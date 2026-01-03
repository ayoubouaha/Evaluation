package ma.projet.classes;

import javax.persistence.*;
import java.util.Date;

@Entity
public class EmployeTache {
    @EmbeddedId
    private EmployeTachePK id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("employeId")
    private Employe employe;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("tacheId")
    private Tache tache;

    @Temporal(TemporalType.DATE)
    private Date dateDebutReelle;
    @Temporal(TemporalType.DATE)
    private Date dateFinReelle;

    public EmployeTache() {}

    public EmployeTache(Employe employe, Tache tache, Date dateDebutReelle, Date dateFinReelle) {
        this.employe = employe;
        this.tache = tache;
        this.dateDebutReelle = dateDebutReelle;
        this.dateFinReelle = dateFinReelle;
        this.id = new EmployeTachePK(employe.getId(), tache.getId());
    }

    @PrePersist
    public void prePersist() {
        if (id == null && employe != null && tache != null) {
            id = new EmployeTachePK(employe.getId(), tache.getId());
        }
    }

    public EmployeTachePK getId() { return id; }
    public Employe getEmploye() { return employe; }
    public Tache getTache() { return tache; }
    public Date getDateDebutReelle() { return dateDebutReelle; }
    public void setDateDebutReelle(Date d) { this.dateDebutReelle = d; }
    public Date getDateFinReelle() { return dateFinReelle; }
    public void setDateFinReelle(Date d) { this.dateFinReelle = d; }
}
