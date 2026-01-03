package ma.projet.classes;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date date;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LigneCommandeProduit> lignes = new ArrayList<>();

    public Commande() {}

    public Commande(Date date) {
        this.date = date;
    }

    public Long getId() { return id; }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    public List<LigneCommandeProduit> getLignes() { return lignes; }
}
