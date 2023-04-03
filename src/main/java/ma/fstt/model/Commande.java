package ma.fstt.model;


import java.util.Date;

// java bean
public class Commande {
        private Long id ;
        private Double quantite;
        private Date date_D ;
        private Date date_F ;
        private String etat;
        private String client;
        private String produit;
        private String livreur;

    public Commande() {
    }
    public Commande(long id, Date date,String client, String livreur) {
        this.id = id;
        this.date_D = date;
        this.livreur = livreur;
        this.client=client;
    }
    public Commande(long id, String client, String livreur , Date date_F) {
        this.id = id;
        this.date_F = date_F;
        this.livreur = livreur;
        this.client=client;
    }
    public Commande(Long id,Double quantite,Date date_D, Date date_F, String etat,String client
            , String produit, String livreur) {
        this.id = id;
        this.quantite = quantite;
        this.date_D = date_D;
        this.date_F = date_F;
        this.etat = etat;
        this.client = client;
        this.produit=produit;
        this.livreur=livreur;
    }

    public Long getId() {
        return id;
    }
    public void setId(){ this.id = id;}
    public void setQuantite(Double quantite) {
        this.quantite = quantite;
    }
    public String getProduit() {
        return produit;
    }
    public void setProduit(String produit) {
        this.produit = produit;
    }
    public String getLivreur() {
        return livreur;
    }
    public void setLivreur(String livreur) {
        this.livreur = livreur;
    }
    public Double getQuantite() {
        return quantite;
    }
    public String getClient() {
        return client;
    }
    public void setClient(String client) {
        this.client = client;
    }
    public Date getDate_F() {
        return date_F;
    }
    public void setDate_F(Date date_F) {
        this.date_F = date_F;
    }
    public String getEtat() {
        return etat;
    }
    public void setEtat(String etat) {
        this.etat = etat;
    }
    public Date getDate_D() {
        return date_D;
    }
    public void setDate_D(Date date_D) {
        this.date_D = date_D;
    }

    @Override
    public String toString() {
        return "Commande{" +
                "id_commande=" + id +
                ", quantite='" + quantite + '\'' +
                 "date debut=" + date_D +
                "date fin=" + date_F +
                "etat=" + etat +
                "client=" + client +
                '}';
    }
}
