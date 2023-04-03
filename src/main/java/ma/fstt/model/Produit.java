package ma.fstt.model;


// java bean
public class Produit {
        private Long id_produit ;

        private String nom ;

        private Double prix ;

    public Produit() {
    }

    public Produit(Long id_produit, String nom, Double prix) {
        this.id_produit = id_produit;
        this.nom = nom;
        this.prix = prix;
    }

    public Long getId_produit() {
        return id_produit;
    }

    public void setId_produit(Long id_produit) {
        this.id_produit = id_produit;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    @Override
    public String toString() {
        return "Produit{" +
                "id_produit=" + id_produit +
                ", nom='" + nom + '\'' +
                ", prix='" + prix + '\'' +
                '}';
    }
}
