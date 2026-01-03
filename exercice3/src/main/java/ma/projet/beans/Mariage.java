package ma.projet.beans;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Mariage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Homme homme;

    @ManyToOne(fetch = FetchType.LAZY)
    private Femme femme;

    @Temporal(TemporalType.DATE)
    private Date dateDebut;

    @Temporal(TemporalType.DATE)
    private Date dateFin; // null means still ongoing

    private int nbrEnfant;

    public Mariage() {}

    public Mariage(Homme homme, Femme femme, Date dateDebut, Date dateFin, int nbrEnfant) {
        this.homme = homme;
        this.femme = femme;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.nbrEnfant = nbrEnfant;
    }

    public Long getId() { return id; }
    public Homme getHomme() { return homme; }
    public Femme getFemme() { return femme; }
    public Date getDateDebut() { return dateDebut; }
    public void setDateDebut(Date dateDebut) { this.dateDebut = dateDebut; }
    public Date getDateFin() { return dateFin; }
    public void setDateFin(Date dateFin) { this.dateFin = dateFin; }
    public int getNbrEnfant() { return nbrEnfant; }
    public void setNbrEnfant(int nbrEnfant) { this.nbrEnfant = nbrEnfant; }
}
