package ma.projet.classes;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class LigneCommandeProduitPK implements Serializable {
    private Long commandeId;
    private Long produitId;

    public LigneCommandeProduitPK() {}
    public LigneCommandeProduitPK(Long commandeId, Long produitId) {
        this.commandeId = commandeId;
        this.produitId = produitId;
    }

    public Long getCommandeId() { return commandeId; }
    public Long getProduitId() { return produitId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LigneCommandeProduitPK that = (LigneCommandeProduitPK) o;
        return Objects.equals(commandeId, that.commandeId) && Objects.equals(produitId, that.produitId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commandeId, produitId);
    }
}
