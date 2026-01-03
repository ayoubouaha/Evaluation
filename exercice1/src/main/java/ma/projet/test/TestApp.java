package ma.projet.test;

import ma.projet.classes.*;
import ma.projet.service.*;
import java.util.Date;
import java.util.List;

public class TestApp {
    public static void main(String[] args) {
        System.out.println("=== Exercice 1 - Gestion de Stock ===\n");

        // Initialize services
        CategorieService categorieService = new CategorieService();
        ProduitService produitService = new ProduitService();
        CommandeService commandeService = new CommandeService();

        try {
            // 1. Create categories
            System.out.println("1. Création des catégories...");
            Categorie cat1 = new Categorie("CAT001", "Électronique");
            Categorie cat2 = new Categorie("CAT002", "Vêtements");
            categorieService.create(cat1);
            categorieService.create(cat2);
            System.out.println("   ✓ Catégories créées: " + cat1.getLibelle() + ", " + cat2.getLibelle());

            // 2. Create products
            System.out.println("\n2. Création des produits...");
            Produit p1 = new Produit("PROD001", 1500.0, cat1);
            Produit p2 = new Produit("PROD002", 2500.0, cat1);
            Produit p3 = new Produit("PROD003", 500.0, cat2);
            Produit p4 = new Produit("PROD004", 3000.0, cat1);
            produitService.create(p1);
            produitService.create(p2);
            produitService.create(p3);
            produitService.create(p4);
            System.out.println("   ✓ Produits créés: " + p1.getReference() + ", " + p2.getReference() + 
                             ", " + p3.getReference() + ", " + p4.getReference());

            // 3. Create orders
            System.out.println("\n3. Création des commandes...");
            Date date1 = new Date(System.currentTimeMillis() - 86400000 * 5); // 5 days ago
            Date date2 = new Date(System.currentTimeMillis() - 86400000 * 2); // 2 days ago
            Date date3 = new Date(); // today

            Commande cmd1 = new Commande(date1);
            Commande cmd2 = new Commande(date2);
            Commande cmd3 = new Commande(date3);
            commandeService.create(cmd1);
            commandeService.create(cmd2);
            commandeService.create(cmd3);
            System.out.println("   ✓ Commandes créées: " + cmd1.getId() + ", " + cmd2.getId() + ", " + cmd3.getId());

            // 4. Create order lines (after orders and products are persisted)
            System.out.println("\n4. Création des lignes de commande...");
            LigneCommandeProduitService ligneService = new LigneCommandeProduitService();
            
            // Refresh to get IDs
            cmd1 = commandeService.findById(cmd1.getId());
            cmd2 = commandeService.findById(cmd2.getId());
            cmd3 = commandeService.findById(cmd3.getId());
            p1 = produitService.findById(p1.getId());
            p2 = produitService.findById(p2.getId());
            p3 = produitService.findById(p3.getId());
            p4 = produitService.findById(p4.getId());

            // Create order lines without setting ID in constructor (let @PrePersist handle it)
            LigneCommandeProduit l1 = new LigneCommandeProduit();
            l1.setCommande(cmd1);
            l1.setProduit(p1);
            l1.setQuantite(2);
            
            LigneCommandeProduit l2 = new LigneCommandeProduit();
            l2.setCommande(cmd1);
            l2.setProduit(p2);
            l2.setQuantite(1);
            
            LigneCommandeProduit l3 = new LigneCommandeProduit();
            l3.setCommande(cmd2);
            l3.setProduit(p3);
            l3.setQuantite(3);
            
            LigneCommandeProduit l4 = new LigneCommandeProduit();
            l4.setCommande(cmd3);
            l4.setProduit(p4);
            l4.setQuantite(1);
            
            LigneCommandeProduit l5 = new LigneCommandeProduit();
            l5.setCommande(cmd3);
            l5.setProduit(p1);
            l5.setQuantite(1);

            // Save order lines directly
            ligneService.create(l1);
            ligneService.create(l2);
            ligneService.create(l3);
            ligneService.create(l4);
            ligneService.create(l5);
            System.out.println("   ✓ Lignes de commande créées");

            // 5. Test queries
            System.out.println("\n5. Tests des requêtes...");

            // Find products by category
            List<Produit> produitsCat1 = produitService.findByCategorie(cat1.getId());
            System.out.println("   ✓ Produits de la catégorie " + cat1.getLibelle() + ": " + produitsCat1.size());

            // Find products with price > 1000
            List<Produit> produitsChers = produitService.findByPrixSuperieur(1000.0);
            System.out.println("   ✓ Produits avec prix > 1000: " + produitsChers.size());

            // Find products ordered between dates
            Date d1 = new Date(System.currentTimeMillis() - 86400000 * 7);
            Date d2 = new Date();
            List<Produit> produitsCommandes = produitService.findCommandesBetween(d1, d2);
            System.out.println("   ✓ Produits commandés entre " + d1 + " et " + d2 + ": " + produitsCommandes.size());

            // Find order lines by order
            List<LigneCommandeProduit> lignesCmd1 = produitService.findByCommande(cmd1.getId());
            System.out.println("   ✓ Lignes de la commande " + cmd1.getId() + ": " + lignesCmd1.size());

            // 6. Display all products
            System.out.println("\n6. Liste de tous les produits:");
            List<Produit> allProduits = produitService.findAll();
            for (Produit p : allProduits) {
                String categorieLibelle = "N/A";
                try {
                    if (p.getCategorie() != null) {
                        categorieLibelle = p.getCategorie().getLibelle();
                    }
                } catch (Exception e) {
                    // If lazy initialization fails, use category ID or skip
                    categorieLibelle = "N/A (lazy)";
                }
                System.out.println("   - " + p.getReference() + " | Prix: " + p.getPrix() + 
                                 " | Catégorie: " + categorieLibelle);
            }

            System.out.println("\n=== Exercice 1 terminé avec succès! ===");

        } catch (Exception e) {
            System.err.println("Erreur: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Close Hibernate session factory
            ma.projet.util.HibernateUtil.getSessionFactory().close();
        }
    }
}

