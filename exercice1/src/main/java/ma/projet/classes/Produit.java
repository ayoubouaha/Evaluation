package ma.projet.classes;

import javax.persistence.*;

@Entity
@NamedQuery(name = "Produit.findByPrixSup", query = "from Produit p where p.prix > :minPrix")
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String reference;
    private double prix;

    @ManyToOne(fetch = FetchType.LAZY)
    private Categorie categorie;

    public Produit() {}

    public Produit(String reference, double prix, Categorie categorie) {
        this.reference = reference;
        this.prix = prix;
        this.categorie = categorie;
    }

    public Long getId() { return id; }
    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }
    public double getPrix() { return prix; }
    public void setPrix(double prix) { this.prix = prix; }
    public Categorie getCategorie() { return categorie; }
    public void setCategorie(Categorie categorie) { this.categorie = categorie; }
}
