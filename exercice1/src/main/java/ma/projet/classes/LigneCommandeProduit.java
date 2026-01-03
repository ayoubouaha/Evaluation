package ma.projet.classes;

import javax.persistence.*;

@Entity
public class LigneCommandeProduit {
    @EmbeddedId
    private LigneCommandeProduitPK id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("commandeId")
    private Commande commande;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("produitId")
    private Produit produit;

    private int quantite;

    public LigneCommandeProduit() {}

    public LigneCommandeProduit(Commande commande, Produit produit, int quantite) {
        this.commande = commande;
        this.produit = produit;
        this.quantite = quantite;
        this.id = new LigneCommandeProduitPK(commande.getId(), produit.getId());
    }

    @PrePersist
    public void prePersist() {
        if (id == null && commande != null && produit != null) {
            this.id = new LigneCommandeProduitPK(commande.getId(), produit.getId());
        }
    }

    public LigneCommandeProduitPK getId() { return id; }
    public Commande getCommande() { return commande; }
    public void setCommande(Commande commande) { this.commande = commande; }
    public Produit getProduit() { return produit; }
    public void setProduit(Produit produit) { this.produit = produit; }
    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }
}
